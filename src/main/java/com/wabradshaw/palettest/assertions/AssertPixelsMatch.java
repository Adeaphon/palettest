package com.wabradshaw.palettest.assertions;

import java.awt.image.BufferedImage;

import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;
import static com.wabradshaw.palettest.utils.ImageFileUtils.asImage;

public class AssertPixelsMatch {

    /**
     * <p>
     * Asserts that one {@link BufferedImage} has all of the same pixels as the expected image.
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
                //TODO
            }
        }
    }

    /**
     * <p>
     * Asserts that one {@link BufferedImage} has all of the same pixels as the expected image.
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
}
