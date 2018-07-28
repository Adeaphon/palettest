package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.awt.Color;
import java.util.*;

import static com.wabradshaw.palettest.assertions.AssertContainsColor.assertContainsColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A set of tests for {@link AssertContainsColor}
 */
public class AssertContainsColorTest {

    /**
     * Tests that calling assertContainsColor using a string will throw an exception if the distribution is null.
     */
    @Test
    public void testByString_NullDistribution(){
        try {
            assertContainsColor("Green", null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a string will throw an exception if the distribution doesn't have
     * any colors in it.
     */
    @Test
    public void testByString_EmptyDistribution(){
        try {
            assertContainsColor("Green", new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a string will fail if that color wasn't contained in the
     * distribution.
     */
    @Test
    public void testByString_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertContainsColor("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "The desired color (Green) wasn't used in the image.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a string will pass if that color is the only one in the
     * distribution.
     */
    @Test
    public void testByString_OnlyColor(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Green", Color.GREEN));
        assertContainsColor("Green", new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertContainsColor using a string will pass if that color is contained in the
     * distribution.
     */
    @Test
    public void testByString_AongstOtherColors(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                                               toneCount(1, "Green", Color.GREEN),
                                               toneCount(1, "Blue", Color.BLUE));
        assertContainsColor("Green", new PaletteDistribution(counts));
    }
    
    //TODO

    /**
     * Tests that calling assertContainsColor using a Color will throw an exception if the distribution is null.
     */
    @Test
    public void testByColor_NullDistribution(){
        try {
            assertContainsColor(Color.GREEN, null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a Color will throw an exception if the distribution doesn't have
     * any colors in it.
     */
    @Test
    public void testByColor_EmptyDistribution(){
        try {
            assertContainsColor(Color.GREEN, new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a Color will fail if that color wasn't contained in the
     * distribution.
     */
    @Test
    public void testByColor_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertContainsColor(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "The desired color (#00ff00) wasn't used in the image.");
        }
    }

    /**
     * Tests that calling assertContainsColor using a Color will pass if that color is the only one in the
     * distribution.
     */
    @Test
    public void testByColor_OnlyColor(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Green", Color.GREEN));
        assertContainsColor(Color.GREEN, new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertContainsColor using a Color will pass if that color is contained in the
     * distribution.
     */
    @Test
    public void testByColor_AongstOtherColors(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                toneCount(1, "Green", Color.GREEN),
                toneCount(1, "Blue", Color.BLUE));
        assertContainsColor(Color.GREEN, new PaletteDistribution(counts));
    }
    
    /**
     * A helper method to instantiate a {@link ToneCount} that only contains a single color.
     *
     * @param pixels The number of pixels in that color.
     * @param name   The name of the {@link Tone}.
     * @param color  The {@link Color} the {@link Tone} represents.
     * @return       A {@link ToneCount} with the specified details.
     */
    private ToneCount toneCount(int pixels, String name, Color color){
        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(color, pixels);
        return new ToneCount(new Tone(name, color), pixelCounts);
    }
}
