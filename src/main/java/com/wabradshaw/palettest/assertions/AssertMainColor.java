package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;

import java.awt.*;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertionHelpers.checkDistribution;
import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;

/**
 * Assertion methods that check that the biggest single {@link Tone} in a {@link PaletteDistribution} is the one
 * specified.
 */
public class AssertMainColor {

    /**
     * <p>
     * Asserts that the largest Color concentration {@link PaletteDistribution} is the desired one. This finds the color
     * using its name. Please note that if there are multiple colors with the same count, this will fail.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The name of the color which should be the biggest color in the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertMainColor(String target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);

        checkMainColor(target, distribution, count);
    }


    /**
     * <p>
     * Asserts that the largest Color concentration {@link PaletteDistribution} is the desired one. This finds the color
     * using its {@link Color} representation. Please note that if there are multiple colors with the same count, this
     * will fail.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The {@link Color} which should be the biggest color in the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertMainColor(Color target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);
        String name = new Tone(target).getName();

        checkMainColor(name, distribution, count);
    }

    /**
     * Checks that the supplied count is the single biggest one in the distribution.
     *
     * @param targetName   The name describing the color that should be the majority.
     * @param distribution The {@link PaletteDistribution} under test.
     * @param actual       The {@link ToneCount} representing the number of times the desired color was used.
     */
    private static void checkMainColor(String targetName, PaletteDistribution distribution, ToneCount actual) {
        List<ToneCount> tones = distribution.byCount();

        ToneCount biggest = tones.get(0);
        if(biggest != actual) {
            fail("The desired color isn't the single greatest one in the image.",
                 targetName,
                 biggest.getTone().getName());
        }

        if(tones.size() > 1){
            ToneCount secondBiggest = tones.get(1);
            if(secondBiggest.getCount() == biggest.getCount()) {
                fail("The desired color isn't the single greatest one in the image.",
                        targetName,
                        secondBiggest.getTone().getName());
            }
        }
    }
}
