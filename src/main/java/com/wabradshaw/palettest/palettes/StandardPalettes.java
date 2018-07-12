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
}
