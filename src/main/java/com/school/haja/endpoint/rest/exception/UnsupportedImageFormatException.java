package com.school.haja.endpoint.rest.exception;

public class UnsupportedImageFormatException extends RuntimeException {

  public UnsupportedImageFormatException(String contentType) {
    super(
        "Unsupported image format: "
            + contentType
            + ". Only image/jpeg and image/png are accepted.");
  }
}
