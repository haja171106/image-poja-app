package com.school.haja.endpoint.rest.dto;

import com.school.haja.entity.Image;
import java.time.Instant;

public record ImageDto(Long id, String filename, String email, Instant createdAt) {

  public static ImageDto from(Image domain) {
    return new ImageDto(
        domain.getId(), domain.getFilename(), domain.getEmail(), domain.getCreatedAt());
  }
}
