package com.wabradshaw.palettest.assertions;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertMostly.assertMostly;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of tests for {@link AssertMostly}
 */
public class AssertMostlyTest {

    /**
     * Tests that calling assertMostly using a string will throw an exception if the distribution is null.
     */
    @Test
    public void testByString_NullDistribution(){
        try {
            assertMostly("Green", null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertMostly using a string will throw an exception if the distribution doesn't have any
     * colors in it.
     */
    @Test
    public void testByString_EmptyDistribution(){
        try {
            assertMostly("Green", new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertMostly using a string will fail if that color wasn't contained in the distribution.
     */
    @Test
    public void testByString_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertMostly("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "1 Green pixel");
            assertEquals(e.getActual().getValue(), "[Red: 1]");
            assertEquals(e.getMessage(), "The image didn't contain the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a string will succeed on an image with a single pixel of the desired color.
     */
    @Test
    public void testByString_OnlyColor_OnePixel(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Green", Color.GREEN));
        assertMostly("Green", new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a string will succeed on an image with several pixel of the desired color.
     */
    @Test
    public void testByString_OnlyColor_ManyPixels(){
        List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN));
        assertMostly("Green", new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a string will fail on an image where the target color is not the main one.
     */
    @Test
    public void testByString_MultipleColors_NotTheMajority(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                                                   toneCount(1, "Green", Color.GREEN));

            assertMostly("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "6 Green pixels");
            assertEquals(e.getActual().getValue(), "[Red: 10, Green: 1]");
            assertEquals(e.getMessage(), "Not enough of the image was the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a string will fail on an image where the target color is not the main one.
     */
    @Test
    public void testByString_MultipleColors_Half(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(5, "Red", Color.RED),
                                                   toneCount(5, "Green", Color.GREEN));

            assertMostly("Green", new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "6 Green pixels");
            assertEquals(e.getActual().getValue(), "[Red: 5, Green: 5]");
            assertEquals(e.getMessage(), "Not enough of the image was the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a string will pass if the target color is only just the majority.
     */
    @Test
    public void testByString_MultipleColors_ExactMajority(){
        List<ToneCount> counts = Arrays.asList(toneCount(5, "Red", Color.RED),
                                               toneCount(6, "Green", Color.GREEN));

        assertMostly("Green", new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a string will pass if the target color is definitely the majority.
     */
    @Test
    public void testByString_MultipleColors_SevereMajority(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                                               toneCount(6, "Green", Color.GREEN));

        assertMostly("Green", new PaletteDistribution(counts));
    }
    
    //TODO
    
    /**
     * Tests that calling assertMostly using a Color will throw an exception if the distribution is null.
     */
    @Test
    public void testByColor_NullDistribution(){
        try {
            assertMostly(Color.GREEN, null);
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution was null.");
        }
    }

    /**
     * Tests that calling assertMostly using a Color will throw an exception if the distribution doesn't have any
     * colors in it.
     */
    @Test
    public void testByColor_EmptyDistribution(){
        try {
            assertMostly(Color.GREEN, new PaletteDistribution(new ArrayList<>()));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getMessage(), "Could not assess color as the supplied distribution didn't contain any colors.");
        }
    }

    /**
     * Tests that calling assertMostly using a Color will fail if that color wasn't contained in the distribution.
     */
    @Test
    public void testByColor_NotContained(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED));
            assertMostly(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "1 #00ff00 pixel");
            assertEquals(e.getActual().getValue(), "[Red: 1]");
            assertEquals(e.getMessage(), "The image didn't contain the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a Color will succeed on an image with a single pixel of the desired color.
     */
    @Test
    public void testByColor_OnlyColor_OnePixel(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Green", Color.GREEN));
        assertMostly(Color.GREEN, new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a Color will succeed on an image with several pixel of the desired color.
     */
    @Test
    public void testByColor_OnlyColor_ManyPixels(){
        List<ToneCount> counts = Arrays.asList(toneCount(10, "Green", Color.GREEN));
        assertMostly(Color.GREEN, new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a Color will fail on an image where the target color is not the main one.
     */
    @Test
    public void testByColor_MultipleColors_NotTheMajority(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(10, "Red", Color.RED),
                                                   toneCount(1, "Green", Color.GREEN));

            assertMostly(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "6 #00ff00 pixels");
            assertEquals(e.getActual().getValue(), "[Red: 10, Green: 1]");
            assertEquals(e.getMessage(), "Not enough of the image was the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a Color will fail on an image where the target color is not the main one.
     */
    @Test
    public void testByColor_MultipleColors_Half(){
        try {
            List<ToneCount> counts = Arrays.asList(toneCount(5, "Red", Color.RED),
                                                   toneCount(5, "Green", Color.GREEN));

            assertMostly(Color.GREEN, new PaletteDistribution(counts));
            fail("Should have thrown an exception.");
        } catch (AssertionFailedError e){
            assertEquals(e.getExpected().getValue(), "6 #00ff00 pixels");
            assertEquals(e.getActual().getValue(), "[Red: 5, Green: 5]");
            assertEquals(e.getMessage(), "Not enough of the image was the desired color.");
        }
    }

    /**
     * Tests that calling assertMostly using a Color will pass if the target color is only just the majority.
     */
    @Test
    public void testByColor_MultipleColors_ExactMajority(){
        List<ToneCount> counts = Arrays.asList(toneCount(5, "Red", Color.RED),
                                               toneCount(6, "Green", Color.GREEN));

        assertMostly(Color.GREEN, new PaletteDistribution(counts));
    }

    /**
     * Tests that calling assertMostly using a Color will pass if the target color is definitely the majority.
     */
    @Test
    public void testByColor_MultipleColors_SevereMajority(){
        List<ToneCount> counts = Arrays.asList(toneCount(1, "Red", Color.RED),
                                               toneCount(6, "Green", Color.GREEN));

        assertMostly(Color.GREEN, new PaletteDistribution(counts));
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
