package com.wabradshaw.palettest.analysis;

import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for the {@link Palettester} class.
 */
public class PalettesterTest {

    /**
     * Tests analyseAllColors when the image only has a single color.
     */
    @Test
    public void testAnalyseAllColors_Monochrome(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        PaletteDistribution result = new Palettester().analyseAllColors(image);

        assertEquals(1, result.getDistribution().size());
        assertEquals("#00ff00", result.getDistribution().get(0).getTone().getName());
        assertEquals(100, result.getDistribution().get(0).getCount());
        assertEquals(1, result.getDistribution().get(0).getPixelCounts().size());
        assertEquals(100, (int) result.getDistribution().get(0).getPixelCounts().get(Color.GREEN));
    }

    /**
     * Tests analyseAllColors when the image only has two colors.
     */
    @Test
    public void testAnalyseAllColors_Simple(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        PaletteDistribution result = new Palettester().analyseAllColors(image);

        assertEquals(2, result.getDistribution().size());

        assertEquals("#ff0000", result.getDistribution().get(0).getTone().getName());
        assertEquals(75, result.getDistribution().get(0).getCount());
        assertEquals(1, result.getDistribution().get(0).getPixelCounts().size());
        assertEquals(75, (int) result.getDistribution().get(0).getPixelCounts().get(Color.RED));

        assertEquals("#0000ff", result.getDistribution().get(1).getTone().getName());
        assertEquals(25, result.getDistribution().get(1).getCount());
        assertEquals(1, result.getDistribution().get(1).getPixelCounts().size());
        assertEquals(25, (int) result.getDistribution().get(1).getPixelCounts().get(Color.BLUE));
    }

    /**
     * Tests analyseAllColors on a complex image.
     */
    @Test
    public void testAnalyseAllColors_Complex(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Barcelona.png");

        PaletteDistribution result = new Palettester().analyseAllColors(image);
        System.out.println(result.byCount());
        assertEquals(255, result.getDistribution().size());

    }
}
