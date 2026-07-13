package com.school.haja.util;

import java.util.Set;
import java.util.UUID;

public final class ImageFileUtil {

  private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/png");
  private static final String DEFAULT_EXTENSION = "jpg";

  private ImageFileUtil() {}

  public static boolean isSupportedContentType(String contentType) {
    return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase());
  }

  public static String originalKey(UUID imageId, String filename) {
    return "original/image-" + imageId + "." + extractExtension(filename);
  }

  public static String bwKey(UUID imageId) {
    return "bw/image-" + imageId + ".png";
  }

  private static String extractExtension(String filename) {
    if (filename == null) {
      return DEFAULT_EXTENSION;
    }
    var lastDot = filename.lastIndexOf('.');
    if (lastDot == -1 || lastDot == filename.length() - 1) {
      return DEFAULT_EXTENSION;
    }
    return filename.substring(lastDot + 1).toLowerCase();
  }
}
