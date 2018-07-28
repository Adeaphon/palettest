package com.wabradshaw.palettest.analysis.clustering;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.CompuPhaseDistance;

import java.awt.Color;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * A {@link ClusteringAlgorithm} which uses a weighted version of k-means clustering to define the clusters.
 */
public class WeightedKMeansClusterer implements ClusteringAlgorithm {

    private final ColorDistanceFunction distanceFunction;
    private final int maxAttempts = 20;

    /**
     * Default constructor. Sets up a {@link WeightedKMeansClusterer} which uses {@link CompuPhaseDistance} to
     * measure the distance between {@link Color}s.
     */
    public WeightedKMeansClusterer(){
        this(new CompuPhaseDistance());
    }

    /**
     * Distance function constructor. Sets up a {@link WeightedKMeansClusterer} which uses the supplied
     * {@link ColorDistanceFunction} to measure the difference between {@link Color}s.
     *
     * @param distanceFunction The function to use when measuring the distance between {@link Color}s.
     */
    public WeightedKMeansClusterer(ColorDistanceFunction distanceFunction){
        this.distanceFunction = distanceFunction;
    }

    @Override
    public Collection<Color> cluster(Map<Color, Integer> colorCounts, int targetClusters) {
        //Using a map of tones to facilitate distance functions.
        Map<Tone, Integer> toneCounts = colorCounts.entrySet()
                                                   .stream()
                                                   .collect(Collectors.toMap(e -> new Tone("", e.getKey()),
                                                                             Map.Entry::getValue));

        List<Tone> clusters = initialiseClusters(toneCounts, targetClusters);

        int attempts = 0;
        List<Tone> oldClusters = new ArrayList<>();

        while(attempts < maxAttempts && !oldClusters.containsAll(clusters)){
            oldClusters = clusters;
            attempts++;

            Map<Tone, Map<Tone, Integer>> partitions = assignClusters(clusters, toneCounts);

            clusters = updateClusters(partitions);
        }

        return clusters.stream().map(tone -> tone.getColor()).collect(Collectors.toList());
    }

    /**
     * Initialises starting colors for each of the clusters.
     *
     * @param colorCounts    The colors being described and the number of times they each appeared.
     * @param targetClusters The number of clusters that should be created.
     * @return               A list of colors serving as the initial set of clusters.
     */
    private List<Tone> initialiseClusters(Map<Tone, Integer> colorCounts, int targetClusters) {
        List<Tone> possibleTones = new ArrayList<>(colorCounts.keySet());
        Collections.shuffle(possibleTones);
        return possibleTones.subList(0, targetClusters);
    }

    /**
     * Finds the color cluster which best describes each color defined in the image.
     *
     * @param clusters    The color clusters that can be used.
     * @param colorCounts The colors being described and the number of times they each appeared.
     * @return            A map of color clusters and all of the color counts that are closest to that cluster.
     */
    private Map<Tone,Map<Tone,Integer>> assignClusters(List<Tone> clusters, Map<Tone, Integer> colorCounts) {
        return colorCounts.entrySet()
                          .stream()
                          .map(cC -> assignCluster(clusters, cC.getKey(), cC.getValue()))
                          .collect(groupingBy(assignment -> assignment.cluster,
                                              toMap(closestTone -> closestTone.color,
                                              assignment -> assignment.count)));
    }

    /**
     * Updates the colors describing each cluster so that each is the average of the whole cluster.
     *
     * @param assignments A map of clusters and the map of individual colors and their counts assigned to each cluster.
     * @return            A list of Tones accurately describing the average color for each pixel.
     */
    private List<Tone> updateClusters(Map<Tone, Map<Tone, Integer>> assignments) {
        return assignments.values()
                          .stream()
                          .map(colorCounts -> getAverageColor(colorCounts))
                          .collect(Collectors.toList());
    }

    /**
     * Finds the average color for a particular cluster.
     *
     * @param colorCounts The colors being described by the cluster and the number of times they each appeared.
     * @return            The mean color out of all of the colors assigned to the cluster.
     */
    private Tone getAverageColor(Map<Tone, Integer> colorCounts) {
        int pixels = colorCounts.values().stream().collect(Collectors.summingInt(Integer::intValue));

        double averageRed = averageColorChannel(colorCounts, pixels, c -> c.getRed());
        double averageGreen = averageColorChannel(colorCounts, pixels, c -> c.getGreen());
        double averageBlue = averageColorChannel(colorCounts, pixels, c -> c.getBlue());
        double averageAlpha = averageColorChannel(colorCounts, pixels, c -> c.getAlpha());

        return new Tone("", new Color((int) averageRed, (int) averageGreen, (int) averageBlue, (int) averageAlpha));
    }

    /**
     * Takes color counts and averages the value for a particular channel (i.e. red, blue, green or alpha).
     *
     * @param colorCounts     The colors being described by a cluster and the number of times they each appeared.
     * @param pixels          The total number of pixels being described.
     * @param channelAccessor A function that gets the color channel from the Color object itself.
     * @return                The average for that particular color channel.
     */
    private double averageColorChannel(Map<Tone, Integer> colorCounts, int pixels, Function<Color, Integer> channelAccessor){
        return colorCounts.entrySet()
                          .stream()
                          .map(e -> channelAccessor.apply(e.getKey().getColor()) * e.getValue())
                          .collect(Collectors.summingInt(Integer::intValue)) * 1.0 / pixels;
    }

    /**
     * A triple class used to store the closest cluster to a particular color, and the number of times the color appeared.
     */
    private class Assignment {
        private final Tone cluster;
        private final Tone color;
        private final Integer count;

        Assignment(Tone cluster, Tone color, int count){
            this.cluster = cluster;
            this.color = color;
            this.count = count;
        }
    }

    /**
     * Finds the cluster {@link Tone} closest to the target color and creates a Assignment triple storing this data.
     *
     * @param clusters The list of cluster centers.
     * @param color    The Color to match to a cluster.
     * @param count    The number of times the Color appeared.
     * @return         A tuple containing the Color, how many times it appeared, and most importantly, the closest
     *                 cluster.
     */
    private Assignment assignCluster(List<Tone> clusters, Tone color, Integer count) {
        Tone cluster = clusters.stream()
                               .max((o1, o2) -> (int) Math.signum(distanceFunction.getDistance(o2, color) -
                                                                  distanceFunction.getDistance(o1, color)))
                               .get();
        return new Assignment(cluster, color, count);
    }
}
