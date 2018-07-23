package com.wabradshaw.palettest.analysis;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
     * Tests the get method on a name which exists in the counts.
     */
    @Test
    public void testGetByName_exists(){
        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(blue, distribution.get("blue"));
        assertEquals(red, distribution.get("red"));
        assertEquals(green, distribution.get("green"));
    }

    /**
     * Tests the get method on a name which isn't in the distribution.
     */
    @Test
    public void testGetByName_doesntExists(){
        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(null, distribution.get("octarine"));
    }

    /**
     * Tests the get method on a name which someone has accidentally been included multiple times will return the
     * largest.
     */
    @Test
    public void testGetByName_duplicate(){
        ToneCount blue1 = toneCount("blue", new Color(0,0,255), 5);
        ToneCount blue2 = toneCount("blue", new Color(0,0,200), 9);
        ToneCount blue3 = toneCount("blue", new Color(10,20,180), 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue1);
        original.add(blue2);
        original.add(blue3);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(blue2, distribution.get("blue"));
    }

    /**
     * Tests the get method on a color which exists in the counts.
     */
    @Test
    public void testGetByColor_exists(){
        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(blue, distribution.get(Color.BLUE));
        assertEquals(red, distribution.get(Color.RED));
        assertEquals(green, distribution.get(Color.GREEN));
    }

    /**
     * Tests the get method on a color which isn't in the distribution.
     */
    @Test
    public void testGetByColor_doesntExists(){
        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(null, distribution.get(Color.WHITE));
    }

    /**
     * Tests the get method on a color which someone has accidentally been included multiple times will return the
     * largest.
     */
    @Test
    public void testGetByColor_duplicate(){
        ToneCount blue1 = toneCount("bleu", Color.BLUE, 5);
        ToneCount blue2 = toneCount("blue", Color.BLUE, 9);
        ToneCount blue3 = toneCount("blau", Color.BLUE, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue1);
        original.add(blue2);
        original.add(blue3);

        PaletteDistribution distribution = new PaletteDistribution(original);

        assertEquals(blue2, distribution.get(Color.BLUE));
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

    /**
     * Tests that byName will return the original items in alphabetical order.
     */
    @Test
    public void testByName(){

        ToneCount blue = toneCount("blue", Color.BLUE, 5);
        ToneCount red = toneCount("red", Color.RED, 9);
        ToneCount green = toneCount("green", Color.GREEN, 2);

        List<ToneCount> original = new ArrayList<>();

        original.add(blue);
        original.add(red);
        original.add(green);

        PaletteDistribution distribution = new PaletteDistribution(original);

        List<ToneCount> target = new ArrayList<>();

        target.add(blue);
        target.add(green);
        target.add(red);

        assertEquals(target, distribution.byName());
    }

    /**
     * Tests the ToString method returns something sensible.
     */
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
     * Tests that the equals method will return false when passed a null.
     */
    @Test
    public void testEquals_null(){
        List<ToneCount> counts = new ArrayList<>();

        counts.add(toneCount("blue", Color.BLUE, 5));
        counts.add(toneCount("red", Color.RED, 9));
        counts.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(counts);

        assertFalse(distribution.equals(null));
    }

    /**
     * Tests that the equals method will return false when passed a different object type.
     */
    @Test
    public void testEquals_differentType(){
        List<ToneCount> counts = new ArrayList<>();

        counts.add(toneCount("blue", Color.BLUE, 5));
        counts.add(toneCount("red", Color.RED, 9));
        counts.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(counts);

        assertFalse(distribution.equals("[red: 9, blue: 5, green: 2]"));
    }

    /**
     * Tests that the equals method will return true when passed itself.
     */
    @Test
    public void testEquals_identity(){
        List<ToneCount> counts = new ArrayList<>();

        counts.add(toneCount("blue", Color.BLUE, 5));
        counts.add(toneCount("red", Color.RED, 9));
        counts.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution = new PaletteDistribution(counts);

        assertTrue(distribution.equals(distribution));
    }

    /**
     * Tests that the equals method will return true when passed the same tone counts.
     */
    @Test
    public void testEquals_equivalent(){
        List<ToneCount> counts = new ArrayList<>();

        counts.add(toneCount("blue", Color.BLUE, 5));
        counts.add(toneCount("red", Color.RED, 9));
        counts.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts);
        PaletteDistribution distribution2 = new PaletteDistribution(counts);

        assertTrue(distribution1.equals(distribution2));
    }

    /**
     * Tests that the equals method will return true when passed the same tone counts in
     * a different list.
     */
    @Test
    public void testEquals_equivalentDifferentObjects(){
        List<ToneCount> counts1 = new ArrayList<>();

        counts1.add(toneCount("blue", Color.BLUE, 5));
        counts1.add(toneCount("red", Color.RED, 9));
        counts1.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts1);

        List<ToneCount> counts2 = new ArrayList<>();

        counts2.add(toneCount("blue", Color.BLUE, 5));
        counts2.add(toneCount("red", Color.RED, 9));
        counts2.add(toneCount("green", Color.GREEN, 2));
        PaletteDistribution distribution2 = new PaletteDistribution(counts2);

        assertTrue(distribution1.equals(distribution2));
    }

    /**
     * Tests that the equals method will return true when passed the same tone counts in
     * a differently ordered list.
     */
    @Test
    public void testEquals_equivalentDifferentOrder(){
        List<ToneCount> counts1 = new ArrayList<>();

        counts1.add(toneCount("blue", Color.BLUE, 5));
        counts1.add(toneCount("red", Color.RED, 9));
        counts1.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts1);

        List<ToneCount> counts2 = new ArrayList<>();

        counts2.add(toneCount("green", Color.GREEN, 2));
        counts2.add(toneCount("blue", Color.BLUE, 5));
        counts2.add(toneCount("red", Color.RED, 9));
        PaletteDistribution distribution2 = new PaletteDistribution(counts2);

        assertTrue(distribution1.equals(distribution2));
    }

    /**
     * Tests that the equals method will return false if the counts don't match up.
     */
    @Test
    public void testEquals_differentCounts(){
        List<ToneCount> counts1 = new ArrayList<>();

        counts1.add(toneCount("blue", Color.BLUE, 5));
        counts1.add(toneCount("red", Color.RED, 9));
        counts1.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts1);

        List<ToneCount> counts2 = new ArrayList<>();

        counts2.add(toneCount("green", Color.GREEN, 6));
        counts2.add(toneCount("blue", Color.BLUE, 1));
        counts2.add(toneCount("red", Color.RED, 3));
        PaletteDistribution distribution2 = new PaletteDistribution(counts2);

        assertFalse(distribution1.equals(distribution2));
    }

    /**
     * Tests that the hashcode method will return the same hash for equivalent objects.
     */
    @Test
    public void testHashCode_same(){
        List<ToneCount> counts1 = new ArrayList<>();

        counts1.add(toneCount("blue", Color.BLUE, 5));
        counts1.add(toneCount("red", Color.RED, 9));
        counts1.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts1);

        List<ToneCount> counts2 = new ArrayList<>();

        counts2.add(toneCount("green", Color.GREEN, 2));
        counts2.add(toneCount("blue", Color.BLUE, 5));
        counts2.add(toneCount("red", Color.RED, 9));
        PaletteDistribution distribution2 = new PaletteDistribution(counts2);

        assertEquals(distribution1.hashCode(), distribution2.hashCode());
    }

    /**
     * Tests that the equals method will return different hashes for different objects.
     */
    @Test
    public void testHashCode_different(){
        List<ToneCount> counts1 = new ArrayList<>();

        counts1.add(toneCount("blue", Color.BLUE, 5));
        counts1.add(toneCount("red", Color.RED, 9));
        counts1.add(toneCount("green", Color.GREEN, 2));

        PaletteDistribution distribution1 = new PaletteDistribution(counts1);

        List<ToneCount> counts2 = new ArrayList<>();

        counts2.add(toneCount("green", Color.GREEN, 6));
        counts2.add(toneCount("blue", Color.BLUE, 1));
        counts2.add(toneCount("red", Color.RED, 3));
        PaletteDistribution distribution2 = new PaletteDistribution(counts2);

        assertNotEquals(distribution1.hashCode(), distribution2.hashCode());
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
