package com.wabradshaw.palettest.analysis.naming;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import org.junit.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A set of tests for the {@link SimplePaletteColorNamer}.
 */
public class SimplePaletteColorNamerTest {

    /**
     * Tests that a color that exactly matches one in the base palette will be used as a name.
     */
    @Test
    public void testOneColor_Matching(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(Color.RED);
        List<Tone> basePalette = Arrays.asList(new Tone("Red", Color.RED));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(1, result.size());
        assertEquals("Red", result.get(0).getName());
    }

    /**
     * Tests that a color that is close to one in the base palette will be used as a name.
     */
    @Test
    public void testOneColor_Close(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(new Color(200, 50, 50));
        List<Tone> basePalette = Arrays.asList(new Tone("Red", new Color(210, 45, 55)));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(1, result.size());
        assertEquals("Red", result.get(0).getName());
    }

    /**
     * Tests that a color that is a long way from any in the base palette will be ignored, and a default name will be
     * used.
     */
    @Test
    public void testOneColor_Far(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(Color.BLUE);
        List<Tone> basePalette = Arrays.asList(new Tone("Red", Color.RED));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(1, result.size());
        assertEquals("#0000ff", result.get(0).getName());
    }


    /**
     * Tests that the closest color in the base palette will be used as a name.
     */
    @Test
    public void testOneColor_Closest(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(new Color(200, 70, 0));

        List<Tone> basePalette = Arrays.asList(new Tone("Scarlet", new Color(210, 45, 55)),
                                               new Tone("Dark Red", new Color(150, 0, 0)),
                                               new Tone("Similar Red", new Color(205, 69, 0)));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(1, result.size());
        assertEquals("Similar Red", result.get(0).getName());
    }

    /**
     * Tests that multiple colors (which are different from each other) will each get their own name.
     */
    @Test
    public void testMultipleColors_Distinct(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN);
        List<Tone> basePalette = Arrays.asList(new Tone("Green", Color.GREEN),
                                               new Tone("Blue", Color.BLUE),
                                               new Tone("Red", Color.RED));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(3, result.size());
        assertEquals("Red", result.get(0).getName());
        assertEquals("Blue", result.get(1).getName());
        assertEquals("Green", result.get(2).getName());
    }

    /**
     * Tests that multiple colors (which are different from each other) will each get their own name.
     */
    @Test
    public void testMultipleColors_Clashes(){
        ColorNamer namer = new SimplePaletteColorNamer();

        Collection<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.GREEN, Color.GREEN);
        List<Tone> basePalette = Arrays.asList(new Tone("Green", Color.GREEN),
                new Tone("Blue", Color.BLUE),
                new Tone("Red", Color.RED));

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(6, result.size());
        assertEquals("Red", result.get(0).getName());
        assertEquals("Blue", result.get(1).getName());
        assertEquals("Green", result.get(2).getName());
        assertEquals("Red2", result.get(3).getName());
        assertEquals("Green2", result.get(4).getName());
        assertEquals("Green3", result.get(5).getName());
    }

    /**
     * Tests that the distance function constructor will actually use the supplied distance function.
     *
     * Done by forcing the distance function to think that blue is closer to red than red is.
     */
    @Test
    public void testDistanceFunctionConstructor(){
        ColorDistanceFunction distanceFunction = mock(ColorDistanceFunction.class);

        ColorNamer namer = new SimplePaletteColorNamer(distanceFunction);

        Collection<Color> colors = Arrays.asList(Color.RED);
        List<Tone> basePalette = Arrays.asList(new Tone("Red", Color.RED), new Tone("Blue", Color.BLUE));

        when(distanceFunction.getDistance(eq(new Tone(Color.RED)), eq(new Tone(Color.RED)))).thenReturn(99.9);
        when(distanceFunction.getDistance(eq(new Tone(Color.RED)), eq(new Tone(Color.BLUE)))).thenReturn(0.0);

        List<Tone> result = namer.nameTones(colors, basePalette);

        assertEquals(1, result.size());
        assertEquals("Blue", result.get(0).getName());
    }
}
