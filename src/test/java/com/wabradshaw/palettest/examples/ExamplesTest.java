package com.wabradshaw.palettest.examples;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Palettester;
import com.wabradshaw.palettest.assertions.*;
import org.junit.jupiter.api.Test;

import com.wabradshaw.palettest.utils.ImageFileUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        AssertDimensions.assertDimensions(result, 100, 50);

    }

    /**
     * A test for the example showing people how {@link ImageFileUtils#loadImageResource(String)} works.
     */
    @Test
    public void loadExampleTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        // Validation

        AssertDimensions.assertDimensions(exampleImage, 100, 50);
    }

    /**
     * A test for the example showing people how {@link AssertDimensions#assertDimensions(BufferedImage, int, int)}
     * works.
     */
    @Test
    public void assertDimensionsTest(){

        // Example

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        AssertDimensions.assertDimensions(exampleImage, 100, 50);

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
        AssertPixelsMatch.assertPixelsMatch(exampleImage, myImage);

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

        AssertMostly.assertMostly(Color.WHITE, distribution);
        AssertMainColor.assertMainColor(Color.WHITE, distribution);
        AssertContainsColor.assertContainsColor(Color.RED, distribution);

        // Validation

        // None needed, already doing the assert.
    }

}
