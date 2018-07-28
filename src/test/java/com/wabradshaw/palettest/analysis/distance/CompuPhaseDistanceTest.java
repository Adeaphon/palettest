package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A set of tests for {@link CompuPhaseDistance}.
 */
public class CompuPhaseDistanceTest {

    /**
     * Tests the function shows no difference between identical tones.
     */
    @Test
    public void testDifference_NoDifference(){
        Tone a = getTone(123,45,67,89);
        Tone b = getTone(123,45,67,89);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertEquals(0, function.getDistance(a,b));
    }

    /**
     * Tests the function has the same difference between the furthest possible colors as Euclidean distance.
     */
    @Test
    public void testDifference_MaxDifference(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(0,0,0,0);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertEquals(510.0, function.getDistance(a,b));
    }

    /**
     * Tests the weightings in a high red situation are in the order (alpha < blue < red < green).
     */
    @Test
    public void testDefaultDifference_HighRedComparative(){
        Tone base = getTone(200,200,200,200);
        Tone red = getTone(255,200,200,200);
        Tone green = getTone(200,255,200,200);
        Tone blue = getTone(200,200,255,200);
        Tone alpha = getTone(200,200,200,255);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertTrue(function.getDistance(base, base) < function.getDistance(base, alpha));
        assertTrue(function.getDistance(base, alpha) < function.getDistance(base, blue));
        assertTrue(function.getDistance(base, blue) < function.getDistance(base, red));
        assertTrue(function.getDistance(base, red) < function.getDistance(base, green));
    }

    /**
     * Tests the weightings in a low red situation are in the order (alpha < red < blue < green).
     */
    @Test
    public void testDefaultDifference_LowRedComparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(100,0,0,0);
        Tone green = getTone(0,100,0,0);
        Tone blue = getTone(0,0,100,0);
        Tone alpha = getTone(0,0,0,100);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertTrue(function.getDistance(base, base) < function.getDistance(base, alpha));
        assertTrue(function.getDistance(base, alpha) < function.getDistance(base, red));
        assertTrue(function.getDistance(base, red) < function.getDistance(base, blue));
        assertTrue(function.getDistance(base, blue) < function.getDistance(base, green));
    }

    /**
     * Tests the function shows no difference between identical tones when computing ranking difference.
     */
    @Test
    public void testRankingDifference_NoDifference(){
        Tone a = getTone(123,45,67,89);
        Tone b = getTone(123,45,67,89);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertEquals(0, function.getRankingDistance(a,b));
    }

    /**
     * Tests the weightings in a high red situation are in the order (alpha < blue < red < green)  when computing
     * ranking difference.
     */
    @Test
    public void testRankingDifference_HighRedComparative(){
        Tone base = getTone(200,200,200,200);
        Tone red = getTone(255,200,200,200);
        Tone green = getTone(200,255,200,200);
        Tone blue = getTone(200,200,255,200);
        Tone alpha = getTone(200,200,200,255);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertTrue(function.getRankingDistance(base, base) < function.getRankingDistance(base, alpha));
        assertTrue(function.getRankingDistance(base, alpha) < function.getRankingDistance(base, blue));
        assertTrue(function.getRankingDistance(base, blue) < function.getRankingDistance(base, red));
        assertTrue(function.getRankingDistance(base, red) < function.getRankingDistance(base, green));
    }

    /**
     * Tests the weightings in a low red situation are in the order (alpha < red < blue < green)  when computing
     * ranking difference.
     */
    @Test
    public void testRankingDifference_LowRedComparative(){
        Tone base = getTone(0,0,0,0);
        Tone red = getTone(100,0,0,0);
        Tone green = getTone(0,100,0,0);
        Tone blue = getTone(0,0,100,0);
        Tone alpha = getTone(0,0,0,100);

        CompuPhaseDistance function = new CompuPhaseDistance();

        assertTrue(function.getRankingDistance(base, base) < function.getRankingDistance(base, alpha));
        assertTrue(function.getRankingDistance(base, alpha) < function.getRankingDistance(base, red));
        assertTrue(function.getRankingDistance(base, red) < function.getRankingDistance(base, blue));
        assertTrue(function.getRankingDistance(base, blue) < function.getRankingDistance(base, green));
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
