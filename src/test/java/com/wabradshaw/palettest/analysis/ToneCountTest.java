package com.wabradshaw.palettest.analysis;

import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A set of tests for the {@link ToneCount} object.
 */
public class ToneCountTest {

    /**
     * Tests that the main constructor will store the arguments it was created with.
     */
    @Test
    public void testConstructor(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(red, toneCount.getTone());
        assertEquals(pixelCounts, toneCount.getPixelCounts());
    }


    /**
     * Tests that a null Tone is okay. In some situations we may want a null Tone to cover {@link Color}s too far from
     * the defined palette.
     */
    @Test
    public void testNullTone(){
        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(null, pixelCounts);
        assertEquals(null, toneCount.getTone());
    }

    /**
     * Tests that a null pixel count map will throw an illegal argument exception.
     */
    @Test
    public void testNullPixelCount(){
        Tone red = new Tone("red", Color.RED);

        assertThrows(IllegalArgumentException.class, () -> new ToneCount(red, null));
    }

    /**
     * Tests that a null pixel count map (and tone) will throw an illegal argument exception.
     */
    @Test
    public void testNullPixelCountAndTone(){
        assertThrows(IllegalArgumentException.class, () -> new ToneCount(null, null));
    }
    /**
     * Tests that changes to the pixel count used in the constructor are not reflected in the ToneCount.
     */
    @Test
    public void testSetPixelCountsImmutable(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        pixelCounts.put(Color.magenta, 4);

        assertEquals(3, toneCount.getPixelCounts().size());
        assertNotEquals(pixelCounts, toneCount.getPixelCounts());
    }



    /**
     * Tests that changes to the pixel count produced by the getter method are not reflected in the ToneCount.
     */
    @Test
    public void testGetterPixelCountsImmutable(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        Map<Color, Integer> gotPixelCounts = toneCount.getPixelCounts();
        gotPixelCounts.put(Color.magenta, 4);

        assertEquals(3, toneCount.getPixelCounts().size());
        assertNotEquals(gotPixelCounts, toneCount.getPixelCounts());
    }

