package com.wabradshaw.palettest.analysis;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
     * Tests that a null Tone is okay. In some situations we may want a null Tone to cover {@link Color}s too far from
     * the defined palette.
     */
    @Test
    public void testNullTone(){
        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(null, pixelCounts);
        assertEquals(null, toneCount.getTone());
    }

    /**
     * Tests that a null pixel count map will throw an illegal argument exception.
     */
    @Test
    public void testNullPixelCount(){
        Tone red = new Tone("red", Color.RED);

        assertThrows(IllegalArgumentException.class, () -> new ToneCount(red, null));
    }

    /**
     * Tests that a null pixel count map (and tone) will throw an illegal argument exception.
     */
    @Test
    public void testNullPixelCountAndTone(){
        assertThrows(IllegalArgumentException.class, () -> new ToneCount(null, null));
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

    /**
     * Tests that getCount will return the number of pixels of the Tone.
     */
    @Test
    public void testStandardCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(16, toneCount.getCount());
    }

    /**
     * Tests that getCount works when supplied with an empty pixelCounts map.
     */
    @Test
    public void testEmptyCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(0, toneCount.getCount());
    }

    /**
     * Tests that getCount will ignore {@link Color}s which have a null count.
     */
    @Test
    public void testNullCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, null);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(15, toneCount.getCount());
    }
}
