package com.wabradshaw.palettest.visualisation;

import com.sun.xml.internal.bind.api.impl.NameConverter;
import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Palettester;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for the {@link DistributionPainter} class.
 */
public class DistributionPainterTest {

    /**
     * Tests that using paintTones will return an image of the correct size.
     */
    @Test
    public void testPaintTones_Size(){
        DistributionPainter visualiser = new DistributionPainter();

        BufferedImage result = visualiser.paintTones(new ArrayList<>(), 10, 20);

        assertDimensions(result, 10, 20);
    }

    /**
     * Tests that using paintTones on a simple image returns an image with the same color palette.
     */
    @Test
    public void testPaintTones_Simple(){

        Palettester tester = new Palettester();

        BufferedImage original = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");
        PaletteDistribution originalDistribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, original);

        DistributionPainter visualiser = new DistributionPainter();
        BufferedImage result = visualiser.paintTones(originalDistribution.byCount(), 10, 10);
        PaletteDistribution newDistribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, result);

        assertEquals(originalDistribution, newDistribution);
    }

    /**
     * Tests that using paintTones on an image with a custom palette returns an image with the same color palette.
     */
    @Test
    public void testPaintTones_Map(){

        Palettester tester = new Palettester();

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Dark Purple", new Color(97,55,138)));
        palette.add(new Tone("Light Purple", new Color(122,61,182)));
        palette.add(new Tone("White", Color.WHITE));

        BufferedImage original = ImageFileUtils.loadImageResource("/sampleImages/maps/Barcelona.png");
        PaletteDistribution originalDistribution = new Palettester().analysePalette(palette, original);

        DistributionPainter visualiser = new DistributionPainter();
        BufferedImage result = visualiser.paintTones(originalDistribution.byCount(), 1200, 1280);
        PaletteDistribution newDistribution = tester.analysePalette(palette, result);
    }

}
