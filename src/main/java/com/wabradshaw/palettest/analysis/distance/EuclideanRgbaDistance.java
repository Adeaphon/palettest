package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;

/**
 * <p>
 * A {@link ColorDistanceFunction} that finds the Euclidean distance between two colors in RGBA space. Effectively
 * this means that the colors are treated as points in 4d space (red, green, blue, alpha), and this measures a
 * theoretical straight line between them.
 * </p>
 * <p>
 * Please note that because of the nature of transparency, this metric does not work well for very transparent colors.
 * For example, completely transparent red and completely transparent blue will have a high distance despite being
 * identical.
 * </p>
 * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
 */
public class EuclideanRgbaDistance implements ColorDistanceFunction {

    @Override
    public double getDistance(Tone first, Tone second) {
        return Math.sqrt(getRankingDistance(first, second));
    }

    @Override
    public double getRankingDistance(Tone first, Tone second) {
        return Math.pow((first.getRed() - second.getRed()), 2) +
               Math.pow((first.getGreen() - second.getGreen()), 2) +
               Math.pow((first.getBlue() - second.getBlue()), 2) +
               Math.pow((first.getAlpha() - second.getAlpha()), 2);
    }
}
