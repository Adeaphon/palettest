package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A palette replacement is a type of visualisation where an image is recolored according to its palette. Every pixel in
 * the input image is replaced with the closest pixel color in the palette.
 */
public class PaletteReplacer {

    /**
     * Takes in an image and creates a new version where each pixel has been mapped to the nearest color in the palette.
     *
     * @param image   The image to recolor.
     * @param palette The palette the new image should use.
     * @return        A version of the input image with each pixel redrawn according to the palette.
     */
    public BufferedImage replace(BufferedImage image, List<Tone> palette) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        return result;
    }
}
