package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;

/**
 * A set of tests for the {@link PaletteVisualiser} class.
 */
public class PaletteVisualiserTest {

    /**
     * Tests that the image returned when used on a single color in one column will be just that single cell.
     */
    @Test
    public void testSize_One_ExactFit(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage result = visualiser.visualise(palette, 1);

        assertDimensions(result, 100, 50);
    }

    /**
     * Tests that the image returned when used on six colors in two columns will be equivalent to three rows of two.
     */
    @Test
    public void testSize_Six_ExactFit(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red),    new Tone(Color.blue),
                                           new Tone(Color.green),  new Tone(Color.yellow),
                                           new Tone(Color.orange), new Tone(Color.magenta));

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage result = visualiser.visualise(palette, 2);

        assertDimensions(result, 200, 150);
    }


    /**
     * Tests that the image returned when used on five colors in two columns will be equivalent to three rows of two.
     */
    @Test
    public void testSize_Five_UnevenFit(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red),    new Tone(Color.blue),
                                           new Tone(Color.green),  new Tone(Color.yellow),
                                           new Tone(Color.orange));

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage result = visualiser.visualise(palette, 2);

        assertDimensions(result, 200, 150);
    }

    /**
     * Tests that setting columnHeight and rowWidth will be respected.
     */
    @Test
    public void testSize_DefinedDimensions(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red),    new Tone(Color.blue),
                                           new Tone(Color.green),  new Tone(Color.yellow),
                                           new Tone(Color.orange), new Tone(Color.magenta));

        PaletteVisualiser visualiser = new PaletteVisualiser(11, 13);

        BufferedImage result = visualiser.visualise(palette, 2);

        assertDimensions(result, 26, 33);
    }
}
