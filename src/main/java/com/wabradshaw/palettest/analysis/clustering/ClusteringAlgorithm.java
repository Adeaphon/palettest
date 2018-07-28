package com.wabradshaw.palettest.analysis.clustering;

import java.awt.Color;
import java.util.Collection;
import java.util.Map;

/**
 * A set of interfaces for algorithms that attempt to find the standard colors used in an image. These algorithms take
 * in a map of colors used in an image, as well as the number of times they were each used, then attempts to define
 * a palette that describes most of them.
 */
public interface ClusteringAlgorithm {

    /**
     * Takes the individual colors that make up an image, and defines a subset of colors which describe the image.
     *
     * @param colorCounts    The individual colors making up an image, and the number of times they each appeared.
     * @param targetClusters The number of target color clusters to use to describe the image.
     * @return               A collection of Color clusters that describe an image.
     */
    public Collection<Color> cluster(Map<Color, Integer> colorCounts, int targetClusters);

}
