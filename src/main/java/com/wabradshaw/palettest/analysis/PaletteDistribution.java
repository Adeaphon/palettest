package com.wabradshaw.palettest.analysis;

import java.util.List;

/**
 * A {@link PaletteDistribution} is a record of the different {@link Tone}s used in an image. It stores all of the
 * {@link Tone}s that have been used alongside how many times they were used as {@link ToneCount}s. The counts can be
 * retrieved through various sorting methods such as by count, by red, or by hue.
 */
public class PaletteDistribution {

    private final List<ToneCount> counts;

    /**
     * Main constructor producing {@link PaletteDistribution}s. However, typical people should not need to call this
     * themselves, but instead should use a {@link Palettester} to analyse the count from an image. This constructor
     * has been left public to make testing easier.
     *
     * @param counts The list of {@link ToneCount}s that make up the distribution
     */
    public PaletteDistribution(List<ToneCount> counts){
        this.counts = counts;
    }

    /**
     * Gets the {@link ToneCount}s representing the number of times each {@link Tone} was used within the palette.
     * These counts are returned in insertion order, so it depends on the settings of the {@link Palettester} used
     * to produce the {@link PaletteDistribution}.
     *
     * @return the {@link ToneCount}s in insertion order.
     */
    public List<ToneCount> getDistribution(){
        return this.counts;
    }
}
