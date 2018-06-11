package com.wabradshaw.palettest.assertions;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;
import static com.wabradshaw.palettest.utils.ImageFileUtils.loadImageResource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of tests for {@link AssertDimensions}
 */
public class AssertPixelsMatchTest {

    /**
     * Tests assertPixelsMatch using BufferedImages if both expected and actual are null.
     */
    @Test
    void testOnImage_bothNull(){
        assertPixelsMatch((BufferedImage) null, (BufferedImage) null);
    }

    /**
     * Tests assertPixelsMatch using BufferedImages if the expected image is null but actual isn't.
     */
    @Test
    void testOnImage_expectedNull() {
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/red.png");
        try {
            assertPixelsMatch(null, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getIdentityHashCode(), 0);
            assertEquals(e.getActual().getIdentityHashCode(), actualImage.hashCode());
            assertEquals(e.getMessage(), "The expected image was null, but the actual image wasn't.");
        }
    }

    /**
     * Tests assertPixelsMatch using BufferedImages if the actual image is null but expected isn't.
     */
    @Test
    void testOnImage_actualNull(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        try {
            assertPixelsMatch(expectedImage, null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getIdentityHashCode(), expectedImage.hashCode());
            assertEquals(e.getActual().getIdentityHashCode(), 0);
            assertEquals(e.getMessage(), "The actual image was null.");
        }
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected objects are the same item.
     */
    @Test
    void testOnImage_identical(){
        BufferedImage image = loadImageResource("/sampleImages/geometric/red.png");
        assertPixelsMatch(image, image);
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
