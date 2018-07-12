package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.ToneCount;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * <p>
 * A DistributionPainter produces visualisations from {@link PaletteDistribution}s. It takes in a list of
 * {@link ToneCount}s from a {@link PaletteDistribution} then redraws each of the pixels in a new image.
 * </p>
 */
public class DistributionPainter {

    /**
     * <p>
     * Paints a new picture using the {@link com.wabradshaw.palettest.analysis.Tone}s present in the {@link ToneCount}s
     * from a {@link PaletteDistribution}. The resulting image will use the same number of pixels in each
     * {@link ToneCount} as the input, in the same order horizontally.
     * </p>
     * <p>
     * If there are more pixels in the {@link ToneCount}s than in the target image, later {@link Tone}s
     * may not be drawn. If the target image is larger, the right side of the image will be transparent.
     * </p>
     * @param orderedDistribution The list of {@link ToneCount}s that should be represented in the new image, in order
     *                            of appearance in the new image.
     * @param targetWidth         The number of pixels wide the new image should be.
     * @param targetHeight        The number of pixels high the new image should be.
     * @return                    A new {@link BufferedImage} visualising the ordered {@link ToneCount} distribution.
     */
    public BufferedImage paintTones(List<ToneCount> orderedDistribution, int targetWidth, int targetHeight){
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        return result;
    }
}
