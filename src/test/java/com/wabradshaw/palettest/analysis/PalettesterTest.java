package com.wabradshaw.palettest.analysis;

import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A set of tests for the {@link Palettester} class.
 */
public class PalettesterTest {

    /**
     * Tests analysePalette when the image only has a single color which is in the palette.
     */
    @Test
    public void testAnalysePalette_OneColor(){

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Red", Color.RED));
        palette.add(new Tone("Green", Color.GREEN));
        palette.add(new Tone("Blue", Color.BLUE));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        PaletteDistribution result = new Palettester().analysePalette(palette, image);

        assertEquals(1, result.byCount().size());
        assertEquals("Green", result.byCount().get(0).getTone().getName());
        assertEquals(100, result.byCount().get(0).getCount());
        assertEquals(1, result.byCount().get(0).getPixelCounts().size());
        assertEquals(100, (int) result.byCount().get(0).getPixelCounts().get(Color.GREEN));
    }

    /**
     * Tests analysePalette on an image containing a black/white gradient. The image has five bands, three closer to
     * white, two closer to black.
     */
    @Test
    public void testAnalysePalette_Greyscale(){

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Black", Color.BLACK));
        palette.add(new Tone("White", Color.WHITE));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/simple/blackWhiteGradient.png");

        PaletteDistribution result = new Palettester().analysePalette(palette, image);

        assertEquals(2, result.byCount().size());

        ToneCount firstColor = result.byCount().get(0);
        assertEquals("White", firstColor.getTone().getName());
        assertEquals(60, firstColor.getCount());
        assertEquals(3, firstColor.getPixelCounts().size());
        assertEquals(20, (int) firstColor.getPixelCounts().get(Color.WHITE));
        assertEquals(20, (int) firstColor.getPixelCounts().get(new Color(191,191,191)));
        assertEquals(20, (int) firstColor.getPixelCounts().get(new Color(150,150,150)));

        ToneCount secondColor = result.byCount().get(1);
        assertEquals("Black", secondColor.getTone().getName());
        assertEquals(40, secondColor.getCount());
        assertEquals(2, secondColor.getPixelCounts().size());
        assertEquals(20, (int) secondColor.getPixelCounts().get(Color.BLACK));
        assertEquals(20, (int) secondColor.getPixelCounts().get(new Color(124,124,124)));
    }

    /**
     * Tests analysePalette on a complex image with a custom palette. The image is mostly light purple, dark purple
     * and white. There is no red. Analysing the pixels independently gives a huge count of subtly different shades.
     */
    @Test
    public void testAnalysePalette_Complex(){

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Dark Purple", new Color(97,55,138)));
        palette.add(new Tone("Light Purple", new Color(122,61,182)));
        palette.add(new Tone("White", Color.WHITE));
        palette.add(new Tone("Red", Color.RED));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Barcelona.png");

        PaletteDistribution result = new Palettester().analysePalette(palette, image);

        assertEquals(3, result.byCount().size());
        assertEquals("Light Purple", result.byCount().get(0).getTone().getName());
        assertEquals("Dark Purple", result.byCount().get(1).getTone().getName());
        assertEquals("White", result.byCount().get(2).getTone().getName());
    }

    /**
     * Tests analysePalette using the default palette when the image only has a single color.
     */
    @Test
    public void testAnalysePalette_Default_OneColor(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        PaletteDistribution result = new Palettester().analysePalette(image);

        assertEquals(1, result.byCount().size());
        assertEquals("Green", result.byCount().get(0).getTone().getName());
        assertEquals(100, result.byCount().get(0).getCount());
        assertEquals(1, result.byCount().get(0).getPixelCounts().size());
        assertEquals(100, (int) result.byCount().get(0).getPixelCounts().get(Color.GREEN));
    }

    /**
     * Tests analysePalette using the default palette on an image containing a black/white gradient. The image has five
     * equally spaced bands. In the PWG color palette, these map to four different colors: White, Black, Silver, and
     * Light Black x 2.
     */
    @Test
    public void testAnalysePalette_Default_Greyscale(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/simple/blackWhiteGradient.png");

        PaletteDistribution result = new Palettester().analysePalette(image);

        List<ToneCount> results = result.byName();
        assertEquals(4, results.size());
        assertEquals("Black", results.get(0).getTone().getName());
        assertEquals("Light Black", results.get(1).getTone().getName());
        assertEquals("Silver", results.get(2).getTone().getName());
        assertEquals("White", results.get(3).getTone().getName());
    }

    /**
     * Tests analysePalette using the default palette on a complex image with a custom palette. The image is mostly
     * light purple, dark purple and white. Analysing the pixels independently gives a huge count of subtly different
     * shades.
     */
    @Test
    public void testAnalysePalette_Default_Complex(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Barcelona.png");

        PaletteDistribution result = new Palettester().analysePalette(image);

        assertEquals(10, result.byCount().size());
    }

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

        assertEquals(255, result.getDistribution().size());

    }

    /**
     * Tests definePalette on an image only containing one color
     */
    @Test
    public void testDefinePalette_OneColor(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        List<Tone> result = new Palettester().definePalette(image, 1);

        assertEquals(1, result.size());
        assertEquals(Color.GREEN, result.get(0).getColor());
    }

    /**
     * Tests definePalette on a trivial image (equal parts red and blue), asking for a single cluster (so it should be
     * purple).
     */
    @Test
    public void testDefinePalette_Trivial_Fewer(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        List<Tone> result = new Palettester().definePalette(image, 1);

        assertEquals(1, result.size());
        assertEquals(new Color(127, 0, 127), result.get(0).getColor());
    }

    /**
     * Tests definePalette on a trivial image (equal parts red and blue), asking for two clusters (i.e. red and blue).
     */
    @Test
    public void testDefinePalette_Trivial_Equal(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        List<Tone> result = new Palettester().definePalette(image, 2);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Tone(Color.RED)));
        assertTrue(result.contains(new Tone(Color.BLUE)));
    }

    /**
     * Tests definePalette on a trivial image (equal parts red and blue), asking for several clusters. Only the
     * present colors should appear.
     */
    @Test
    public void testDefinePalette_Trivial_TooMany(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        List<Tone> result = new Palettester().definePalette(image, 20);

        assertEquals(2, result.size());
        assertTrue(result.contains(new Tone(Color.RED)));
        assertTrue(result.contains(new Tone(Color.BLUE)));
    }

    /**
     * Tests definePalette on a simple image, which has about four main colors (white and three shades of green).
     */
    @Test
    public void testDefinePalette_Simple(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Seville.png");

        List<Tone> result = new Palettester().definePalette(image, 4);

        assertEquals(4, result.size());
    }

    /**
     * Tests definePalette on a simple image, which has about four main colors (white and three shades of green). This
     * checks that the defined palette is reflected in the image.
     */
    @Test
    public void testDefinePalette_Simple_ReflectsReality(){
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Seville.png");

        Palettester tester = new Palettester();
        List<Tone> palette = tester.definePalette(image, 4);
        PaletteDistribution result = tester.analysePalette(palette, image);

        result.byCount().stream().forEach(
                x -> assertTrue(x.getAverageDistance(new EuclideanRgbaDistance()) < 50)
        );
    }

    /**
     * Tests definePalette on a photograph.
     */
    @Test
    public void testDefinePalette_Complex() {
        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/complex/rooves.jpg");

        List<Tone> result = new Palettester().definePalette(image, 10);

        assertEquals(10, result.size());
    }
}