    /**
     * Tests that getCount will return the number of pixels of the Tone.
     */
    @Test
    public void testStandardCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(16, toneCount.getCount());
    }

    /**
     * Tests that getCount works when supplied with an empty pixelCounts map.
     */
    @Test
    public void testEmptyCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(0, toneCount.getCount());
    }

    /**
     * Tests that getCount will ignore {@link Color}s which have a null count.
     */
    @Test
    public void testNullCount(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, null);

        ToneCount toneCount = new ToneCount(red, pixelCounts);
        assertEquals(15, toneCount.getCount());
    }

    /**
     * Tests the getAverageDistance method if there aren't any pixelCounts.
     */
    @Test
    public void testGetAverageDistance_empty(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);

        assertEquals(0, toneCount.getAverageDistance(function));
    }

    /**
     * Tests the getAverageDistance method will return the correct distance if there is only a single pixelCount, with a
     * single pixel in it.
     */
    @Test
    public void testGetAverageDistance_oneSingleColor(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.BLUE, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.BLUE)))
               .thenReturn(10.0);

        assertEquals(10.0, toneCount.getAverageDistance(function));
    }

    /**
     * Tests the getAverageDistance method will return the correct distance if there is only a single pixelCount,
     * which has multiple pixels within it.
     */
    @Test
    public void testGetAverageDistance_multipleSingleColor(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.BLUE, 25);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.BLUE)))
                .thenReturn(10.0);

        assertEquals(10.0, toneCount.getAverageDistance(function));
    }

    /**
     * Tests the getAverageDistance method will return the correct distance if there are multiple colors in the pixel
     * counts.
     */
    @Test
    public void testGetAverageDistance_multipleColors(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.BLUE, 1);
        pixelCounts.put(Color.RED, 5);
        pixelCounts.put(Color.MAGENTA, 4);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.BLUE)))
                .thenReturn(10.0);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.RED)))
                .thenReturn(0.0);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.MAGENTA)))
                .thenReturn(2.5);

        assertEquals(2.0, toneCount.getAverageDistance(function));
    }


    /**
     * Tests the getMaxDistance method if there aren't any pixelCounts.
     */
    @Test
    public void testGetMaxDistance_empty(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);

        assertEquals(0, toneCount.getMaxDistance(function));
    }

    /**
     * Tests the getMaxDistance method will return the correct distance if there is only a single pixelCount.
     */
    @Test
    public void testGetMaxDistance_singleColor(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.BLUE, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.BLUE)))
                .thenReturn(10.0);

        assertEquals(10.0, toneCount.getMaxDistance(function));
    }

    /**
     * Tests the getMaxDistance method will return the correct distance if there are multiple colors in the pixel
     * counts.
     */
    @Test
    public void testGetMaxDistance_multipleColors(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.BLUE, 1);
        pixelCounts.put(Color.RED, 5);
        pixelCounts.put(Color.MAGENTA, 4);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        ColorDistanceFunction function = Mockito.mock(ColorDistanceFunction.class);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.BLUE)))
                .thenReturn(10.0);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.RED)))
                .thenReturn(0.0);
        Mockito.when(function.getDistance(Mockito.eq(red), Mockito.argThat(tone -> tone.getColor() == Color.MAGENTA)))
                .thenReturn(2.5);

        assertEquals(10.0, toneCount.getMaxDistance(function));
    }

    /**
     * Tests the toString method works appropriately.
     */
    @Test
    public void testToString(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertEquals("red: 16", toneCount.toString());
    }

    /**
     * Tests the equals method will fail when used on null.
     */
    @Test
    public void testEquals_null(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertFalse(toneCount.equals(null));
    }

    /**
     * Tests the equals method will fail when used on a different type of object.
     */
    @Test
    public void testEquals_otherType(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertFalse(toneCount.equals("red: 16"));
    }


    /**
     * Tests the equals method will pass when used on the same object.
     */
    @Test
    public void testEquals_identity(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertTrue(toneCount.equals(toneCount));
    }

    /**
     * Tests the equals method will succeed if the objects use the same tone and count.
     */
    @Test
    public void testEquals_equivalent(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red, pixelCounts);
        ToneCount toneCount2 = new ToneCount(red, pixelCounts);

        assertTrue(toneCount1.equals(toneCount2));
    }

    /**
     * Tests the equals method will succeed if the objects use equivalent tones and counts.
     */
    @Test
    public void testEquals_equivalentDifferentObjects(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 10);
        pixelCounts2.put(Color.orange, 5);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertTrue(toneCount1.equals(toneCount2));
    }

    /**
     * Tests the equals method will fail if the tone is different.
     */
    @Test
    public void testEquals_differentTone(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", new Color(200, 0, 10));

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 10);
        pixelCounts2.put(Color.orange, 5);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertFalse(toneCount1.equals(toneCount2));
    }

    /**
     * Tests the equals method will fail if the count is different.
     */
    @Test
    public void testEquals_differentCount(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 3);
        pixelCounts2.put(Color.orange, 2);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertFalse(toneCount1.equals(toneCount2));
    }

    /**
     * Tests the equals method will succeed if the objects use equivalent tones and counts, but have different
     * individual pixel counts.
     */
    @Test
    public void testEquals_equivalentDifferentPixels(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.MAGENTA, 4);
        pixelCounts2.put(Color.orange, 9);
        pixelCounts2.put(Color.pink, 3);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertTrue(toneCount1.equals(toneCount2));
    }

    /**
     * Tests the hashcode method will return the same value for equivalent objects.
     */
    @Test
    public void testHashCode_same(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.MAGENTA, 4);
        pixelCounts2.put(Color.orange, 9);
        pixelCounts2.put(Color.pink, 3);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertEquals(toneCount1.hashCode(), toneCount2.hashCode());
    }

    /**
     * Tests the hashcode method will return different values if they have different tones.
     */
    @Test
    public void testHashCode_differentTone(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", new  Color(200, 0 , 10));

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.MAGENTA, 4);
        pixelCounts2.put(Color.orange, 9);
        pixelCounts2.put(Color.pink, 3);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertNotEquals(toneCount1.hashCode(), toneCount2.hashCode());
    }

    /**
     * Tests the hashcode method will return different values if they have different counts.
     */
    @Test
    public void testHashCode_differentCounts(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.MAGENTA, 400);
        pixelCounts2.put(Color.orange, 900);
        pixelCounts2.put(Color.pink, 300);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertNotEquals(toneCount1.hashCode(), toneCount2.hashCode());
    }

    /**
     * Tests the deep equals method will fail when used on null.
     */
    @Test
    public void testDeepEquals_null(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertFalse(toneCount.deepEquals(null));
    }

    /**
     * Tests the deep equals method will fail when used on a different type of object.
     */
    @Test
    public void testDeepEquals_otherType(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertFalse(toneCount.deepEquals("red: 16"));
    }


    /**
     * Tests the deep equals method will pass when used on the same object.
     */
    @Test
    public void testDeepEquals_identity(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount = new ToneCount(red, pixelCounts);

        assertTrue(toneCount.deepEquals(toneCount));
    }

    /**
     * Tests the deep equals method will succeed if the objects use the same tone and count.
     */
    @Test
    public void testDeepEquals_equivalent(){
        Tone red = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts = new HashMap<>();
        pixelCounts.put(Color.red, 10);
        pixelCounts.put(Color.orange, 5);
        pixelCounts.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red, pixelCounts);
        ToneCount toneCount2 = new ToneCount(red, pixelCounts);

        assertTrue(toneCount1.deepEquals(toneCount2));
    }

    /**
     * Tests the deep equals method will succeed if the objects use equivalent tones and counts.
     */
    @Test
    public void testDeepEquals_equivalentDifferentObjects(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 10);
        pixelCounts2.put(Color.orange, 5);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertTrue(toneCount1.deepEquals(toneCount2));
    }

    /**
     * Tests the deep equals method will fail if the tone is different.
     */
    @Test
    public void testDeepEquals_differentTone(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", new Color(200, 0, 10));

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 10);
        pixelCounts2.put(Color.orange, 5);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertFalse(toneCount1.deepEquals(toneCount2));
    }

    /**
     * Tests the deep equals method will fail if the count is different.
     */
    @Test
    public void testDeepEquals_differentCount(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.red, 3);
        pixelCounts2.put(Color.orange, 2);
        pixelCounts2.put(Color.pink, 1);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertFalse(toneCount1.deepEquals(toneCount2));
    }

    /**
     * Tests the deep equals method will fail if the objects use equivalent tones and counts, but have different
     * individual pixel counts. This is different to equals.
     */
    @Test
    public void testDeepEquals_equivalentDifferentPixels(){
        Tone red1 = new Tone("red", Color.RED);

        Map<Color, Integer> pixelCounts1 = new HashMap<>();
        pixelCounts1.put(Color.red, 10);
        pixelCounts1.put(Color.orange, 5);
        pixelCounts1.put(Color.pink, 1);

        ToneCount toneCount1 = new ToneCount(red1, pixelCounts1);

        Tone red2 = new Tone("reddish", Color.RED);

        Map<Color, Integer> pixelCounts2 = new HashMap<>();
        pixelCounts2.put(Color.MAGENTA, 4);
        pixelCounts2.put(Color.orange, 9);
        pixelCounts2.put(Color.pink, 3);
        ToneCount toneCount2 = new ToneCount(red2, pixelCounts2);

        assertFalse(toneCount1.deepEquals(toneCount2));
    }
}
