package com.school.haja.endpoint.rest.controller;

import com.school.haja.endpoint.rest.dto.ImageDto;
import com.school.haja.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<ImageDto> submit(
      @RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
    var created = imageService.submit(file, email);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping
  public List<ImageDto> findAll() {
    return imageService.findAll();
  }
}
