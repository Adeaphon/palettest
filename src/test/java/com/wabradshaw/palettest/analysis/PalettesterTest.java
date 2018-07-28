package com.wabradshaw.palettest.analysis;

import com.wabradshaw.palettest.analysis.clustering.ClusteringAlgorithm;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;
import com.wabradshaw.palettest.analysis.naming.ColorNamer;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertContainsColor.assertContainsColor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.*;

/**
 * A set of tests for the {@link Palettester} class.
 */
public class PalettesterTest {

    /**
     * Tests that the default palette constructor will use that palette as the default.
     *
     * Done by checking that analysePalette on a green image can be forced to use a named red palette.
     */
    @Test
    public void testDefaultPaletteConstructor(){

        List<Tone> palette = Arrays.asList(new Tone("Rouge", Color.RED));
        Palettester tester = new Palettester(palette);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        PaletteDistribution result = tester.analysePalette(image);

        assertEquals(1, result.byCount().size());
        assertContainsColor("Rouge", result);
    }

    /**
     * Tests that the distance function constructor will use that distance function.
     *
     * Done by checking that analysePalette on a light red pixel can be forced to return blue.
     */
    @Test
    public void testDistanceFunctionConstructor(){
        ColorDistanceFunction distanceFunction = mock(ColorDistanceFunction.class);
        when(distanceFunction.getRankingDistance(eq(new Tone(Color.RED)),any())).thenReturn(999.9);
        when(distanceFunction.getRankingDistance(eq(new Tone(Color.BLUE)),any())).thenReturn(1.0);

        Palettester tester = new Palettester(distanceFunction);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/1x1.png");

        List<Tone> palette = Arrays.asList(new Tone("Red",  Color.red), new Tone("Blue",  Color.blue));
        PaletteDistribution result = tester.analysePalette(palette, image);

        assertEquals(1, result.byName().size());
        assertContainsColor(Color.BLUE, result);
    }

