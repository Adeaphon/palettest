package com.wabradshaw.palettest.utils;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of tests for the {@link GraphicsUtils} class
 */
public class GraphicsUtilsTest {

    /**
     * Checks that the createCopy method can create a copy of a simple one pixel image.
     */
    @Test
    public void testCreateCopy_simple(){
        BufferedImage original = ImageFileUtils.loadImageResource("/sampleImages/dimensions/1x1.png");

        BufferedImage copy = GraphicsUtils.createCopy(original);

        assertEquals(1, copy.getWidth());
        assertEquals(1, copy.getHeight());
        assertEquals(original.getRGB(0,0), copy.getRGB(0, 0));
    }


    /**
     * Checks that the createCopy method can create a copy of a small image with multiple colors.
     */
    @Test
    public void testCreateCopy_complex(){
        BufferedImage original = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        BufferedImage copy = GraphicsUtils.createCopy(original);

        assertEquals(10, copy.getWidth());
        assertEquals(10, copy.getHeight());
        for(int x = 0; x < 10; x ++){
            for(int y = 0; y < 10; y++){
                assertEquals(original.getRGB(x, y), copy.getRGB(x, y));
            }
        }
    }


    /**
     * Checks that createCopy will ignore changes to the original after the copy has been produced.
     */
    @Test
    public void testCreateCopy_immutable(){
        BufferedImage original = ImageFileUtils.loadImageResource("/sampleImages/geometric/redBlueQuarter.png");

        BufferedImage copy = GraphicsUtils.createCopy(original);

        Graphics2D g = copy.createGraphics();
        g.setPaint(Color.green);
        g.fillRect(0,0,10,10);
        g.dispose();

        for(int x = 0; x < 10; x ++){
            for(int y = 0; y < 10; y++){
                assertNotEquals(original.getRGB(x, y), copy.getRGB(x, y));
            }
        }
    }

    /**
     * Checks that the createCopy will throw an exception if the image is null.
     */
    @Test
    public void testCreateCopy_null(){
        assertThrows(IllegalArgumentException.class, () -> GraphicsUtils.createCopy(null));
    }
}
