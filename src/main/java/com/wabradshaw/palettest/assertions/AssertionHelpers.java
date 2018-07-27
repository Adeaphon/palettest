package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import org.opentest4j.AssertionFailedError;

import static com.wabradshaw.palettest.utils.ImageFileUtils.asImage;

/**
 * A set of helper methods to make writing test methods easier.
 */
class AssertionHelpers {

    private AssertionHelpers(){
        /* no-op */
    }

    /**
     * The failure method to be called in situations where the test can't actually go ahead. Typically used if the
     * supplied image is null. Where possible {@link #fail(String, Object, Object)} should be called instead.
     *
     * @param message  The error message that should be shown to the user.
     */
    static void fail(String message){
        throw new AssertionFailedError(message);
    }

    /**
     * The default failure method to be called from tests. Wherever possible tests should have an expected outcome.
     *
     * @param message  The error message that should be shown to the user.
     * @param expected The object that the user wanted.
     * @param actual   The object that was actually supplied.
     */
    static void fail(String message, Object expected, Object actual){
        throw new AssertionFailedError(message, expected, actual);
    }

    /**
     * Checks that the supplied byte array can be converted to an image, and fails if it can't.
     *
     * @param image The byte array that should represent an image.
     */
    static void checkWorksAsImage(byte[] image){
        try{
            asImage(image);
        } catch (Exception e){
            throw new AssertionFailedError(e.getMessage());
        }
    }


    /**
     * Checks that the {@link PaletteDistribution} has colors in it, and fails if it doesn't.
     *
     * @param distribution The {@link PaletteDistribution} under test.
     */
     static void checkDistribution(PaletteDistribution distribution) {
        if(distribution == null){
            fail("Could not assess color as the supplied distribution was null.");
        } else if(distribution.byCount().isEmpty()){
            fail("Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }
}
