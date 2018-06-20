package com.wabradshaw.palettest.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link PaletteDistribution} is a record of the different {@link Tone}s used in an image. It stores all of the
 * {@link Tone}s that have been used alongside how many times they were used as {@link ToneCount}s. The counts can be
 * retrieved through various sorting methods such as by count, by red, or by hue.
 * </p>
 * <p>
 * Please note that {@link PaletteDistribution}s are immutable. If the list of counts changes after the distribution
 * has been created, it will not reflect those changes.
 * </p>
 */
public class PaletteDistribution {

    private final List<ToneCount> counts;

    /**
     * Main constructor producing {@link PaletteDistribution}s. However, typical people should not need to call this
     * themselves, but instead should use a {@link Palettester} to analyse the count from an image. This constructor
     * has been left public to make testing easier.
     *
     * @param counts The list of {@link ToneCount}s that make up the distribution. This list is copied to keep the
     *               {@link PaletteDistribution} immutable. Cannot be null.
     */
    public PaletteDistribution(List<ToneCount> counts){
        if(counts == null){
            throw new IllegalArgumentException("A PaletteDistribution was created with a null list of counts.");
        }
        this.counts = new ArrayList<>(counts);
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
