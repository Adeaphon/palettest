package com.wabradshaw.palettest.assertions;

import java.awt.image.BufferedImage;

import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;
import static com.wabradshaw.palettest.utils.ImageFileUtils.asImage;

/**
 * Assertion methods that check that an image has the desired dimensions. All methods take width x height in that order.
 */
public class AssertDimensions {

    /**
     * <p>
     * Asserts that the supplied {@link BufferedImage} represents an image with the supplied width and height.
     * </p>
     * </p>
     * This assertion will fail if the image is null, or if either dimension does not match the target.
     * </p>
     * @param image        The {@link BufferedImage} to test.
     * @param targetWidth  The desired width in pixels.
     * @param targetHeight The desired height in pixels.
     */
    public static void assertDimensions(BufferedImage image, int targetWidth, int targetHeight){
        if(image == null){
            fail("Could not assess dimensions as the supplied image was null.");
        }

        int actualWidth = image.getWidth();
        int actualHeight = image.getHeight();

        if(actualWidth != targetWidth || actualHeight != targetHeight){
            fail("Image did not meet the required dimensions.",
                 targetWidth + " x " + targetHeight,
                 actualWidth + " x " + actualHeight);
        }
    }

    /**
     * <p>
     * Asserts that the supplied byte array represents an image with the supplied width and height.
     * </p>
     * </p>
     * This assertion will fail if the byte array is null, or if either dimension does not match the target.
     * </p>
     * <p>
     * If the byte array can't be converted to an image, this will throw an exception.
     * </p>
     * @param image        The byte array which represents the content of an image.
     * @param targetWidth  The desired width in pixels.
     * @param targetHeight The desired height in pixels.
     */
    public static void assertDimensions(byte[] image, int targetWidth, int targetHeight){
        assertDimensions(asImage(image), targetWidth, targetHeight);
    }
}
