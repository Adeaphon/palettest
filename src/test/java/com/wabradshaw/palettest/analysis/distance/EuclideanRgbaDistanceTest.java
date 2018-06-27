package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for {@link EuclideanRgbaDistance}.
 */
public class EuclideanRgbaDistanceTest {

    EuclideanRgbaDistance function = new EuclideanRgbaDistance();

    /**
     * Tests that two black colours have no distance.
     */
    @Test
    public void testDistanceBlackBlack(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two red colours have no distance.
     */
    @Test
    public void testDistanceRedRed(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(255,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two green colours have no distance.
     */
    @Test
    public void testDistanceGreenGreen(){
        Tone a = getTone(0,255,0,255);
        Tone b = getTone(0,255,0,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result);
    }

    /**
     * Tests that two blue colours have no distance.
     */
    @Test
    public void testDistanceBlueBlue(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(0,0,255,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two black colours have no distance.
     */
    @Test
    public void testDistanceWhiteWhite(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two identical compound colours have no distance.
     */
    @Test
    public void testDistanceCompoundCompound(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(220,250,206,255);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests the difference between black and white.
     */
    @Test
    public void testDistanceBlackWhite(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(441.672956 , result, 0.000001);
    }

    /**
     * Tests the difference between red and white.
     */
    @Test
    public void testDistanceRedWhite(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(360.624458 , result, 0.000001);
    }

    /**
     * Tests the difference between green and white.
     */
    @Test
    public void testDistanceGreenWhite(){
        Tone a = getTone(0, 255,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(360.624458 , result, 0.000001);
    }

    /**
     * Tests the difference between blue and white.
     */
    @Test
    public void testDistanceBlueWhite(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(360.624458 , result, 0.000001);
    }

    /**
     * Tests the difference between a compound color and white.
     */
    @Test
    public void testDistanceCompoundWhite(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getDistance(a,b);
        assertEquals(60.423505 , result, 0.000001);
    }

    /**
     * Tests the difference between white and black.
     */
    @Test
    public void testDistanceWhiteBlack(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(441.672956 , result, 0.000001);
    }

    /**
     * Tests the difference between red and black.
     */
    @Test
    public void testDistanceRedBlack(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(255 , result, 0.000001);
    }

    /**
     * Tests the difference between green and black.
     */
    @Test
    public void testDistanceGreenBlack(){
        Tone a = getTone(0, 255,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(255 , result, 0.000001);
    }

    /**
     * Tests the difference between blue and black.
     */
    @Test
    public void testDistanceBlueBlack(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(255 , result, 0.000001);
    }

    /**
     * Tests the difference between a compound color and black.
     */
    @Test
    public void testDistanceCompoundBlack(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getDistance(a,b);
        assertEquals(391.581409 , result, 0.000001);
    }

    /**
     * Tests that a transparent version of a color is different.
     */
    @Test
    public void testDistanceTransparentCompound(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getDistance(a,b);
        assertEquals(255, result, 0.000001);
    }

    /**
     * Tests that two identical transparent colors are the same.
     */
    @Test
    public void testDistanceIdenticalTransparent(){
        Tone a = getTone(220,250,206,0);
        Tone b = getTone(220,250,206,0);
        double result = function.getDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two different transparent colors are apparently different.
     */
    @Test
    public void testDistanceDifferentTransparent(){
        Tone a = getTone(255,0,255,0);
        Tone b = getTone(0,255,0,0);
        double result = function.getDistance(a,b);
        assertEquals(441.672956, result, 0.000001);
    }

    /**
     * Tests that black and transparent are different.
     */
    @Test
    public void testDistanceTransparentBlack(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getDistance(a,b);
        assertEquals(467.29113, result, 0.000001);
    }


    /**
     * Tests that white and transparent are different.
     */
    @Test
    public void testDistanceTransparentWhite(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getDistance(a,b);
        assertEquals(262.061062, result, 0.000001);
    }

    ///////////

    /**
     * Tests that two black colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceBlackBlack(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two red colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceRedRed(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(255,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two green colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceGreenGreen(){
        Tone a = getTone(0,255,0,255);
        Tone b = getTone(0,255,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result);
    }

    /**
     * Tests that two blue colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceBlueBlue(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(0,0,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two black colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceWhiteWhite(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two identical compound colours have no ranking distance.
     */
    @Test
    public void testRankingDistanceCompoundCompound(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(220,250,206,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests the difference between black and white.
     */
    @Test
    public void testRankingDistanceBlackWhite(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*3 , result, 0.000001);
    }

    /**
     * Tests the difference between red and white.
     */
    @Test
    public void testRankingDistanceRedWhite(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*2 , result, 0.000001);
    }

    /**
     * Tests the difference between green and white.
     */
    @Test
    public void testRankingDistanceGreenWhite(){
        Tone a = getTone(0, 255,0,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*2 , result, 0.000001);
    }

    /**
     * Tests the difference between blue and white.
     */
    @Test
    public void testRankingDistanceBlueWhite(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*2 , result, 0.000001);
    }

    /**
     * Tests the difference between a compound color and white.
     */
    @Test
    public void testRankingDistanceCompoundWhite(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(255,255,255,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(3651 , result, 0.000001);
    }

    /**
     * Tests the difference between white and black.
     */
    @Test
    public void testRankingDistanceWhiteBlack(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*3 , result, 0.000001);
    }

    /**
     * Tests the difference between red and black.
     */
    @Test
    public void testRankingDistanceRedBlack(){
        Tone a = getTone(255,0,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255 , result, 0.000001);
    }

    /**
     * Tests the difference between green and black.
     */
    @Test
    public void testRankingDistanceGreenBlack(){
        Tone a = getTone(0, 255,0,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255 , result, 0.000001);
    }

    /**
     * Tests the difference between blue and black.
     */
    @Test
    public void testRankingDistanceBlueBlack(){
        Tone a = getTone(0,0,255,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255 , result, 0.000001);
    }

    /**
     * Tests the difference between a compound color and black.
     */
    @Test
    public void testRankingDistanceCompoundBlack(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(0,0,0,255);
        double result = function.getRankingDistance(a,b);
        assertEquals(153336 , result, 0.000001);
    }

    /**
     * Tests that a transparent version of a color is different.
     */
    @Test
    public void testRankingDistanceTransparentCompound(){
        Tone a = getTone(220,250,206,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255, result, 0.000001);
    }

    /**
     * Tests that two identical transparent colors are the same.
     */
    @Test
    public void testRankingDistanceIdenticalTransparent(){
        Tone a = getTone(220,250,206,0);
        Tone b = getTone(220,250,206,0);
        double result = function.getRankingDistance(a,b);
        assertEquals(0, result, 0.000001);
    }

    /**
     * Tests that two different transparent colors are apparently different.
     */
    @Test
    public void testRankingDistanceDifferentTransparent(){
        Tone a = getTone(255,0,255,0);
        Tone b = getTone(0,255,0,0);
        double result = function.getRankingDistance(a,b);
        assertEquals(255*255*3, result, 0.000001);
    }

    /**
     * Tests that black and transparent are different.
     */
    @Test
    public void testRankingDistanceTransparentBlack(){
        Tone a = getTone(0,0,0,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getRankingDistance(a,b);
        assertEquals(218361, result, 0.000001);
    }


    /**
     * Tests that white and transparent are different.
     */
    @Test
    public void testRankingDistanceTransparentWhite(){
        Tone a = getTone(255,255,255,255);
        Tone b = getTone(220,250,206,0);
        double result = function.getRankingDistance(a,b);
        assertEquals(68676, result, 0.000001);
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
