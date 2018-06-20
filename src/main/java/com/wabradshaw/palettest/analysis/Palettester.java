package com.wabradshaw.palettest.analysis;

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
 * <li>analyseColors(Image) - Used to take a count of every single color in an image. This treats every hex code as a
 *                            separate {@link Tone}.</li>
 * <li>definePalette(Image, Clusters) - Used to define a new palette from the image, then to count how many times each
 *                                      {@link Tone} is used. Used when the palette is not known up front.</li>
 * </ul>
 */
public class Palettester {

    public Palettester(){
        /* no-op */
    }

}
