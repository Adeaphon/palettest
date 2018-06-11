package com.wabradshaw.palettest.assertions;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import javax.imageio.ImageIO;
import java.awt.*;
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
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are the same object.
     */
    @Test
    void testOnImage_identical(){
        BufferedImage image = loadImageResource("/sampleImages/geometric/red.png");
        assertPixelsMatch(image, image);
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * represent the same image.
     */
    @Test
    void testOnImage_match(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/red.png");
        assertPixelsMatch(expectedImage, actualImage);
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * represent the same image. Specifically, they use the same complex image.
     */
    @Test
    void testOnImage_matchComplex(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/maps/Barcelona.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/maps/Barcelona.png");
        assertPixelsMatch(expectedImage, actualImage);
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * represent completely different images.
     */
    @Test
    void testOnImage_completelyDifferent(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/blue.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 0,0 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * represent completely different images. Specifically, they use different complex images.
     */
    @Test
    void testOnImage_completelyDifferentComplex(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/maps/Barcelona.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/maps/Bratislava.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), new Color(97,55,138));
            assertEquals(e.getActual().getValue(), new Color(182,182,61));
            assertEquals(e.getMessage(), "The pixel at 0,0 differs.");
        }
    }


    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * differ horizontally. The actual image has a blue vertical stripe.
     */
    @Test
    void testOnImage_differentHorizontally(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/redBlueVertical.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 5,0 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * differ vertically. The actual image has a blue horizontal stripe.
     */
    @Test
    void testOnImage_differentVertically(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 0,5 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * differ but share the same starting area. The actual image has a blue corner.
     */
    @Test
    void testOnImage_differentSubsection(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 5,5 differs.");
        }
    }


    /**
     * Tests assertPixelsMatch using BufferedImages where the actual and expected arguments are different objects that
     * differ but share everything except the final pixel.
     */
    @Test
    void testOnImage_differentPixel(){
        BufferedImage expectedImage = loadImageResource("/sampleImages/geometric/red.png");
        BufferedImage actualImage = loadImageResource("/sampleImages/geometric/redBluePixel.png");

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 9,9 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using byte arrays if both expected and actual are null.
     */
    @Test
    void testOnArray_bothNull(){
        assertPixelsMatch((byte[]) null, (byte[]) null);
    }

    /**
     * Tests assertPixelsMatch using byte arrays if the expected image is null but actual isn't.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_expectedNull() throws IOException {
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
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
     * Tests assertPixelsMatch using byte arrays if the actual image is null but expected isn't.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_actualNull() throws IOException {
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
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
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are the same object.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_identical() throws IOException{
        byte[] image = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        assertPixelsMatch(image, image);
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * represent the same image.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_match() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        assertPixelsMatch(expectedImage, actualImage);
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * represent the same image. Specifically, they use the same complex image.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_matchComplex() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/maps/Barcelona.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/maps/Barcelona.png"));
        assertPixelsMatch(expectedImage, actualImage);
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * represent completely different images.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_completelyDifferent() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/blue.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 0,0 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * represent completely different images. Specifically, they use different complex images.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_completelyDifferentComplex() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/maps/Barcelona.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/maps/Bratislava.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), new Color(97,55,138));
            assertEquals(e.getActual().getValue(), new Color(182,182,61));
            assertEquals(e.getMessage(), "The pixel at 0,0 differs.");
        }
    }


    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * differ horizontally. The actual image has a blue vertical stripe.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentHorizontally() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/redBlueVertical.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 5,0 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * differ vertically. The actual image has a blue horizontal stripe.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentVertically() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/redBlueHorizontal.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 0,5 differs.");
        }
    }

    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * differ but share the same starting area. The actual image has a blue corner.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentSubsection() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/redBlueQuarter.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 5,5 differs.");
        }
    }


    /**
     * Tests assertPixelsMatch using byte arrays where the actual and expected arguments are different objects that
     * differ but share everything except the final pixel.
     *
     * @throws IOException - If the file with the test image can't be loaded.
     */
    @Test
    void testOnArray_differentPixel() throws IOException{
        byte[] expectedImage = toArray(loadImageResource("/sampleImages/geometric/red.png"));
        byte[] actualImage = toArray(loadImageResource("/sampleImages/geometric/redBluePixel.png"));

        try {
            assertPixelsMatch(expectedImage, actualImage);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), Color.RED);
            assertEquals(e.getActual().getValue(), Color.BLUE);
            assertEquals(e.getMessage(), "The pixel at 9,9 differs.");
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
