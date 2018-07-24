package com.wabradshaw.palettest.analysis.clustering;

import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

    /**
     * Tests that using the clusterer for the same number of clusters as colors will return the original colors.
     */
    @Test
    public void testMultipleClusters_SameNumber(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(Color.RED, 10);
        counts.put(Color.BLUE, 11);
        counts.put(Color.GREEN, 4);

        Collection<Color> result = clusterer.cluster(counts, 3);

        assertEquals(3, result.size());
        assertTrue(result.contains(Color.RED));
        assertTrue(result.contains(Color.BLUE));
        assertTrue(result.contains(Color.GREEN));
    }

    /**
     * Tests that using the clusterer for multiple clusters which are obvious will always find the same centroids.
     */
    @Test
    public void testMultipleClusters_ClearCutAverages(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(new Color(200,0,0), 10);
        counts.put(new Color(240,0,0), 30);
        counts.put(new Color(0, 190, 0), 20);
        counts.put(new Color(0, 210, 0), 20);
        counts.put(new Color(15,0,200), 50);
        counts.put(new Color(0,15,200), 50);
        counts.put(new Color(0,0,215), 50);

        Collection<Color> result = clusterer.cluster(counts, 3);

        assertEquals(3, result.size());
        assertTrue(result.contains(new Color(230,0,0)));
        assertTrue(result.contains(new Color(0,200,0)));
        assertTrue(result.contains(new Color(5,5,205)));
    }

    /**
     * Tests that using the clusterer for multiple clusters which are controversial will still finish.
     */
    @Test
    public void testMultipleClusters_Complex(){
        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer();

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(new Color(200,0,0), 10);
        counts.put(new Color(240,120,0), 30);
        counts.put(new Color(0, 190, 70), 20);
        counts.put(new Color(90, 140, 0), 20);
        counts.put(new Color(159,0,200), 50);
        counts.put(new Color(140,130,200), 50);
        counts.put(new Color(0,0,80), 50);

        Collection<Color> result = clusterer.cluster(counts, 3);

        assertEquals(3, result.size());
    }

    /**
     * Tests that the distance function constructor will use the supplied distance function.
     *
     * Because this is a heuristic based algorithm, it's difficult to predict exact behaviour with the distance
     * function, so a mockito spy is used to validate it's being called.
     */
    @Test
    public void testSuppliedDistanceFunction(){
        ColorDistanceFunction distanceFunction = spy(new EuclideanRgbaDistance());

        WeightedKMeansClusterer clusterer = new WeightedKMeansClusterer(distanceFunction);

        Map<Color, Integer> counts = new HashMap<>();
        counts.put(Color.RED, 10);
        counts.put(Color.BLUE, 10);

        Collection<Color> result = clusterer.cluster(counts, 2);

        verify(distanceFunction, atLeastOnce()).getDistance(any(), any());
    }
}
