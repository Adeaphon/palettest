package com.wabradshaw.palettest.analysis;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A set of test methods for the {@link PaletteDistribution} class.
 */
public class PaletteDistributionTest {

    /**
     * Tests that getDistribution will return the original items in the same order.
     */
    @Test
    public void testGetDistribution(){

        List<ToneCount> original = new ArrayList<>();

        original.add(toneCount("blue", Color.BLUE, 5));
        original.add(toneCount("red", Color.RED, 9));
        original.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(original, distribution.getDistribution());
    }

    /**
     * Tests that creating a PaletteDistribution with a null list of ToneCounts will throw an exception.
     */
    @Test
    public void testNullConstructor(){
        assertThrows(IllegalArgumentException.class, () -> new PaletteDistribution(null));
    }

    /**
     * Tests that changes made to the list of ToneCounts used to instantiate the object will not be respected.
     */
    @Test
    public void testSetDistributionImmutable(){

        List<ToneCount> original = new ArrayList<>();

        original.add(toneCount("blue", Color.BLUE, 5));
        original.add(toneCount("red", Color.RED, 9));
        original.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(original);

        original.add(toneCount("purple", Color.MAGENTA, 1));

        assertNotEquals(original, distribution.getDistribution());
    }

    /**
     * Tests that changes made to a list of ToneCounts from getDistribution will not be respected.
     */
    @Test
    public void testGetDistributionImmutable(){

        List<ToneCount> original = new ArrayList<>();

        original.add(toneCount("blue", Color.BLUE, 5));
        original.add(toneCount("red", Color.RED, 9));
        original.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(original);

        List<ToneCount> firstGet = distribution.getDistribution();
        firstGet.add(toneCount("purple", Color.MAGENTA, 1));

        assertNotEquals(firstGet, distribution.getDistribution());
    }

    /**
     * Tests that byCount will return the original items in count order.
     */
    @Test
    public void testByCount(){

        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        List<ToneCount> target = new ArrayList<>();

        target.add(red);
        target.add(blue);
        target.add(green);

        assertEquals(target, distribution.byCount());
    }

    @Test
    public void testToString(){
        List<ToneCount> original = new ArrayList<>();

        original.add(toneCount("blue", Color.BLUE, 5));
        original.add(toneCount("red", Color.RED, 9));
        original.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals("[blue: 5, red: 9, green: 2]", distribution.toString());
    }

    /**
     * A mock method to make it easier to create a ToneCount during testing.
     *
     * @param name  The name of the tone.
     * @param color The color of the tone.
     * @param count How many times it apparently appeared.
     * @return      A ToneCount containing the supplied information.
     */
    private ToneCount toneCount(String name, Color color, int count){
        Tone tone = new Tone(name, color);
        Map<Color, Integer> map = new HashMap<>();
        map.put(color, count);
        return new ToneCount(tone, map);
    }
}
