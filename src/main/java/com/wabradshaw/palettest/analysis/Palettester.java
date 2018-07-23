package com.wabradshaw.palettest.analysis;

import com.wabradshaw.palettest.analysis.clustering.ClusteringAlgorithm;
import com.wabradshaw.palettest.analysis.clustering.WeightedKMeansClusterer;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;
import com.wabradshaw.palettest.analysis.naming.ColorNamer;
import com.wabradshaw.palettest.analysis.naming.SimplePaletteColorNamer;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.GraphicsUtils;
import com.wabradshaw.palettest.visualisation.PaletteReplacer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * <p>
 * The main class behind the whole Palettest project. A {@link Palettester} is used to analyse the colors that make up
 * an image. As it is often inconvenient to test against every single pixel color in an image, the notion of palettes is
 * used to cluster similar colors together into a general {@link Tone}. For example, #ff0000 and #ff00001 would likely
 * be part of a single 'red' {@link Tone}. The collection of {@link Tone}s to test against is called a palette.
 *</p>
 * <p>
 * An image is tested using three main methods:
 * </p>
 * <ul>
 * <li>analysePalette   - Used to count how many times each general {@link Tone} in a pre-defined palette is used.</li>
 * <li>analyseAllColors - Used to take a count of every single color in an image. This treats every hex code as a
 *                        separate {@link Tone}.</li>
 * <li>definePalette    - Used to define a new palette from the image, then to count how many times each {@link Tone}
 *                        is used. Used when the palette is not known up front.</li>
 * </ul>
 */
public class Palettester {

    private final List<Tone> defaultPalette;
    private final ColorDistanceFunction distanceFunction;
    private final ClusteringAlgorithm clusteringAlgorithm;
    private final ColorNamer namer;

    /**
     * Default constructor. Sets up a Palettester with the default settings. Specifically that means that it will use
     * the PWG Standard palette, {@link EuclideanRgbaDistance} when computing similarity,
     * {@link WeightedKMeansClusterer} to find new palettes, and {@link SimplePaletteColorNamer} to name new Colors.
     *
     * @see StandardPalettes#PWG_STANDARD
     * @see EuclideanRgbaDistance
     * @see WeightedKMeansClusterer
     * @see SimplePaletteColorNamer
     */
    public Palettester(){
        this(null, null, null, null);
    }

    /**
     * <p>
     * Full configuration constructor. Sets up a completely custom {@link Palettester}. All arguments are optional, if
     * null is supplied then the deefault will be used.
     * </p>
     * <p>The defaults are:</p>
     * <ul>
     * <li>defaultPalette - The PWG Standard palette from {@link StandardPalettes}.</li>
     * <li>distanceFunction - A {@link EuclideanRgbaDistance} function to measure {@link Color} similarity.</li>
     * <li>clusteringAlgorithm - A {@link WeightedKMeansClusterer} to define palettes from images.</li>
     * <li>namer - A {@link SimplePaletteColorNamer} to supply names to new {@link Color}s.</li>
     * </ul>
     * @see StandardPalettes#PWG_STANDARD
     * @see EuclideanRgbaDistance
     * @see WeightedKMeansClusterer
     * @see SimplePaletteColorNamer
     *
     * @param defaultPalette      A list of {@link Tone}s to serve as the default color palette for analysis and naming.
     *                            If null, defaults to the PWG Standard palette.
     * @param distanceFunction    A {@link ColorDistanceFunction} to use to measure the distance between different
     *                            {@link Color}s. If null, defaults to the {@link EuclideanRgbaDistance} function.
     * @param clusteringAlgorithm A {@link ClusteringAlgorithm} to use to define palettes from images. If null,
     *                            defaults to a {@link WeightedKMeansClusterer}.
     * @param namer               A {@link ColorNamer} to use to supply names for {@link Color}s when defining a new
     *                            {@link Color} palette. If null, defaults to a {@link SimplePaletteColorNamer}.
     */
    public Palettester(List<Tone> defaultPalette,
                       ColorDistanceFunction distanceFunction,
                       ClusteringAlgorithm clusteringAlgorithm,
                       ColorNamer namer){

        this.defaultPalette = defaultPalette == null ? StandardPalettes.PWG_STANDARD : defaultPalette;
        this.distanceFunction = distanceFunction == null ? new EuclideanRgbaDistance() : distanceFunction;
        this.clusteringAlgorithm = clusteringAlgorithm == null ? new WeightedKMeansClusterer() : clusteringAlgorithm;
        this.namer = namer == null ? new SimplePaletteColorNamer() : namer;
    }

