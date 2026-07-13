package com.school.haja.util;

import java.util.Set;

public final class ImageFileUtil {

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png");

  private ImageFileUtil() {}

  public static boolean isSupportedContentType(String contentType) {
    return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase());
  }

  public static String originalKey(Long imageId, String filename) {
    return "original/" + imageId;
  }

  public static String bwKey(Long imageId) {
    return "bw/" + imageId + ".png";
  }
}
