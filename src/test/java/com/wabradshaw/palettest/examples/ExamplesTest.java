package com.wabradshaw.palettest.examples;

import com.wabradshaw.palettest.assertions.AssertDimensions;
import org.junit.jupiter.api.Test;

import com.wabradshaw.palettest.utils.ImageFileUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/helloWorld.png");

        // Validation

        AssertDimensions.assertDimensions(exampleImage, 100, 50);
    }

}
