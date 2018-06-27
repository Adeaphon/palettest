package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;

/**
 * <p>
 * A {@link ColorDistanceFunction} that finds the Manhattan distance between two colors in RGBA space. Effectively
 * this the sum of the distance across each of the four dimensions (red, green, blue, alpha).
 * </p>
 * <p>
 * Please note that because of the nature of transparency, this metric does not work well for very transparent colors.
 * For example, completely transparent red and completely transparent blue will have a high distance despite being
 * identical.
 * </p>
 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a>
 */
public class ManhattanRgbaDistance implements ColorDistanceFunction {

    @Override
    public double getDistance(Tone first, Tone second) {
        return Math.abs(first.getRed() - second.getRed()) +
               Math.abs(first.getGreen() - second.getGreen()) +
               Math.abs(first.getBlue() - second.getBlue()) +
               Math.abs(first.getAlpha() - second.getAlpha());
    }
}
