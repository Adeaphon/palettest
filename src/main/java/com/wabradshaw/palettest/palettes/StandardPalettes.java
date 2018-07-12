package com.wabradshaw.palettest.palettes;

import com.wabradshaw.palettest.analysis.Tone;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * This class contains a list of common palettes. Currently available are:
 * </p>
 * <ul>
 * <li>JAVA_COLORS - The standard colors available through the Color class</li>
 * <li>RAINBOW - The colors of the rainbow.</li>
 * <li>RAINBOW_BW - The colors of the rainbow, as well as black and white.</li>
 * </ul>
 */
public class StandardPalettes {

    /**
     * A palette containing the standard colors available through the Color class.
     */
    public static final List<Tone> JAVA_COLORS = Arrays.asList(new Tone("Red", Color.red),
                                                               new Tone("Green", Color.green),
                                                               new Tone("Blue", Color.blue),
                                                               new Tone("Yellow", Color.yellow),
                                                               new Tone("Cyan", Color.cyan),
                                                               new Tone("Magenta", Color.magenta),
                                                               new Tone("White", Color.white),
                                                               new Tone("Black", Color.black),
                                                               new Tone("Gray", Color.gray),
                                                               new Tone("Light Gray", Color.lightGray),
                                                               new Tone("Dark Gray", Color.darkGray),
                                                               new Tone("Orange", Color.orange),
                                                               new Tone("Pink", Color.pink));

    /**
     * A palette containing the colors of the rainbow. Color codes found on Webnots.com
     * @see <a href="https://www.webnots.com/vibgyor-rainbow-color-codes/">Color codes</a>
     */
    public static final List<Tone> RAINBOW = Arrays.asList(new Tone("Violet", new Color(148,0,211)),
                                                           new Tone("Indigo", new Color(75,0,130)),
                                                           new Tone("Blue", new Color(0,0,255)),
                                                           new Tone("Green", new Color(0,255,0)),
                                                           new Tone("Yellow", new Color(255,255,0)),
                                                           new Tone("Orange", new Color(255,127,0)),
                                                           new Tone("Red", new Color(255,0,0)));

    /**
     * A palette containing the colors of the rainbow, as well as black and white. Color codes found on Webnots.com
     * @see <a href="https://www.webnots.com/vibgyor-rainbow-color-codes/">Color codes</a>
     */
    public static final List<Tone> RAINBOW_BW = Arrays.asList(new Tone("Violet", new Color(148,0,211)),
                                                              new Tone("Indigo", new Color(75,0,130)),
                                                              new Tone("Blue", new Color(0,0,255)),
                                                              new Tone("Green", new Color(0,255,0)),
                                                              new Tone("Yellow", new Color(255,255,0)),
                                                              new Tone("Orange", new Color(255,127,0)),
                                                              new Tone("Red", new Color(255,0,0)),
                                                              new Tone("Black", new Color(0,0,0)),
                                                              new Tone("White", new Color(255,255,255)));
}
