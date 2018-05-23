package com.wabradshaw.palettest.utils;

import java.io.FileOutputStream;

/**
 * A class used for simple file operations like saving a copy to disk for an actual review.
 */
public class ImageFileUtils {

    /**
     * Saves a byte array to the supplied path.
     *
     * @param output A byte array containing the file contents.
     * @param path   Where the file should be stored, including the path, name and extension
     * @throws IllegalArgumentException If the byte array is null.
     * @throws RuntimeException         If the file could not be saved.
     */
    public static void save(byte[] output, String path) {
        if (output == null) {
            throw new IllegalArgumentException("Could not save an empty byte array to " + path + ".");
        } else {
            try {
                new FileOutputStream(path).write(output);
            } catch (Exception ex) {
                throw new RuntimeException("Could not save the file " + path + ".", ex);
            }
        }
    }
}
