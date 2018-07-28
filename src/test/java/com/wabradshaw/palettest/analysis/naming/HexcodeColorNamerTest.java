package com.wabradshaw.palettest.analysis.naming;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for the {@link HexcodeColorNamer}.
 */
public class HexcodeColorNamerTest {

    /**
     * Tests that a single color can be named.
     */
    @Test
    public void testOneColor(){
        ColorNamer namer = new HexcodeColorNamer();

        Collection<Color> colors = Arrays.asList(Color.RED);

        List<Tone> result = namer.nameTones(colors, null);

        assertEquals(1, result.size());
        assertEquals("#ff0000", result.get(0).getName());
    }

    /**
     * Tests that multiple colors can be named.
     */
    @Test
    public void testMultipleColors(){
        ColorNamer namer = new HexcodeColorNamer();

        Collection<Color> colors = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE);

        List<Tone> result = namer.nameTones(colors, null);

        assertEquals(3, result.size());
        assertEquals("#ff0000", result.get(0).getName());
        assertEquals("#00ff00", result.get(1).getName());
        assertEquals("#0000ff", result.get(2).getName());
    }

}
