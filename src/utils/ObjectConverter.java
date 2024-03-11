package utils;

public class ObjectConverter {
  @SuppressWarnings("unchecked")
  public static <T> T convert(Object objectToConvert) {
    try {
      Class<T> targetClass = (Class<T>) objectToConvert.getClass();
      return targetClass.cast(objectToConvert);
    } catch (ClassCastException e) {
      return null;
    }
  }
}
