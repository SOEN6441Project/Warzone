package com.hexaforce.warzone.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

/**
 * Utility class for common operations such as checking null/empty collections, strings, and
 * objects.
 */
public class CommonUtil {

  /**
   * Checks if a string is empty or null.
   *
   * @param p_str The string to check.
   * @return true if the string is empty or null, otherwise false.
   */
  public static boolean isEmpty(String p_str) {
    return (p_str == null || p_str.trim().isEmpty());
  }

  /**
   * Checks if a string is not empty and not null.
   *
   * @param p_str The string to check.
   * @return true if the string is not empty and not null, otherwise false.
   */
  public static boolean isNotEmpty(String p_str) {
    return !isEmpty(p_str);
  }

  /**
   * Checks if an object is null.
   *
   * @param p_object The object to check.
   * @return true if the object is null, otherwise false.
   */
  public static boolean isNull(Object p_object) {
    return (p_object == null);
  }

  /**
   * Checks if a collection is empty or null.
   *
   * @param p_collection The collection to check.
   * @return true if the collection is empty or null, otherwise false.
   */
  public static boolean isCollectionEmpty(Collection<?> p_collection) {
    return (p_collection == null || p_collection.isEmpty());
  }

  /**
   * Checks if a map is empty or null.
   *
   * @param p_map The map to check.
   * @return true if the map is empty or null, otherwise false.
   */
  public static boolean isMapEmpty(Map<?, ?> p_map) {
    return (p_map == null || p_map.isEmpty());
  }

  /**
   * Generates an absolute file path for a given map file.
   *
   * @param p_fileName The filename to map to its file path.
   * @return The absolute file path of the map file, including its path.
   */
  public static String getMapFilePath(String p_fileName) {
    String l_absolutePath = new File("").getAbsolutePath();
    return l_absolutePath + File.separator + "src/main/maps" + File.separator + p_fileName;
  }
}