    /**
     * <p>
     * Takes a {@link BufferedImage} and analyses how many times each {@link Tone} in the supplied color palette
     * appears. Each individual {@link Color} in the image is mapped to the closest {@link Tone}. The results are
     * stored as a {@link PaletteDistribution} where each {@link Tone} has a total pixel count, as well as a map of
     * exactly which {@link Color} pixels were attributed to that {@link Tone}. Only {@link Tone}s in the palette that
     * were also in the image will appear in the {@link PaletteDistribution}.
     * </p>
     * @param palette The palette of {@link Tone}s which should be used in the final description.
     * @param image   The {@link BufferedImage} to be described.
     * @return        A {@link PaletteDistribution} containing all of the {@link Tone}s that were used in the image,
     *                and the number of pixels that can be attributed to each {@link Tone}.
     */
    public PaletteDistribution analysePalette(List<Tone> palette, BufferedImage image){
        Map<Color, Integer> colorCounts = countColors(image);

        Map<Tone, Map<Color, Integer>> toneCounts = colorCounts
                                                    .entrySet()
                                                    .stream()
                                                    .map(cC -> getClosestTone(palette, cC.getKey(), cC.getValue()))
                                                    .collect(groupingBy(closestTone -> closestTone.tone,
                                                                        toMap(closestTone -> closestTone.color,
                                                                              closestTone -> closestTone.count)));

       return new PaletteDistribution(toneCounts.entrySet()
                                                .stream()
                                                .map(tc -> new ToneCount(tc.getKey(), tc.getValue()))
                                                .collect(toList()));
    }

    /**
     * <p>
     * Takes a {@link BufferedImage} and analyses how many times each {@link Tone} in the {@link Palettester}s color
     * palette is used. Each individual {@link Color} in the image is mapped to the closest {@link Tone}. The results
     * are stored as a {@link PaletteDistribution} where each {@link Tone} has a total pixel count, as well as a map of
     * exactly which {@link Color} pixels were attributed to that {@link Tone}. Only {@link Tone}s in the palette that
     * were also in the image will appear in the {@link PaletteDistribution}.
     * </p>
     * @param image   The {@link BufferedImage} to be described.
     * @return        A {@link PaletteDistribution} containing all of the {@link Tone}s that were used in the image,
     *                and the number of pixels that can be attributed to each {@link Tone}.
     */
    public PaletteDistribution analysePalette(BufferedImage image){
        return this.analysePalette(this.defaultPalette, image);
    }

    /**
     * <p>
     * Takes a {@link BufferedImage} and counts how many times each {@link Color} in it appeared. The {@link Color}s are
     * stored as a {@link PaletteDistribution} where each {@link Color} is named by its hex code. Please note that this
     * includes every minute pixel difference, so images often have more colors than expected. Only {@link Color}s
     * which were in the image will be in the {@link PaletteDistribution}.
     * </p>
     * <p>
     * Color analysis is done in 8-bit RGB, so differences more granular than that will not be picked up.
     * </p>
     * @param image The {@link BufferedImage} to analyse
     * @return      A {@link PaletteDistribution} listing each color present in the image, and how many times they
     *              appeared.
     */
    public PaletteDistribution analyseAllColors(BufferedImage image){
        Map<Color, Integer> colorCounts = countColors(image);

        return new PaletteDistribution(colorCounts.entrySet().stream()
                                                             .map(entry -> toSingleToneCount(entry.getKey(), entry.getValue()))
                                                             .collect(toList()));
    }

