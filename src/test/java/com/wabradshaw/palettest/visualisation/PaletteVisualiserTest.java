package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;
import static com.wabradshaw.palettest.utils.ImageFileUtils.save;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    /**
     * Tests that the palette visualiser can produce the reference image for a palette. This includes multiple colors,
     * some of which should have white letters and some that should have black.
     */
    @Test
    public void testDrawing(){
        List<Tone> palette = StandardPalettes.RAINBOW_BW;

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage target = ImageFileUtils.loadImageResource("/palettes/rainbow_bw.png");
        BufferedImage result = visualiser.visualise(palette, 3);

        assertPixelsMatch(target, result);
    }

    /**
     * Tests that empty cells will be drawn using alpha.
     */
    @Test
    public void testDrawingWithAlpha(){
        List<Tone> palette = StandardPalettes.RAINBOW;

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage target = ImageFileUtils.loadImageResource("/palettes/rainbow.png");
        BufferedImage result = visualiser.visualise(palette, 3);

        assertPixelsMatch(target, result);
    }

    /**
     * Tests that calling visualise with no columns will throw an exception.
     */
    @Test
    public void testNoColumns(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        PaletteVisualiser visualiser = new PaletteVisualiser();

        assertThrows(IllegalArgumentException.class, () -> visualiser.visualise(palette, 0));
    }

    /**
     * Tests that calling visualise with a negative number of columns will throw an exception.
     */
    @Test
    public void testNegativeColumns(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        PaletteVisualiser visualiser = new PaletteVisualiser();

        assertThrows(IllegalArgumentException.class, () -> visualiser.visualise(palette, -4));
    }

    //TODO - Remove
    //@Test
    private void manuallyPrintPalette(){
        List<Tone> palette = StandardPalettes.JAVA_COLORS;

        PaletteVisualiser visualiser = new PaletteVisualiser();

        BufferedImage result = visualiser.visualise(palette, 5);

        save(result, "java_colors.png", "png");
    }

}
