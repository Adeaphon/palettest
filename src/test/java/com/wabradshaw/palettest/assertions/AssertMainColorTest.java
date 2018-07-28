package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.awt.Color;
import java.util.*;

import static com.wabradshaw.palettest.assertions.AssertMainColor.assertMainColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A set of tests for {@link AssertMainColor}
 */
public class AssertMainColorTest {

    /**
     * Tests that calling assertMainColor using a string will throw an exception if the distribution is null.
     */
    @Test
    public void testByString_NullDistribution(){
        try {
            assertMainColor("Green", null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertMainColor using a string will throw an exception if the distribution doesn't have any
     * colors in it.
     */
    @Test
    public void testByString_EmptyDistribution(){
        try {
            assertMainColor("Green", new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertMainColor using a string will fail if that color wasn't contained in the distribution.
     */
    @Test
    public void testByString_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertMainColor("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "Green");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a string will pass if it is the only one in the image.
     */
    @Test
    public void testByString_OnlyColor(){
        List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN));
        assertMainColor("Green", new PaletteDistribution(counts));
    }


    /**
     * Tests that calling assertMainColor using a string will fail if that color is minimal in the image.
     */
    @Test
    public void testByString_ContainedButNotBiggest(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                                                   toneCount(1, "Green", Color.GREEN));
            assertMainColor("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "Green");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a string will pass if it is by far the biggest in the image.
     */
    @Test
    public void testByString_StrictlyBiggest(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                                               toneCount(10, "Green", Color.GREEN));
        assertMainColor("Green", new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMainColor using a string will fail if there is another which is the same size (that
     * came first).
     */
    @Test
    public void testByString_Equal_TargetSecond(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                                                   toneCount(10, "Green", Color.GREEN));
            assertMainColor("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "Green");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a string will fail if there is another which is the same size (that
     * came later).
     */
    @Test
    public void testByString_Equal_TargetFirst(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN),
                                                   toneCount(10, "Red", Color.RED));
            assertMainColor("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "Green");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    //TODO
    /**
     * Tests that calling assertMainColor using a Color will throw an exception if the distribution is null.
     */
    @Test
    public void testByColor_NullDistribution(){
        try {
            assertMainColor(Color.GREEN, null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertMainColor using a Color will throw an exception if the distribution doesn't have any
     * colors in it.
     */
    @Test
    public void testByColor_EmptyDistribution(){
        try {
            assertMainColor(Color.GREEN, new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertMainColor using a Color will fail if that color wasn't contained in the distribution.
     */
    @Test
    public void testByColor_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertMainColor(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "#00ff00");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a Color will pass if it is the only one in the image.
     */
    @Test
    public void testByColor_OnlyColor(){
        List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN));
        assertMainColor(Color.GREEN, new PaletteDistribution(counts));
    }


    /**
     * Tests that calling assertMainColor using a Color will fail if that color is minimal in the image.
     */
    @Test
    public void testByColor_ContainedButNotBiggest(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                    toneCount(1, "Green", Color.GREEN));
            assertMainColor(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "#00ff00");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a Color will pass if it is by far the biggest in the image.
     */
    @Test
    public void testByColor_StrictlyBiggest(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                toneCount(10, "Green", Color.GREEN));
        assertMainColor(Color.GREEN, new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMainColor using a Color will fail if there is another which is the same size (that
     * came first).
     */
    @Test
    public void testByColor_Equal_TargetSecond(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                    toneCount(10, "Green", Color.GREEN));
            assertMainColor(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "#00ff00");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
    }

    /**
     * Tests that calling assertMainColor using a Color will fail if there is another which is the same size (that
     * came later).
     */
    @Test
    public void testByColor_Equal_TargetFirst(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN),
                    toneCount(10, "Red", Color.RED));
            assertMainColor(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "#00ff00");
            assertEquals(e.getActual().getValue(), "Red");
            assertEquals(e.getMessage(), "The desired color isn't the single greatest one in the image.");
        }
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
