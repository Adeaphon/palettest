package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;

import java.awt.*;
import java.util.stream.Collectors;

import static com.wabradshaw.palettest.assertions.AssertionHelpers.checkDistribution;
import static com.wabradshaw.palettest.assertions.AssertionHelpers.fail;

/**
 * Assertion methods that check that the majority (above 50% of pixels) are part of a certain
 * {@link com.wabradshaw.palettest.analysis.Tone}.
 */
public class AssertMostly {

    /**
     * <p>
     * Asserts that the more than 50% of the {@link PaletteDistribution} is the desired color. This finds the color
     * using its name. Please note that this is strictly more than 50%, so an image which is half one color will fail.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The name of the color which should be the majority of the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertMostly(String target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);

        checkMostly(target, distribution, count);
    }

    /**
     * <p>
     * Asserts that the more than 50% of the {@link PaletteDistribution} is the desired color. This finds the color
     * using its name. Please note that this is strictly more than 50%, so an image which is half one color will fail.
     * </p>
     * <p>
     * This assertion will fail if the distribution is null, or if there are no {@link ToneCount}s in the distribution.
     * </p>
     * @param target       The {@link java.awt.Color} which should be the majority of the image.
     * @param distribution The {@link PaletteDistribution} describing an image.
     */
    public static void assertMostly(Color target, PaletteDistribution distribution){
        checkDistribution(distribution);

        ToneCount count = distribution.get(target);
        String name = new Tone(target).getName();

        checkMostly(name, distribution, count);
    }

    /**
     * Checks that the supplied count makes up the strict majority of the distribution.
     *
     * @param targetName   The name describing the color that should be the majority.
     * @param distribution The {@link PaletteDistribution} under test.
     * @param actualCount  The {@link ToneCount} representing the actual number of times the desired color was used.
     */
    private static void checkMostly(String targetName, PaletteDistribution distribution, ToneCount actualCount) {
        int totalPixels = distribution.getDistribution().stream().collect(Collectors.summingInt(ToneCount::getCount));
        int targetPixels = totalPixels > 1 ? totalPixels / 2 + 1 : 1;

        if(actualCount == null){
            fail("The image didn't contain the desired color.",
                    pixels(targetName, targetPixels),
                    distribution.byCount().toString());
        } else if (actualCount.getCount() < targetPixels){
            fail("Not enough of the image was the desired color.",
                    pixels(targetName, targetPixels),
                    distribution.byCount().toString());
        }
    }

    /**
     * A helper method to say pixel or pixels based on whether or not the number is greater than 1.
     *
     * @param descriptor A descriptor to describe what type of pixels.
     * @param pixels     The number of pixels to mention.
     * @return           A string saying the number of pixels
     */
    private static String pixels(String descriptor, int pixels){
        String pixelsMessage = pixels > 1 ? "pixels" : "pixel";
        return pixels + " " + descriptor + " " + pixelsMessage;
    }
}
