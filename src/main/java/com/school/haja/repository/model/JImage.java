package com.school.haja.repository.model;

import com.school.haja.entity.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JImage {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "filename", nullable = false)
  private String filename;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  public Image toDomain() {
    return Image.builder().id(id).filename(filename).email(email).createdAt(createdAt).build();
  }

  public static JImage fromDomain(Image domain) {
    return JImage.builder()
        .id(domain.getId())
        .filename(domain.getFilename())
        .email(domain.getEmail())
        .createdAt(domain.getCreatedAt())
        .build();
  }
}
