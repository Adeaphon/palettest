package com.wabradshaw.palettest.analysis;

import java.awt.Color;
import java.util.Objects;

/**
 * <p>
 * A {@code Tone} is a named {@link java.awt.Color}, which can be represented in multiple color models.
 * Each {@code Tone} can be described in terms of RGB (red, green, blue), HSL (hue, saturation, lightness), or
 * HSV (hue, saturation, value).
 * </p>
 * <p>
 * Alpha is also respected within each {@code Tone}, representing translucency. As such tones are compatible with RGBA,
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
    private final double hue;
    private final double saturationL;
    private final double saturationV;
    private final double lightness;
    private final double value;

    /**
     * <p>
     * Main constructor defining a particular {@link Color} and the name that should represent it.
     * </p>
     * <p>
     * If name is null, then a new name will be created based on its RGB makeup.
     * For example, pure red will become '#ff0000', green will be '#00ff00', and purple will be '#ff00ff'.
     * Please note that if name is not supplied, two {@code Tones} with the same red, green, and blue, but with
     * different alpha components will get the same name. This is done to avoid confusion about whether the code is
     * ARGB or RGBA.
     * </p>
     * @param name  The name given to the {@link Color}
     * @param color The {@link Color} being named. Cannot be null.
     */
    public Tone(String name, Color color){
        if(color == null){
            throw new IllegalArgumentException("A Tone called " + name + " was created without a Color.");
        }

        this.name = name != null ? name :  getHexName(color);
        this.color = color;

        //See https://en.wikipedia.org/wiki/HSL_and_HSV for conversion formulae
        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;

        double max = Math.max(r, Math.max(g,b));
        double min = Math.min(r, Math.min(g,b));
        double chroma = max - min;

        double rawHue;
        if(chroma == 0){
            rawHue = 0;
        } else if(r == max){
            rawHue = (g-b)/chroma;
        } else if(g == max) {
            rawHue = 2.0 + ((b-r)/chroma);
        } else {
            rawHue = 4.0 + ((r-g)/chroma);
        }

        this.hue = (rawHue * 60) % 360;
        this.lightness = 0.5 * (max + min);
        this.value = max;
        this.saturationL = this.lightness == 0 ? 0 : chroma / (1 - Math.abs(2*this.lightness - 1));
        this.saturationV = this.value == 0 ? 0 : chroma / this.value;
    }

    /**
     * <p>
     * Unnamed constructor which will define a particular {@link Color} and give it a name based on its RGB makeup.
     * For example, pure red will become '#ff0000', green will be '#00ff00', and purple will be '#ff00ff'.
     * </p>
     * <p>
     * Please note that two {@code Tones} with the same red, green, and blue, but with different alpha components
     * will get the same name. This is done to avoid confusion about whether the code is ARGB or RGBA.
     * </p>
     *
     * @param color The {@link Color} being named. Cannot be null.
     */
    public Tone(Color color){
        this(null, color);
    }

    /**
     * Gets the given name for this {@link Tone}.
     *
     * @return The given name for this {@link Tone}.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the RGBA {@link Color} this {@link Tone} represents.
     *
     * @return The RGBA {@link Color} this {@link Tone} represents.
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Gets the Red component of the {@link Color}, as a value from 0 to 255.
     *
     * @return The Red component of the {@link Color}r, as a value from 0 to 255.
     */
    public int getRed() {
        return this.color.getRed();
    }

    /**
     * Gets the Green component of the {@link Color}, as a value from 0 to 255.
     *
     * @return The Green component of the {@link Color}, as a value from 0 to 255.
     */
    public int getGreen() {
        return this.color.getGreen();
    }

    /**
     * Gets the Blue component of the {@link Color}, as a value from 0 to 255.
     *
     * @return The Blue component of the {@link Color}, as a value from 0 to 255.
     */
    public int getBlue() {
        return this.color.getBlue();
    }

    /**
     * Gets the opacity of the {@link Color}, as a value from 0 to 100.
     *
     * @return The opacity of the {@link Color}, as a value from 0 to 100.
     */
    public int getAlpha() {
        return this.color.getAlpha();
    }

    /**
     * <p>
     * Gets the hue of the color, representing which primary color it is closest to. This is represented as a degree
     * from 0 to 360, with 0 or 360 representing red, green at 120, and blue at 240.
     * </p>
     * <p>
     * Strictly speaking, greyscale colors have no hue, but they are given a hue of 0 for convenience.
     * </p>
     *
     * @return The hue as a value from 0 (inclusive) to 360 (exclusive).
     */
    public double getHue() {
        return hue;
    }

    /**
     * <p>
     * Gets the colorfulness of the tone, relative to its own brightness, when using an HSL color model. This is
     * represented as a value between 0 to 1 where 0 represents colorless, and 1 is a pure primary color.
     * </p>
     * <p>
     * Please note that saturation differs between HSL and HSV. This is for HSL. For HSV, see getSaturationV.
     * </p>
     * @return The HSL saturation value of the color as a value from 0 to 1, inclusive.
     * @see #getSaturationV()
     */
    public double getSaturationL(){
        return saturationL;
    }

    /**
     * <p>
     * Gets the colorfulness of the tone, relative to its own brightness, when using an HSV color model. This is
     * represented as a value between 0 to 1 where 0 represents colorless, and 1 is a pure primary color.
     * </p>
     * <p>
     * Please note that saturation differs between HSV and HSL. This is for HSV. For HSL, see getSaturationL.
     * </p>
     * @return The HSV saturation value of the color as a value from 0 to 1, inclusive.
     * @see #getSaturationL()
     */
    public double getSaturationV(){
        return saturationV;
    }

    /**
     * <p>
     * Gets the lightness of the color, representing the brightness relative to white. This is represented from 0 to 1,
     * where 1 represents pure white and 0 represents pure black.
     * </p>
     * @return The lightness of the color as a value from 0 to 1, inclusive.
     */
    public double getLightness(){
        return lightness;
    }

    /**
     * <p>
     * Gets the brightness value of the color, representing the brightness of the largest primary color. This is
     * represented from 0 to 1, where 1 is a clear version of the colour and 0 represents black.
     * </p>
     * @return The brightness value of the color as a value from 0 to 1, inclusive.
     */
    public double getValue(){
        return value;
    }

    /**
     * A method to generate a readable name for the underlying color's RBG values.
     *
     * @param color The color to name.
     * @return      The color in the format #rrggbb.
     */
    private String getHexName(Color color){
        return '#' + Integer.toHexString(color.getRGB()).substring(2);
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        } else if (!(o instanceof Tone)){
            return false;
        } else {
            return Objects.equals(this.color, ((Tone) o).getColor());
        }
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.color);
    }

    @Override
    public String toString(){
        return this.name + " (" + getHexName(this.color) + ")";
    }
}
