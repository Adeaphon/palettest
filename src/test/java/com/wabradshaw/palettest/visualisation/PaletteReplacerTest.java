package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Palettester;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for the {@link PaletteVisualiser} class.
 */
public class PaletteReplacerTest {

    /**
     * Tests that the image returned is the same size as the input image.
     */
    @Test
    public void testSameSize(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/2x4.png");

        PaletteReplacer replacer = new PaletteReplacer();

        BufferedImage result = replacer.replace(image, palette);

        assertDimensions(result, 2, 4);
    }

    /**
     * Tests that replacing an image with a single color returns an image with a single color.
     */
    @Test
    public void testSingleColor(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/2x4.png");

        PaletteReplacer replacer = new PaletteReplacer();

        BufferedImage result = replacer.replace(image, palette);

        PaletteDistribution analysis = new Palettester().analyseAllColors(result);

        assertEquals(1, analysis.byCount().size());
        assertEquals(palette.get(0), analysis.byCount().get(0).getTone());
    }

    /**
     * Tests replacing an image with two colors, using two similar but not identical colors.
     */
    @Test
    public void testTrivialReplacement(){
        List<Tone> palette = Arrays.asList(new Tone(new Color(200,10,10)),
                                           new Tone(new Color(10,200,10)),
                                           new Tone(new Color(10,10,200)));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        PaletteReplacer replacer = new PaletteReplacer();

        BufferedImage result = replacer.replace(image, palette);

        PaletteDistribution analysis = new Palettester().analyseAllColors(result);

        assertEquals(2, analysis.byCount().size());
        assertEquals(palette.get(0), analysis.byCount().get(0).getTone());
        assertEquals(palette.get(2), analysis.byCount().get(1).getTone());
    }

    /**
     * Tests replacing a complex image.
     */
    @Test
    public void testComplex(){
        List<Tone> palette = StandardPalettes.PWG_STANDARD;

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/complex/rooves.jpg");

        PaletteReplacer replacer = new PaletteReplacer();

        BufferedImage result = replacer.replace(image, palette);

        BufferedImage target = ImageFileUtils.loadImageResource("/resultImages/paletteReplacer/pwgRooves.png");

        assertPixelsMatch(target, result);
    }
}
