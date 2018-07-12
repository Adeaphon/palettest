package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;

/**
 * A set of tests for the {@link DistributionPainter} class.
 */
public class DistributionPainterTest {

    /**
     * Tests that using paintTones will return an image of the correct size.
     */
    @Test
    public void testPaintTones_Size(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        DistributionPainter visualiser = new DistributionPainter();

        BufferedImage result = visualiser.paintTones(new ArrayList<>(), 10, 20);

        assertDimensions(result, 10, 20);
    }

}
