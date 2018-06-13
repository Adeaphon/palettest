package com.wabradshaw.palettest.analysis;

import java.awt.*;

/**
 * <p>
 * A {@Code Tone} is a named {@link java.awt.Color}, which can be represented in multiple color models.
 * Each {@Code Tone} can be described in terms of RGB (red, green, blue), HSL (hue, saturation, lightness), or
 * HSV (hue, saturation, value).
 * </p>
 * <p>
 * Alpha is also respected within each {@Code Tone}, representing translucency. As such tones are compatible with RGBA,
 * HSLA, and HSVA as well.
 * </p>
 * <p>
 * Please note that two tones are identical if they represent the same underlying color, regardless of whether or not
 * they have two different names.
 * </p>
 * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">RGB</a>
 * @see <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">HSL and HSV</a>
 */
public class Tone {
    private final String name;
    private final Color color;
//  private final double hue;
//  private final double saturationL;
//  private final double saturationV;
//  private final double lightness;
//  private final double value;

    /**
     * Main constructor defining a particular {@link Color} and the name that should represent it.
     *
     * @param name  The name given to the {@link Color}
     * @param color The {@link Color} itself in RGB space.
     */
    public Tone(String name, Color color){
        this.name = name;
        this.color = color;
    }
}
