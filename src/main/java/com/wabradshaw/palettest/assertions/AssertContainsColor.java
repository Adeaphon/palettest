package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;

import java.awt.Color;

import static com.wabradshaw.palettest.assertions.AssertionHelpers.checkDistribution;
import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;

/**
 * Assertion methods that check that a {@link PaletteDistribution} contains a particular {@link Tone}.
 */
public class AssertContainsColor {

    /**
     * <p>
     * Asserts that the {@link PaletteDistribution} contains the named color.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The name of the color which should be present in the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertContainsColor(String target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);

        if(count == null) {
            notFound(target);
        }
    }

    /**
     *  <p>
     * Asserts that the {@link PaletteDistribution} contains the supplied {@link Color}.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The {@link Color} which should be present in the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertContainsColor(Color target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);

        if(count == null) {
            String name = new Tone(target).getName();
            notFound(name);
        }
    }

    /**
     * Fails with a message saying the supplied name wasn't found.
     *
     * @param name The name of the Color.
     */
    private static void notFound(String name) {
            fail("The desired color (" + name +") wasn't used in the image.");
    }
}
