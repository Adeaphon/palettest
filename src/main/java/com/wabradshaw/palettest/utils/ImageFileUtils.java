package com.wabradshaw.palettest.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A class used for simple file operations like saving a copy to disk for an actual review.
 */
public class ImageFileUtils {

    /**
     * Saves a byte array to the supplied path.
     *
     * @param content A byte array containing the file contents.
     * @param path    Where the file should be stored, including the path, name and extension
     * @throws IllegalArgumentException If the byte array is null.
     * @throws RuntimeException         If the file could not be saved.
     */
    public static void save(byte[] content, String path) {
        if (content == null) {
            throw new IllegalArgumentException("Could not save an empty byte array to " + path + ".");
        } else {
            try {
                new FileOutputStream(path).write(content);
            } catch (IOException ex) {
                throw new RuntimeException("Could not save the file " + path + ".", ex);
            }
        }
    }

    /**
     * Saves a {@link BufferedImage} as the supplied file type to the supplied path.
     *
     * Please note that the path should include the desired extension. The path's extension is what the file is saved
     * under, while the fileType is the extension defining the type of image. These should typically be the same.
     *
     * @param content   A byte array containing the file contents.
     * @param path      Where the file should be stored, including the path, name and extension.
     * @param fileType  The type of image being processed, e.g. "png" or "jpg".
     * @throws IllegalArgumentException If the byte array is null.
     * @throws RuntimeException         If the file could not be saved.
     */
    public static void save(BufferedImage content, String path, String fileType) {
        if (content == null) {
            throw new IllegalArgumentException("Could not save an empty BufferedImage to " + path + ".");
        } else {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(content, fileType, outputStream);
                outputStream.flush();
                byte[] array = outputStream.toByteArray();

                new FileOutputStream(path).write(array);
            } catch (IOException ex) {
                throw new RuntimeException("Could not save the file " + path + ".", ex);
            }
        }
    }

    /**
     * Takes a byte array representing an image and converts it to a BufferedImage.
     *
     * @param content A byte array containing the image.
     * @return        A BufferedImage containing the image.
     * @throws IllegalArgumentException If the byte array is null.
     * @throws RuntimeException         If the byte array could not be converted to an image.
     */
    public static BufferedImage asImage(byte[] content){
        if(content == null){
            throw new IllegalArgumentException("Could not load an empty byte array as an image.");
        } else {
            try {
                return ImageIO.read(new ByteArrayInputStream(content));
            } catch (IOException e) {
                throw new RuntimeException("Could not load the supplied byte array as an image.");
            }
        }
    }
}