    /**
     * Tests that the default palette will be used if the custom constructor is called without a palette.
     *
     * Done by testing analysePalette using the default palette on an image containing a black/white gradient. The image
     * has five equally spaced bands. In the PWG color palette, these map to four different colors: White, Black,
     * Silver, and Light Black x 2.
     */
    @Test
    public void testCustomConstructor_NoPalette(){
        Palettester tester = new Palettester(null, null, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/simple/blackWhiteGradient.png");

        PaletteDistribution result = tester.analysePalette(image);

        assertEquals(4, result.byCount().size());
        assertContainsColor("Black", result);
        assertContainsColor("Light Black", result);
        assertContainsColor("Silver", result);
        assertContainsColor("White", result);
    }

    /**
     * Tests that a custom palette can be set as the default palette for a Palettester.
     *
     * Done by testing analysePalette using a custom palette on an image containing a black/white gradient. The image
     * has five equally spaced bands. However, the custom palette only has one color, Rouge.
     */
    @Test
    public void testCustomConstructor_CustomPalette(){
        List<Tone> palette = Arrays.asList(new Tone("Rouge", Color.RED));
        Palettester tester = new Palettester(palette, null, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/simple/blackWhiteGradient.png");

        PaletteDistribution result = tester.analysePalette(image);

        assertEquals(1, result.byCount().size());
        assertContainsColor("Rouge", result);
    }

    /**
     * Tests that the default distance function will be used if the custom constructor is called without a distance
     * function.
     *
     * Done by checking that analysePalette on a light red pixel returns red.
     */
    @Test
    public void testCustomConstructor_NoDistanceFunction(){
        Palettester tester = new Palettester(null, null, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/1x1.png");

        List<Tone> palette = Arrays.asList(new Tone("Red",  Color.red), new Tone("Blue",  Color.blue));
        PaletteDistribution result = tester.analysePalette(palette, image);

        assertEquals(1, result.byCount().size());
        assertContainsColor("Red", result);
    }

    /**
     * Tests that a custom distance function will be used if the custom constructor is called with it.
     *
     * Done by checking that analysePalette on a light red pixel can be forced to return blue.
     */
    @Test
    public void testCustomConstructor_CustomDistanceFunction(){
        ColorDistanceFunction distanceFunction = mock(ColorDistanceFunction.class);
        when(distanceFunction.getRankingDistance(eq(new Tone(Color.RED)),any())).thenReturn(999.9);
        when(distanceFunction.getRankingDistance(eq(new Tone(Color.BLUE)),any())).thenReturn(1.0);

        Palettester tester = new Palettester(null, distanceFunction, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/1x1.png");

        List<Tone> palette = Arrays.asList(new Tone("Red",  Color.red), new Tone("Blue",  Color.blue));
        PaletteDistribution result = tester.analysePalette(palette, image);

        assertEquals(1, result.byCount().size());
        assertContainsColor("Blue", result);
    }

    /**
     * Tests that a custom distance function will be used by the clustering algorithm.
     *
     * Because this is a heuristic based algorithm, it's difficult to predict exact behaviour with the distance
     * function, so a mockito spy is used to validate it's being called.
     */
    @Test
    public void testCustomConstructor_CustomDistanceFunction_UsedForClusterer(){
        ColorDistanceFunction distanceFunction = spy(new EuclideanRgbaDistance());

        Palettester tester = new Palettester(null, distanceFunction, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/maps/Barcelona.png");

        tester.definePalette(image, 4);

        verify(distanceFunction, atLeastOnce()).getDistance(any(), any());
    }

    /**
     * Tests that a custom distance function will be used by the naming algorithm.
     *
     * Done by getting the namer to incorrectly call a red pixel blue.
     */
    @Test
    public void testCustomConstructor_CustomDistanceFunction_UsedForNameer(){
        ColorDistanceFunction distanceFunction = mock(ColorDistanceFunction.class);
        when(distanceFunction.getDistance(not(eq(new Tone(Color.BLUE))), any())).thenReturn(999.9);
        when(distanceFunction.getDistance(eq(new Tone(Color.BLUE)), any())).thenReturn(1.0);

        Palettester tester = new Palettester(null, distanceFunction, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/red.png");

        List<Tone> results = tester.definePalette(image, 1);

        assertEquals(1, results.size());
        assertEquals("Blue", results.get(0).getName());
    }

    /**
     * Tests that the default clustering algorithm will be used if the custom constructor is called without a clustering
     * algorithm.
     *
     * Done by checking that definePalette (one color) on an image which is half red, half blue will return purple.
     */
    @Test
    public void testCustomConstructor_NoClusteringAlgorithm(){
        Palettester tester = new Palettester(null, null, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        List<Tone> results = tester.definePalette(image, 1);

        assertEquals(1, results.size());
        assertEquals(new Color(127, 0, 127), results.get(0).getColor());
    }

    /**
     * Tests that a custom clustering algorithm will be used if the custom constructor is called with it.
     *
     * Done by checking that definePalette (one color) on an image which is half red, half blue can be forced to
     * return purple.
     */
    @Test
    public void testCustomConstructor_CustomClusteringAlgorithm(){
        ClusteringAlgorithm clusterer = mock(ClusteringAlgorithm.class);
        when(clusterer.cluster(any(), eq(1))).thenReturn(Arrays.asList(Color.GREEN));

        Palettester tester = new Palettester(null, null, clusterer, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueHorizontal.png");

        List<Tone> results = tester.definePalette(image, 1);

        assertEquals(1, results.size());
        assertEquals(Color.GREEN, results.get(0).getColor());
    }

    /**
     * Tests that the default color namer will be used if the custom constructor is called without a color namer.
     *
     * Done by checking that definePalette (one color) on an image which is red, will return a color called red.
     */
    @Test
    public void testCustomConstructor_NoNamer(){
        Palettester tester = new Palettester(null, null, null, null);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/red.png");

        List<Tone> results = tester.definePalette(image, 1);

        assertEquals(1, results.size());
        assertEquals("Red", results.get(0).getName());
    }

    /**
     * Tests that a custom color namer will be used if the custom constructor is called with it.
     *
     * Done by checking that definePalette (one color) on an image which is red, can be forced to call it something
     * else.
     */
    @Test
    public void testCustomConstructor_CustomNamer(){
        ColorNamer namer = mock(ColorNamer.class);
        when(namer.nameTones(any(), any())).thenReturn(Arrays.asList(new Tone("Octarine", Color.RED)));

        Palettester tester = new Palettester(null, null, null, namer);

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/red.png");

        List<Tone> results = tester.definePalette(image, 1);

        assertEquals(1, results.size());
        assertEquals("Octarine", results.get(0).getName());
    }

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
        assertEquals(100, result.get("Green").getCount());
        assertEquals(1, result.get("Green").getPixelCounts().size());
        assertEquals(100, (int) result.get("Green").getPixelCounts().get(Color.GREEN));
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

        ToneCount white = result.get("White");
        assertEquals(60, white.getCount());
        assertEquals(3, white.getPixelCounts().size());
        assertEquals(20, (int) white.getPixelCounts().get(Color.WHITE));
        assertEquals(20, (int) white.getPixelCounts().get(new Color(191,191,191)));
        assertEquals(20, (int) white.getPixelCounts().get(new Color(150,150,150)));

        ToneCount black = result.get("Black");
        assertEquals(40, black.getCount());
        assertEquals(2, black.getPixelCounts().size());
        assertEquals(20, (int) black.getPixelCounts().get(Color.BLACK));
        assertEquals(20, (int) black.getPixelCounts().get(new Color(124,124,124)));
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
        assertEquals(100, result.get("Green").getCount());
        assertEquals(1, result.get("Green").getPixelCounts().size());
        assertEquals(100, (int) result.get("Green").getPixelCounts().get(Color.GREEN));
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
        assertContainsColor("Black", result);
        assertContainsColor("Light Black", result);
        assertContainsColor("Silver", result);
        assertContainsColor("White", result);
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

        assertEquals(11, result.byCount().size());
    }

    /**
     * Tests analyseAllColors when the image only has a single color.
     */
    @Test
    public void testAnalyseAllColors_Monochrome(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/green.png");

        PaletteDistribution result = new Palettester().analyseAllColors(image);

        assertEquals(1, result.getDistribution().size());
        assertEquals(100, result.get("#00ff00").getCount());
        assertEquals(1, result.get("#00ff00").getPixelCounts().size());
        assertEquals(100, (int) result.get("#00ff00").getPixelCounts().get(Color.GREEN));
    }

    /**
     * Tests analyseAllColors when the image only has two colors.
     */
    @Test
    public void testAnalyseAllColors_Simple(){

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        PaletteDistribution result = new Palettester().analyseAllColors(image);

        assertEquals(2, result.getDistribution().size());

        assertEquals(75, result.get("#ff0000").getCount());
        assertEquals(1, result.get("#ff0000").getPixelCounts().size());
        assertEquals(75, (int) result.get("#ff0000").getPixelCounts().get(Color.RED));

        assertEquals(25, result.get("#0000ff").getCount());
        assertEquals(1, result.get("#0000ff").getPixelCounts().size());
        assertEquals(25, (int) result.get("#0000ff").getPixelCounts().get(Color.BLUE));
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

        result.byCount().forEach(
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
