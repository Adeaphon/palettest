package com.wabradshaw.palettest.analysis;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * A set of tests for the {@link ToneCount} object.
 */
public class ToneCountTest {

    /**
     * Tests that the main constructor will store the arguments it was created with.
     */
    @Test
    public void testConstructor(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(red, toneCount.getTone());
        assertEquals(pixelCounts, toneCount.getPixelCounts());
    }

    /**
     * Tests that changes to the pixel count used in the constructor are not reflected in the ToneCount.
     */
    @Test
    public void testSetPixelCountsImmutable(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        pixelCounts.put(Color.magenta, 4);

        assertEquals(3, toneCount.getPixelCounts().size());
        assertNotEquals(pixelCounts, toneCount.getPixelCounts());
    }



    /**
     * Tests that changes to the pixel count produced by the getter method are not reflected in the ToneCount.
     */
    @Test
    public void testGetterPixelCountsImmutable(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        Map<Color, Integer> gotPixelCounts = toneCount.getPixelCounts();
        gotPixelCounts.put(Color.magenta, 4);

        assertEquals(3, toneCount.getPixelCounts().size());
        assertNotEquals(gotPixelCounts, toneCount.getPixelCounts());
    }
}
