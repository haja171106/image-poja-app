package com.school.haja.service;

import com.school.haja.endpoint.event.EventProducer;
import com.school.haja.endpoint.event.model.ImageBwConversionRequested;
import com.school.haja.endpoint.rest.dto.ImageDto;
import com.school.haja.endpoint.rest.exception.UnsupportedImageFormatException;
import com.school.haja.file.bucket.BucketComponent;
import com.school.haja.repository.ImageRepository;
import com.school.haja.repository.model.JImage;
import com.school.haja.util.ImageFileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImageService {

  private final ImageRepository imageRepository;
  private final BucketComponent bucketComponent;
  private final EventProducer<ImageBwConversionRequested> eventProducer;

  @SneakyThrows
  public ImageDto submit(MultipartFile file, String email) {
    var contentType = file.getContentType();
    if (!ImageFileUtil.isSupportedContentType(contentType)) {
      throw new UnsupportedImageFormatException(contentType);
    }

    var entity =
        imageRepository.save(
            JImage.builder()
                .filename(file.getOriginalFilename())
                .email(email)
                .createdAt(Instant.now())
                .build());

    var tempFile = toTempFile(file);
    bucketComponent.upload(tempFile, ImageFileUtil.originalKey(entity.getId()));
    Files.deleteIfExists(tempFile.toPath());

    eventProducer.accept(
        List.of(ImageBwConversionRequested.builder().imageId(entity.getId()).build()));

    return ImageDto.from(entity.toDomain());
  }

  public List<ImageDto> findAll() {
    return imageRepository.findAll().stream().map(ImageDto::from).toList();
  }

  private File toTempFile(MultipartFile file) throws IOException {
    var tempFile = File.createTempFile("image-upload-", null);
    file.transferTo(tempFile);
    return tempFile;
  }
}
