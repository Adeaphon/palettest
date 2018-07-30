package com.wabradshaw.palettest.examples;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Palettester;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.assertions.AssertDimensions;
import com.wabradshaw.palettest.assertions.AssertPixelsMatch;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import com.wabradshaw.palettest.visualisation.DistributionPainter;
import com.wabradshaw.palettest.visualisation.PaletteReplacer;
import com.wabradshaw.palettest.visualisation.PaletteVisualiser;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wabradshaw.palettest.assertions.AssertContainsColor.assertContainsColor;
import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertMainColor.assertMainColor;
import static com.wabradshaw.palettest.assertions.AssertMostly.assertMostly;
import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of tests showing the code used in the examples. Validation is after the comment.
 */
public class ExamplesTest {

    /**
     * A test for the example showing people how {@link ImageFileUtils#save(BufferedImage, String, String)} works.
     */
    @Test
    public void saveExampleTest(){

        // Example

        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        ImageFileUtils.save(myImage, "src/test/resources/resultImages/examples/saveExample.png", "png");

        // Validation

        BufferedImage result = ImageFileUtils.loadImageResource("/resultImages/examples/saveExample.png");
        assertDimensions(result, 100, 50);

    }

    /**
     * A test for the example showing people how {@link ImageFileUtils#loadImageResource(String)} works.
     */
    @Test
    public void loadExampleTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        // Validation

