package utils;

import java.security.MessageDigest;
import java.util.Base64;

public class Hasher {
  private static final String SHA3_INSTANCE_NAME = "SHA3-256";
  private static MessageDigest messageDigest;

  static {
    try {
      messageDigest = MessageDigest.getInstance(SHA3_INSTANCE_NAME);
    } catch (Exception exception) {
      ConsolePrinter.println(
        "Falha ao iniciar hasher SHA3-256!"
      );
    }
  }

  public static String hashAndEncode(String content) {
    byte[] hashedBytes = messageDigest.digest(content.getBytes());
    return Base64.getEncoder().encodeToString(
      hashedBytes
    );
  }

  public static boolean compare(
    String hashedContent, String content
  ) {
    return hashedContent.equals(hashAndEncode(content));
  }
}
