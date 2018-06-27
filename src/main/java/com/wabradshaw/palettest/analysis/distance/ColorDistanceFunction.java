package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;

/**
 * <p>
 * An interface for classes which measure distance between two different colors. The metric used is different for each
 * distance function, but each function should return 0 for two identical colors, and it should increase from there.
 * Distance functions should always be absolute, so should never return below zero.
 * </p>
 * <p>
 * There are two methods available on a distance function - {@link #getDistance(Tone, Tone)} and
 * {@link #getRankingDistance(Tone, Tone)}. While they both get a distance between two colors, getDistance gets an
 * exact measure of distance according to the function, but getRankingDistance gets an approximation suitable for
 * ranking. For example, in a Euclidean distance function, the actual distance metric is the square root of the sum of
 * the distance squared between points in each dimension. However, if you are computing a ranking, computing the
 * square root is an unnecessary calculation (if a > b, then root(a) > root(b)).
 * </p>
 * <p>
 * Distance functions are symmetrical, so getDistance(a,b) should be the same as getDistance(b,a).
 * </p>
 * <p>
 * {@link Tone}s are used rather than {@link java.awt.Color}s to enable distance in RGBA, HSVA, or HSLA color spaces.
 * </p>
 * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
 * @see <a href="https://en.wikipedia.org/wiki/Taxicab_geometry">Manhattan distance</a>
 */
public interface ColorDistanceFunction {

    /**
     * <p>
     * Gets this metric's distance between two {@link Tone}s. This will be zero for two identical {@link Tone}s, and
     * the greater the number the further apart the colors are.
     * </p>
     * <p>
     * Distance functions are symmetrical, so getDistance(a,b) should be the same as getDistance(b,a).
     * </p>
     * @param first  The first {@link Tone}.
     * @param second The second {@link Tone}.
     * @return       A non-negative double representing how apart the two {@link Tone}s are, with 0 being identical.
     */
    public double getDistance(Tone first, Tone second);

    /**
     * <p>
     * Gets an approximation of the distance between two {@link Tone}s suitable for ranking. This allows distance
     * functions to use more efficient algorithms when a linear distance isn't needed. This will be zero for two
     * identical {@link Tone}s, and the greater the number the further apart the colors are.
     * </p>
     * <p>
     * For example, in a Euclidean distance function, the actual distance metric is the square root of the sum of
     * the distance squared between points in each dimension. However, if you are computing a ranking, computing the
     * square root is an unnecessary calculation (if a > b, then root(a) > root(b)).
     * </p>
     * <p>
     * Distance functions are symmetrical, so getDistance(a,b) should be the same as getDistance(b,a).
     * </p>
     * @param first  The first {@link Tone}.
     * @param second The second {@link Tone}.
     * @return       A non-negative double representing how apart the two {@link Tone}s are, with 0 being identical.
     */
    public default double getRankingDistance(Tone first, Tone second){
        return getDistance(first, second);
    }
}
