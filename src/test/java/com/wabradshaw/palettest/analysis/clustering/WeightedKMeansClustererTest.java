package com.wabradshaw.palettest.analysis.clustering;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A set of tests for the {@link WeightedKMeansClusterer}.
 */
public class WeightedKMeansClustererTest {

    /**
     * Tests that using the clusterer with only a single color in the counts, while asking for
     * a single cluster, will return that color.
     */
    @Test
    public void testSingleCluster_SingleColor(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(Color.RED, 10);

        Collection<Color> result = clusterer.cluster(counts, 1);

        assertEquals(1, result.size());
        assertTrue(result.contains(Color.RED));
    }

    /**
     * Tests that using the clusterer with two equal colors, while asking for a single cluster, will return the
     * average of those colors.
     */
    @Test
    public void testSingleCluster_EqualColors(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(Color.BLACK, 10);
        counts.put(Color.WHITE, 10);

        Collection<Color> result = clusterer.cluster(counts, 1);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Color(127,127,127)));
    }

    /**
     * Tests that using the clusterer with two unequal colors, while asking for a single cluster, will return the
     * weighted average of those colors.
     */
    @Test
    public void testSingleCluster_UnequalColors(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(new Color(200, 0, 127), 3);
        counts.put(new Color(100, 160, 127), 1);

        Collection<Color> result = clusterer.cluster(counts, 1);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Color(175,40,127)));
    }

    /**
     * Tests that using the clusterer with two unequal colors, will include an average of the alpha channel.
     */
    @Test
    public void testSingleCluster_AverageAlpha(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(new Color(200, 0, 127, 255), 3);
        counts.put(new Color(200, 0, 127, 0), 1);

        Collection<Color> result = clusterer.cluster(counts, 1);

        assertEquals(1, result.size());
        assertTrue(result.contains(new Color(200,0,127, (int) (255*0.75))));
    }
}
