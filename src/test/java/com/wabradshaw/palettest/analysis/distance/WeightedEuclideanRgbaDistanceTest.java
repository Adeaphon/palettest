package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A set of tests for {@link EuclideanRgbaDistance}.
 */
public class WeightedEuclideanRgbaDistanceTest {

    /**
     * Tests the default weightings how no difference between identical tones.
     */
    @Test
    public void testDefaultDifference_NoDifference(){
        Tone a = getTone(123,45,67,89);
        Tone b = getTone(123,45,67,89);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance();

        assertEquals(0, function.getDistance(a,b));
    }

    /**
     * Tests the default weightings have the same difference between the furthest possible colors when weighted or
     * non-weighted.
     */
    @Test
    public void testDefaultDifference_MaxDifference(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(0,0,0,0);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance();

        assertEquals(510.0, function.getDistance(a,b));
    }

    /**
     * Tests the default weightings are in order (alpha < red < blue < green).
     */
    @Test
    public void testDefaultDifference_Comparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(255,0,0,0);
        Tone green = getTone(0,255,0,0);
        Tone blue = getTone(0,0,255,0);
        Tone alpha = getTone(0,0,0,255);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance();

        assertTrue(function.getDistance(base, base) < function.getDistance(base, alpha));
        assertTrue(function.getDistance(base, alpha) < function.getDistance(base, red));
        assertTrue(function.getDistance(base, red) < function.getDistance(base, blue));
        assertTrue(function.getDistance(base, blue) < function.getDistance(base, green));
    }

    /**
     * Tests that custom weightings have the same difference between the furthest possible colors when weighted or
     * non-weighted as the usual weightings.
     */
    @Test
    public void testCustomDifference_MaxDifference(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(0,0,0,0);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance(100, 200, 300, 400);

        assertEquals(510.0, function.getDistance(a,b));
    }

    /**
     * Tests custom weightings are in order (red < green == blue < alpha).
     */
    @Test
    public void testCustomDifference_Comparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(255,0,0,0);
        Tone green = getTone(0,255,0,0);
        Tone blue = getTone(0,0,255,0);
        Tone alpha = getTone(0,0,0,255);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance(1,2,2,4);

        assertTrue(function.getDistance(base, base) < function.getDistance(base, red));
        assertTrue(function.getDistance(base, red) < function.getDistance(base, green));
        assertTrue(function.getDistance(base, green) == function.getDistance(base, blue));
        assertTrue(function.getDistance(base, blue) < function.getDistance(base, alpha));
    }


    /**
     * Tests the default weightings show no difference between identical tones when computing ranking distance.
     */
    @Test
    public void testDefaultRankingDifference_NoDifference(){
        Tone a = getTone(123,45,67,89);
        Tone b = getTone(123,45,67,89);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance();

        assertEquals(0, function.getRankingDistance(a,b));
    }

    /**
     * Tests the default weightings are in order (alpha < red < blue < green) when computing ranking distance.
     */
    @Test
    public void testDefaultRankingDifference_Comparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(255,0,0,0);
        Tone green = getTone(0,255,0,0);
        Tone blue = getTone(0,0,255,0);
        Tone alpha = getTone(0,0,0,255);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance();

        assertTrue(function.getRankingDistance(base, base) < function.getRankingDistance(base, alpha));
        assertTrue(function.getRankingDistance(base, alpha) < function.getRankingDistance(base, red));
        assertTrue(function.getRankingDistance(base, red) < function.getRankingDistance(base, blue));
        assertTrue(function.getRankingDistance(base, blue) < function.getRankingDistance(base, green));
    }

    /**
     * Tests custom weightings are in order (red < green == blue < alpha) when computing ranking distance.
     */
    @Test
    public void testCustomRankingDifference_Comparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(255,0,0,0);
        Tone green = getTone(0,255,0,0);
        Tone blue = getTone(0,0,255,0);
        Tone alpha = getTone(0,0,0,255);

        WeightedEuclideanRgbaDistance function = new WeightedEuclideanRgbaDistance(1,2,2,4);

        assertTrue(function.getRankingDistance(base, base) < function.getRankingDistance(base, red));
        assertTrue(function.getRankingDistance(base, red) < function.getRankingDistance(base, green));
        assertTrue(function.getRankingDistance(base, green) == function.getRankingDistance(base, blue));
        assertTrue(function.getRankingDistance(base, blue) < function.getRankingDistance(base, alpha));
    }

    /**
     * Utility method to instantiate a tone and color at the same time.
     *
     * @param red   How red the color is between 0 and 255 (inclusive).
     * @param green How green the color is between 0 and 255 (inclusive).
     * @param blue  How blue the color is between 0 and 255 (inclusive).
     * @param alpha How alpha the color is between 0 and 255 (inclusive). Yes, it's our of 255, despite getAlpha
     *              returning a value between 0 and 100. Blame the Color constructor.
     * @return      The specified tone.
     */
    private Tone getTone(int red, int green, int blue, int alpha){
        return new Tone("", new Color(red, green, blue, alpha));
    }
}
