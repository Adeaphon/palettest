package com.wabradshaw.palettest.assertions;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A set of tests for {@link AssertDimensions}
 */
public class AssertDimensionsTest {

    /**
     * Tests assertDimensions used on a null BufferedImage.
     */
    @Test
    void testOnImage_null(){
        try {
            assertDimensions((BufferedImage) null, 1, 2);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess dimensions as the supplied image was null.");
        }
    }

    /**
     * Tests assertDimensions used correctly on a 1x1 BufferedImage.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnImage_matching1x1() throws IOException {
        String name = "/sampleImages/dimensions/1x1.png";
        BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
        assertDimensions(image, 1, 1);
    }

    /**
     * Tests assertDimensions used correctly on a 4x4 BufferedImage.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnImage_matching4x4() throws IOException {
        String name = "/sampleImages/dimensions/4x4.png";
        BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
        assertDimensions(image, 4, 4);
    }

    /**
     * Tests assertDimensions used on a 1x4 BufferedImage, when it was expected to have a different width.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnImage_differentWidth() throws IOException {
        try {
            String name = "/sampleImages/dimensions/1x4.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(image, 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "1 x 4");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * Tests assertDimensions used on a 4x1 BufferedImage, when it was expected to have a different width.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnImage_differentHeight() throws IOException {
        try {
            String name = "/sampleImages/dimensions/4x1.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(image, 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "4 x 1");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * Tests assertDimensions used on a 1x1 BufferedImage, when it was expected to have a different width and height.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnImage_differentBoth() throws IOException {
        try {
            String name = "/sampleImages/dimensions/1x1.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(image, 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "1 x 1");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * Tests assertDimensions used on a null byte array.
     */
    @Test
    void testOnArray_null(){
        assertThrows(IllegalArgumentException.class, () -> assertDimensions((byte[]) null, 1, 2));
    }

    /**
     * Tests assertDimensions used correctly on a 1x1 byte array.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_matching1x1() throws IOException {
        String name = "/sampleImages/dimensions/1x1.png";
        BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
        assertDimensions(toArray(image), 1, 1);
    }

    /**
     * Tests assertDimensions used correctly on a 4x4 byte array.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_matching4x4() throws IOException {
        String name = "/sampleImages/dimensions/4x4.png";
        BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
        assertDimensions(toArray(image), 4, 4);
    }

    /**
     * Tests assertDimensions used on a 1x4 byte array, when it was expected to have a different width.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentWidth() throws IOException {
        try {
            String name = "/sampleImages/dimensions/1x4.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(toArray(image), 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "1 x 4");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * Tests assertDimensions used on a 4x1 byte array, when it was expected to have a different width.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentHeight() throws IOException {
        try {
            String name = "/sampleImages/dimensions/4x1.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(toArray(image), 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "4 x 1");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * Tests assertDimensions used on a 1x1 byte array, when it was expected to have a different width and height.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentBoth() throws IOException {
        try {
            String name = "/sampleImages/dimensions/1x1.png";
            BufferedImage image = ImageIO.read(this.getClass().getResourceAsStream(name));
            assertDimensions(toArray(image), 4, 4);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "4 x 4");
            assertEquals(e.getActual().getValue(), "1 x 1");
            assertEquals(e.getMessage(), "Image did not meet the required dimensions.");
        }
    }

    /**
     * A helper method to convert a BufferedImage to a byte array.
     *
     * @param original The BufferedImage to convert.
     * @return         A byte array representing the buffered image.
     * @throws IOException - If the image could not be converted.
     */
    private byte[] toArray(BufferedImage original) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(original, "png", outputStream);
        outputStream.flush();
        return outputStream.toByteArray();
    }
}
