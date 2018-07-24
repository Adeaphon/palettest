package com.wabradshaw.palettest.analysis.distance;

import com.wabradshaw.palettest.analysis.Tone;

/**
 * <p>
 * A variation on the {@link WeightedEuclideanRgbaDistance} {@link ColorDistanceFunction} that varies the weightings
 * based on the amount of red in the image. The algorithm itself was created by CompuPhase, and is used in a number of
 * their products.
 * </p>
 * <p>
 * Please note that because of the nature of transparency, this metric does not work well for very transparent colors.
 * For example, completely transparent red and completely transparent blue will have a high distance despite being
 * identical. The alpha channel is able to be weighted like the other channels.
 * </p>
 * @see <a href="https://www.compuphase.com/cmetric.htm">CompuPhase Algorithm</a>
 * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
 */
public class CompuPhaseDistance implements ColorDistanceFunction {

    @Override
    public double getDistance(Tone first, Tone second) {
        return Math.sqrt(getRankingDistance(first, second) / 2.5);
    }

    @Override
    public double getRankingDistance(Tone first, Tone second) {
        double redBase = (first.getRed() + second.getRed())/2.0;
        double redMod = redBase/256.0;
        double blueMod = (256-redBase)/256.0;

        return ((2+redMod) * Math.pow((first.getRed() - second.getRed()), 2)) +
               (4 * Math.pow((first.getGreen() - second.getGreen()), 2)) +
               ((2+blueMod) * Math.pow((first.getBlue() - second.getBlue()), 2)) +
               (Math.pow((first.getAlpha() - second.getAlpha()), 2));
    }
}
