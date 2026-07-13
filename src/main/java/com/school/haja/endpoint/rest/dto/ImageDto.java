package com.school.haja.endpoint.rest.dto;

import com.school.haja.entity.Image;
import java.time.Instant;
import java.util.UUID;

public record ImageDto(UUID id, String filename, String email, Instant createdAt) {

  public static ImageDto from(Image domain) {
    return new ImageDto(
        domain.getId(), domain.getFilename(), domain.getEmail(), domain.getCreatedAt());
  }
}
