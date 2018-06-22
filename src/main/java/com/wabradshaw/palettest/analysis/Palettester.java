package com.wabradshaw.palettest.analysis;

import com.sun.istack.internal.NotNull;
import com.wabradshaw.palettest.utils.GraphicsUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
 * <li>analysePalette(Palette, Image) - Used to count how many times each general {@link Tone} in a pre-defined
 *                                      palette is used.</li>
 * <li>analyseAllColors(Image) - Used to take a count of every single color in an image. This treats every hex code as a
 *                               separate {@link Tone}.</li>
 * <li>definePalette(Image, Clusters) - Used to define a new palette from the image, then to count how many times each
 *                                      {@link Tone} is used. Used when the palette is not known up front.</li>
 * </ul>
 */
public class Palettester {

//    private final ColorDistanceFunction distanceFunction;
    /**
     * Default constructor. Sets up a Palettester with the default settings.
     */
    public Palettester(){
        /* no-op */
//        this.distanceFunction = new
    }

    /**
     *
     * @param palette
     * @param image
     * @return
     */
    public PaletteDistribution analysePalette(List<Tone> palette, BufferedImage image){
        Map<Color, Integer> colorCounts = countColors(image);
        //TODO
        return null;
    }

    /**
     * <p>
     * Takes a {@link BufferedImage} and counts how many times each {@link Color} in it appeared. The {@link Color}s are
     * stored as a {@link PaletteDistribution} where each {@link Color} is named by its hex code. Please note that this
     * includes every minute pixel difference, so images often have more colors than expected.
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
                                                             .map(entry -> toToneCount(entry.getKey(), entry.getValue()))
                                                             .collect(Collectors.toList()));
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
                                          .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));

        return counts.entrySet().stream()
                                .collect(Collectors.toMap(
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
    private ToneCount toToneCount(Color color, Integer count){
        Map<Color, Integer> colorMap = new HashMap<>();
        colorMap.put(color, count);
        return new ToneCount(new Tone(color),colorMap);
    }
}
