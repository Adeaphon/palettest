package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;
import com.wabradshaw.palettest.utils.GraphicsUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A palette replacement is a type of visualisation where an image is recolored according to its palette. Every pixel in
 * the input image is replaced with the closest pixel color in the palette.
 */
public class PaletteReplacer {

    private final ColorDistanceFunction distanceFunction;

    /**
     * Default constructor. Sets up a {@link PaletteReplacer} using {@link EuclideanRgbaDistance} to measure distance.
     */
    public PaletteReplacer(){
        this.distanceFunction = new EuclideanRgbaDistance();
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

        Graphics2D g = result.createGraphics();

        int currentX = 0;
        int currentY = 0;

        for(int x = 0; x < image.getWidth(); x++){
            for(int y = 0; y < image.getHeight(); y++){
                Color original = new Color(image.getRGB(x, y));
                Tone target = getClosestTone(palette, new Tone("", original));
                g.setPaint(target.getColor());
                g.drawLine(x, y, x+1, y+1);
            }
        }

        g.dispose();

        return result;
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
