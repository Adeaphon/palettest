package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Palettester;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.CompuPhaseDistance;
import com.wabradshaw.palettest.utils.GraphicsUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A palette replacement is a type of visualisation where an image is recolored according to its palette. Every pixel in
 * the input image is replaced with the closest pixel color in the palette.
 */
public class PaletteReplacer {

    private final ColorDistanceFunction distanceFunction;

    /**
     * Default constructor. Sets up a {@link PaletteReplacer} using {@link CompuPhaseDistance} to measure the distance
     * between colors.
     */
    public PaletteReplacer(){
        this(new CompuPhaseDistance());
    }

    /**
     * Configurable constructor. Sets up a {@link PaletteReplacer} that will use the supplied distance function to
     * measure the distance between colors.
     *
     * @param distanceFunction The distance function to use to find the nearest color.
     */
    public PaletteReplacer(ColorDistanceFunction distanceFunction){
        this.distanceFunction = distanceFunction;
    }

    /**
     * Takes in an image and creates a new version where each pixel has been mapped to the nearest color in the palette.
     *
     * @param image   The image to recolor.
     * @param palette The palette the new image should use.
     * @return        A version of the input image with each pixel redrawn according to the palette.
     */
    public BufferedImage replace(BufferedImage image, List<Tone> palette) {
        image = GraphicsUtils.createCopy(image);

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Map<Color, Color> closestTones = getClosestTonesCache(image, palette);

        Graphics2D g = result.createGraphics();

        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                Color original = new Color(image.getRGB(x, y));
                Color target = closestTones.get(original);
                g.setPaint(target);
                g.drawLine(x, y, x+1, y+1);
            }
        }

        g.dispose();

        return result;
    }

    /**
     * Creates a cache of which {@link Color} in the image should be replaced by which Color from a {@link Tone} in the
     * palette.
     *
     * @param image   The image to analyse.
     * @param palette The list of {@link Tone}s that can be used.
     * @return        A map of original {@link Color} to target {@link Color}.
     */
    private Map<Color,Color> getClosestTonesCache(BufferedImage image, List<Tone> palette) {
        Palettester tester = new Palettester(this.distanceFunction);
        PaletteDistribution colors = tester.analyseAllColors(image);
        return colors.getDistribution()
                     .stream()
                     .map(ToneCount::getTone)
                     .collect(Collectors.toMap(
                             Tone::getColor,
                             x -> getClosestTone(palette, x).getColor()
                     ));
    }


    /**
     * Finds the {@link Tone} closest to the target color.
     *
     * @param palette The list of possible Tones in the palette.
     * @return        The closest Tone in the palette.
     */
    private Tone getClosestTone(List<Tone> palette, Tone targetTone) {
        return palette.stream()
                .max((o1, o2) -> (int) Math.signum(distanceFunction.getDistance(o2, targetTone) -
                                                   distanceFunction.getDistance(o1, targetTone)))
                .get();
    }
}
