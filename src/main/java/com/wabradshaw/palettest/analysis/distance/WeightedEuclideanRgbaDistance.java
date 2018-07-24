package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;

/**
 * <p>
 * A version of the {@link EuclideanRgbaDistance} {@link ColorDistanceFunction} that finds the Euclidean distance
 * between two colors in RGBA space. Effectively this means that the colors are treated as points in 4d space
 * (red, green, blue, alpha), and this measures a theoretical straight line between them.
 * </p>
 * <p>
 * This distance function is different to a normal {@link EuclideanRgbaDistance} in that each color channel is
 * given a different weighting. For example, a difference in green may appear more different than a difference in
 * blue. The overall weightings are normalised, so distances could be compared between different weightings.
 * </p>
 * <p>
 * Please note that because of the nature of transparency, this metric does not work well for very transparent colors.
 * For example, completely transparent red and completely transparent blue will have a high distance despite being
 * identical. The alpha channel is able to be weighted like the other channels.
 * </p>
 * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
 * @see EuclideanRgbaDistance
 */
public class WeightedEuclideanRgbaDistance implements ColorDistanceFunction {

    private final double redWeight;
    private final double greenWeight;
    private final double blueWeight;
    private final double alphaWeight;
    private final double normalisingFactor;

    /**
     * Default constructor. Sets up a {@link WeightedEuclideanRgbaDistance} function with a reasonable initial
     * weighting. Specifically, the weightings are:
     * <ul>
     * <li>Red - 2x</li>
     * <li>Green - 4x</li>
     * <li>Blue - 3x</li>
     * <li>Alpha - 1x</li>
     * </ul>
     * <p>
     * This means that a difference in the amount of green is worth double the same difference in red.
     * </p>
     * @see <a href="https://en.wikipedia.org/wiki/Color_difference">Intial weightings</a>
     */
    public WeightedEuclideanRgbaDistance(){
        this(2,4,3,1);
    }

    /**
     * Configurable constructor. Sets up a {@link WeightedEuclideanRgbaDistance} function with the supplied weighting.
     * The higher the number, the greater the effect of distance in that channel.
     *
     * @param redWeight   How big of an effect a difference in red should have.
     * @param greenWeight How big of an effect a difference in green should have.
     * @param blueWeight  How big of an effect a difference in blue should have.
     * @param alphaWeight How big of an effect a difference in alpha should have.
     */
    public WeightedEuclideanRgbaDistance(double redWeight, double greenWeight, double blueWeight, double alphaWeight){
        this.redWeight = redWeight;
        this.blueWeight = blueWeight;
        this.greenWeight = greenWeight;
        this.alphaWeight = alphaWeight;
        this.normalisingFactor = (redWeight + blueWeight + greenWeight + alphaWeight) / 4;
    }

    @Override
    public double getDistance(Tone first, Tone second) {
        return Math.sqrt(getRankingDistance(first, second) / normalisingFactor);
    }

    @Override
    public double getRankingDistance(Tone first, Tone second) {
        return (redWeight * Math.pow((first.getRed() - second.getRed()), 2)) +
               (greenWeight * Math.pow((first.getGreen() - second.getGreen()), 2)) +
               (blueWeight * Math.pow((first.getBlue() - second.getBlue()), 2)) +
               (alphaWeight * Math.pow((first.getAlpha() - second.getAlpha()), 2));
    }
}
