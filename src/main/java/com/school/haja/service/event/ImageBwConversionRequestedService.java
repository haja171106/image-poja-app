package com.school.haja.service.event;

import com.school.haja.endpoint.event.model.ImageBwConversionRequested;
import com.school.haja.file.bucket.BucketComponent;
import com.school.haja.mail.Email;
import com.school.haja.mail.Mailer;
import com.school.haja.repository.ImageRepository;
import com.school.haja.util.ImageFileUtil;
import jakarta.mail.internet.InternetAddress;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImageBwConversionRequestedService implements Consumer<ImageBwConversionRequested> {

  private static final Duration PRESIGNED_URI_TTL = Duration.ofDays(7);

  private final ImageRepository imageRepository;
  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(ImageBwConversionRequested event) {
    var image =
        imageRepository
            .findById(event.getImageId())
            .orElseThrow(
                () -> new IllegalStateException("Image introuvable, id: " + event.getImageId()));

    var originalFile =
        bucketComponent.download(ImageFileUtil.originalKey(image.getId(), image.getFilename()));

    var originalImage = ImageIO.read(originalFile);
    var bwImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    new ColorConvertOp(null).filter(originalImage, bwImage);

    var bwFile = File.createTempFile("image-bw-", ".png");
    ImageIO.write(bwImage, "png", bwFile);

    var bwKey = ImageFileUtil.bwKey(image.getId());
    bucketComponent.upload(bwFile, bwKey);
    var presignedUri = bucketComponent.presign(bwKey, PRESIGNED_URI_TTL);

    var recipient = new InternetAddress(image.getEmail());
    mailer.accept(
        new Email(
            recipient,
            List.of(),
            List.of(),
            "Votre image en noir et blanc est prête",
            "Bonjour,\n\n"
                + "Voici le lien de téléchargement de votre image convertie en noir et blanc :\n"
                + presignedUri
                + "\n\nCe lien est valable 7 jours.",
            List.of()));
  }
}