        assertDimensions(exampleImage, 100, 50);
    }

    /**
     * A test for the example showing people how {@link AssertDimensions#assertDimensions(BufferedImage, int, int)}
     * works.
     */
    @Test
    public void assertDimensionsTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        assertDimensions(exampleImage, 100, 50);

        // Validation

        // None needed, already doing the assert.
    }

    /**
     * A test for the example showing people how
     * {@link AssertPixelsMatch#assertPixelsMatch(BufferedImage, BufferedImage)} works.
     */
    @Test
    public void assertPixelsMatchTest(){

        // Example

        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        assertPixelsMatch(exampleImage, myImage);

        // Validation

        // None needed, already doing the assert.

    }

    /**
     * A test for the example showing people how {@link Palettester#analyseAllColors(BufferedImage)} works.
     */
    @Test
    public void analyseAllColorsTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println(distribution);

        // Validation

        assertEquals(4829, distribution.get(Color.WHITE).getCount());
        assertEquals(171, distribution.get(Color.RED).getCount());
    }

    /**
     * A test for the example showing people how details of a {@link PaletteDistribution} can be accessed.
     */
    @Test
    public void paletteDistributionTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println("Original Distribution: " + distribution.getDistribution());
        System.out.println("By Count: " + distribution.byCount());
        System.out.println("By Name: " + distribution.byName());

        System.out.println("Red by name: " + distribution.get("#ff0000"));
        System.out.println("White by color: " + distribution.get(Color.WHITE));
        System.out.println("Blue by color: " + distribution.get(Color.BLUE));

        // Validation

        assertEquals("[#ffffff: 4829, #ff0000: 171]", distribution.getDistribution().toString());
        assertEquals("[#ffffff: 4829, #ff0000: 171]", distribution.byCount().toString());
        assertEquals("[#ff0000: 171, #ffffff: 4829]", distribution.byName().toString());

        assertEquals("#ff0000: 171", distribution.get("#ff0000").toString());
        assertEquals("#ffffff: 4829", distribution.get(Color.WHITE).toString());
        assertEquals(null, distribution.get(Color.BLUE));
    }

    /**
     * A test showing the before example of how to use JUnit to assert facts about the image makeup.
     */
    @Test
    public void beforeUsingAssertsTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);

        assertTrue(distribution.get(Color.WHITE).getCount() > 2500);
        assertEquals(Color.WHITE, distribution.byCount().get(0).getTone().getColor());
        assertTrue(distribution.get(Color.RED) != null);

        // Validation

        // None needed, already doing the assert.
    }


    /**
     * A test showing the after example showing how to use {@link com.wabradshaw.palettest.assertions.AssertMostly} , of how to use JUnit to assert facts about the image makeup.
     */
    @Test
    public void assertColorsTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);

        assertMostly(Color.WHITE, distribution);
        assertMainColor(Color.WHITE, distribution);
        assertContainsColor(Color.RED, distribution);

        // Validation

        // None needed, already doing the assert.
    }

    /**
     * A test showing what happens if you try analyseAllColors on an anti-aliased image.
     */
    @Test
    public void beforeUsingPalettesTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println(distribution);

        // Validation

        assertEquals(186, distribution.getDistribution().size());
    }

    /**
     * A test showing how to use analysePalette (with the default settings) on an anti-aliased image.
     */
    @Test
    public void afterUsingPalettesTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(exampleImage);
        System.out.println(distribution);

        // Validation

        assertEquals(10, distribution.getDistribution().size());
    }

    /**
     * A test showing how to use analysePalette (with the default settings) on an anti-aliased image.
     */
    @Test
    public void specificPalettesTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, exampleImage);
        System.out.println(distribution);

        // Validation

        assertEquals(3, distribution.getDistribution().size());
    }

    /**
     * A test showing how to use analysePalette (with the default settings) on an anti-aliased image.
     */
    @Test
    public void variousPalettesTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution javaDistribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, exampleImage);
        PaletteDistribution rainbowDistribution = tester.analysePalette(StandardPalettes.RAINBOW, exampleImage);
        PaletteDistribution rainbowBWDistribution = tester.analysePalette(StandardPalettes.RAINBOW_BW, exampleImage);
        PaletteDistribution pwgDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, exampleImage);
        PaletteDistribution x11Distribution = tester.analysePalette(StandardPalettes.X11_NUMBERED, exampleImage);
        System.out.println("JAVA: " + javaDistribution);
        System.out.println("RAINBOW: " + rainbowDistribution);
        System.out.println("RAINBOW_BW: " + rainbowBWDistribution);
        System.out.println("PWG_STANDARD: " + pwgDistribution);
        System.out.println("X11_NUMBERED: " + x11Distribution);

        // Validation

        assertEquals(3, javaDistribution.getDistribution().size());
        assertEquals(3, rainbowDistribution.getDistribution().size());
        assertEquals(3, rainbowBWDistribution.getDistribution().size());
        assertEquals(10, pwgDistribution.getDistribution().size());
        assertEquals(21, x11Distribution.getDistribution().size());
    }

    /**
     * A test showing how to use analysePalette with a custom palette.
     */
    @Test
    public void customPalettesTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Background", Color.WHITE));
        palette.add(new Tone("Text", new Color(255,200,200)));

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(palette, exampleImage);
        System.out.println(distribution);

        // Validation

        assertEquals(2, distribution.getDistribution().size());
    }

    /**
     * A test showing how to define a palette using an image.
     */
    @Test
    public void definePaletteTest() {

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        List<Tone> palette = tester.definePalette(exampleImage, 2);
        System.out.println(palette);

        // Validation

        assertEquals(2, palette.size());
    }

    /**
     * A test showing how the palette visualiser can be used to demonstrate the colors in a palette.
     */
    @Test
    public void visualisePaletteTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        PaletteVisualiser paletteVisualiser = new PaletteVisualiser();
        BufferedImage paletteImage = paletteVisualiser.visualise(StandardPalettes.JAVA_COLORS, 4);

        ImageFileUtils.save(paletteImage, "src/test/resources/resultImages/examples/examplePalette.png", "png");

        // Validation

        assertDimensions(paletteImage, 400, 200);
    }

    /**
     * A test showing how you can use the palette replacer to show what colors in an image map to which Tones in a
     * palette.
     */
    @Test
    public void redrawImageTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/complex/smallSheep.jpg");

        PaletteReplacer replacer = new PaletteReplacer();
        BufferedImage replacedImage = replacer.replace(exampleImage, StandardPalettes.PWG_STANDARD);

        ImageFileUtils.save(replacedImage, "src/test/resources/resultImages/examples/replacedImage.png", "png");

        // Validation

        Palettester tester = new Palettester();
        PaletteDistribution originalDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, exampleImage);
        PaletteDistribution newDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, replacedImage);
        assertEquals(originalDistribution, newDistribution);
    }

    /**
     * A test showing how several different replacements look.
     */
    @Test
    public void multipleRedrawImageTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/complex/smallSheep.jpg");

        PaletteReplacer replacer = new PaletteReplacer();

        Map<String, List<Tone>> palettes = new HashMap<>();
        palettes.put("java", StandardPalettes.JAVA_COLORS);
        palettes.put("rainbow", StandardPalettes.RAINBOW);
        palettes.put("rainbowBw", StandardPalettes.RAINBOW_BW);
        palettes.put("pwg", StandardPalettes.PWG_STANDARD);
        palettes.put("x11", StandardPalettes.X11_NUMBERED);
        palettes.put("custom", new Palettester().definePalette(exampleImage, 20));

        palettes.entrySet().forEach( namedPalette -> {
            BufferedImage replacedImage = replacer.replace(exampleImage, namedPalette.getValue());

            ImageFileUtils.save(replacedImage,
                                "src/test/resources/resultImages/examples/" + namedPalette.getKey() +"ReplacedImage.png",
                                "png");
        });

        // Validation

        palettes.keySet().forEach( name -> {
            assertNotNull(ImageFileUtils.loadImageResource("/resultImages/examples/" + name +"ReplacedImage.png"));
        });
    }

    /**
     * A test showing how you can use the distribution painter to visualise a distribution.
     */
    @Test
    public void visualiseDistributionTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/complex/smallSheep.jpg");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(exampleImage);

        DistributionPainter painter = new DistributionPainter();

        BufferedImage replacedImage = painter.paintTones(distribution.byCount(),
                                                         exampleImage.getWidth(),
                                                         exampleImage.getHeight());

        ImageFileUtils.save(replacedImage, "src/test/resources/resultImages/examples/distribution.png", "png");

        // Validation

        PaletteDistribution originalDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, exampleImage);
        PaletteDistribution newDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, replacedImage);
        assertEquals(originalDistribution, newDistribution);
    }
}