    /**
     * <p>
     * Takes in a {@link BufferedImage} and analyses it as the basis for a color palette. The result is a list of
     * {@link Tone}s that cover the major colors used in the image.
     * </p>
     * <p>
     * Once a palette has been created, it has two main uses: analysis and visualisation. An image can be analysed
     * using {@link #analysePalette(List, BufferedImage)}, which shows how many pixels belong to each color of the
     * palette. The palette itslef can also be visualised using the
     * {@link com.wabradshaw.palettest.visualisation.PaletteVisualiser}, but this does not reflect the balance of the
     * palette.
     * </p>
     * <p>
     * The exact results are dependant on the clustering algorithm being used. However, these algorithms are frequently
     * non-deterministic. The exact palette may change slightly between calls.
     * </p>
     * <p>
     * Please note that this method can take some time for complex images.
     * </p>
     * @param image    The {@link BufferedImage} to be described.
     * @param maxTones The maximum number of different color {@link Tone}s in the palette.
     * @return         A palette of {@link Tone}s used in the image.
     */
    public List<Tone> definePalette(BufferedImage image, int maxTones){
        Map<Color, Integer> colorCounts = countColors(image);

        Collection<Color> paletteColors;
        if(colorCounts.keySet().size() <= maxTones){
            paletteColors = colorCounts.keySet();
        } else {
            paletteColors = clusteringAlgorithm.cluster(colorCounts, maxTones);
        }

        return namer.nameTones(paletteColors, this.defaultPalette);
    }

    /**
     * Counts the number of pixels of each Color in an image.
     *
     * @param image The image being analysed
     * @return      A map of Colors and the number of times they appeared in the image.
     */
    private Map<Color, Integer> countColors(BufferedImage image){
        image = GraphicsUtils.createCopy(image);

        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

        Map<Integer, Long> counts = Arrays.stream(pixels)
                                          .boxed()
                                          .collect(groupingBy(Integer::intValue, counting()));

        return counts.entrySet().stream()
                                .collect(toMap(
                                    e -> new Color(e.getKey()),
                                    e -> e.getValue().intValue()
                                ));
    }

    /**
     * Converts a color count into a ToneCount which only has that color.
     *
     * @param color The color being counted.
     * @param count The number of times that color appeared.
     * @return      A ToneCount representing the color/count.
     */
    private ToneCount toSingleToneCount(Color color, Integer count){
        Map<Color, Integer> colorMap = new HashMap<>();
        colorMap.put(color, count);
        return new ToneCount(new Tone(color),colorMap);
    }

    /**
     * A triple class used to store the closest tone to a particular color, and the number of times the color appeared.
     */
    private class ClosestTone {
        private final Tone tone;
        private final Color color;
        private final Integer count;

        ClosestTone(Tone tone, Color color, int count){
            this.tone = tone;
            this.color = color;
            this.count = count;
        }
    }

    /**
     * Finds the {@link Tone} closest to the target color and creates a ClosestTone triple storing this data.
     *
     * @param palette The list of possible Tones in the palette.
     * @param color   The Color to match to a Tone.
     * @param count   The number of times the Color appeared.
     * @return        A tuple containing the Color, how many times it appeared, and most importantly, the closest tone
     *                in the palette.
     */
    private ClosestTone getClosestTone(List<Tone> palette, Color color, Integer count) {
        Tone targetTone = new Tone("", color);
        Tone resultTone = palette.stream()
                .max((o1, o2) -> (int) Math.signum(distanceFunction.getDistance(o2, targetTone) -
                                                   distanceFunction.getDistance(o1, targetTone)))
                .get();
        return new ClosestTone(resultTone, color, count);
    }
}
