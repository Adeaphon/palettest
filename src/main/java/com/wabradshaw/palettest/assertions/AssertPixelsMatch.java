package com.wabradshaw.palettest.assertions;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;
import static com.wabradshaw.palettest.utils.ImageFileUtils.asImage;

public class AssertPixelsMatch {

    /**
     * <p>
     * Asserts that one {@link BufferedImage} has all of the same pixels as the expected image. This includes checking
     * that the actual image has the same dimensions as the expected image.
     * </p>
     * <p>
     * Image matches are done in 8-bit RGB, so minute differences may not be picked up.
     * </p>
     * </p>
     * If both images are null, then this counts as a match. If one is null, then the assertion will fail.
     * </p>
     * @param expected The {@link BufferedImage} that the user wants to be produced.
     * @param actual   The {@Link BufferedImage} that was produced.
     */
    public static void assertPixelsMatch(BufferedImage expected, BufferedImage actual){
        if(expected != actual) {
            if(actual == null){
                fail("The actual image was null.", expected, actual);
            } else if(expected == null) {
                fail("The expected image was null, but the actual image wasn't.", expected, actual);
            } else {
                assertDimensions(actual, expected.getWidth(), expected.getHeight());

                BufferedImage argbExpected = createArgbCopy(expected);
                BufferedImage argbActual = createArgbCopy(actual);

                for(int x = 0; x < argbExpected.getWidth(); x++){
                    for(int y = 0; y < argbExpected.getHeight(); y++){
                        int expectedPixel = argbExpected.getRGB(x, y);
                        int actualPixel = argbActual.getRGB(x, y);

                        if(expectedPixel != actualPixel){
                            fail("The pixel at " + x + "," + y + " differs.",
                                 new Color(expectedPixel),
                                 new Color(actualPixel));
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>
     * Asserts that one {@link BufferedImage} has all of the same pixels as the expected image.
     * </p>
     * <p>
     * Image matches are done in 8-bit RGB, so minute differences may not be picked up.
     * </p>
     * </p>
     * If both images are null, then this counts as a match. If one is null, then the assertion will fail.
     * </p>
     * @param expected The {@link BufferedImage} that the user wants to be produced.
     * @param actual   The {@Link BufferedImage} that was produced.
     */
    public static void assertPixelsMatch(byte[] expected, byte[] actual){
        assertPixelsMatch(asImage(expected), asImage(actual));
    }

    /**
     * Creates a copy of an image restricted to the ARGB colour space. Done as BufferedImages can use different image
     * types, including custom types. See https://stackoverflow.com/a/22391951
     *
     * @param image The image to copy
     * @return      An ARGB copy of the image
     */
    private static BufferedImage createArgbCopy(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return copy;
    }
}
