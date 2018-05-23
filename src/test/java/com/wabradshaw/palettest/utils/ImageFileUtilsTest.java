package com.wabradshaw.palettest.utils;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.TemporaryFolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A set of tests for the {@link ImageFileUtils} class.
 */
@EnableRuleMigrationSupport
public class ImageFileUtilsTest {

    /**
     * Tests the save method when used on a byte array.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testSaveFile() throws IOException {
        BufferedImage original = ImageIO.read(this.getClass().getResourceAsStream("/sampleImages/maps/Rome.png"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(original, "png", outputStream);
        outputStream.flush();
        byte[] array = outputStream.toByteArray();

        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        File outputLocation = new File(tempFolder.getRoot().getPath() + "/saveArray.png");

        assertEquals(false, outputLocation.exists());
        ImageFileUtils.save(array, outputLocation.getPath());
        assertEquals(true, outputLocation.exists());
    }

    /**
     * Tests the save method for byte arrays when supplied with null.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testSaveFile_null() throws IOException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        File outputLocation = new File(tempFolder.getRoot().getPath() + "/saveNullArray.png");

        assertThrows(RuntimeException.class, ()-> ImageFileUtils.save(null, outputLocation.getPath()));
    }
}
