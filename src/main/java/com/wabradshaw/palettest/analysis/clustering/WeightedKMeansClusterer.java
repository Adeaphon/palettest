package com.wabradshaw.palettest.analysis.clustering;

import java.awt.Color;
import java.util.*;

/**
 * A {@link ClusteringAlgorithm} which uses a weighted version of k-means clustering to define the clusters.
 */
public class WeightedKMeansClusterer implements ClusteringAlgorithm {

    private final int maxAttempts = 100;

    @Override
    public Collection<Color> cluster(Map<Color, Integer> colorCounts, int targetClusters) {
        List<Color> clusters = initialiseClusters(colorCounts, targetClusters);

        int attempts = 0;
        List<Color> newClusters = new ArrayList<>();

        while(attempts < maxAttempts && newClusters != clusters){
            clusters = newClusters;
            attempts++;

            Map<Color, Map<Color, Integer>> partitions = assignClusters(clusters, colorCounts);

            newClusters = updateClusters(partitions);
        }

        return newClusters;
    }

    /**
     * Initialises starting colors for each of the clusters.
     *
     * @param colorCounts    The colors being described and the number of times they each appeared.
     * @param targetClusters The number of clusters that should be created.
     * @return               A list of colors serving as the initial set of clusters.
     */
    private List<Color> initialiseClusters(Map<Color, Integer> colorCounts, int targetClusters) {
        //TODO
        return null;
    }

    /**
     * Finds the color cluster which best describes each color defined in the image.
     *
     * @param clusters    The color clusters that can be used.
     * @param colorCounts The colors being described and the number of times they each appeared.
     * @return            A map of color clusters and all of the color counts that are closest to that cluster.
     */
    private Map<Color,Map<Color,Integer>> assignClusters(List<Color> clusters, Map<Color, Integer> colorCounts) {
        //TODO
        return null;
    }

    /**
     * Updates the colors describing each cluster so that each is the average of the whole cluster.
     * @param partitions
     * @return
     */
    private List<Color> updateClusters(Map<Color, Map<Color, Integer>> partitions) {
        //TODO
        return null;
    }
}
