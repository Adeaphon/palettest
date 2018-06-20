package com.wabradshaw.palettest.analysis;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * A {@link ToneCount} is a class used to represent the number of times a particular {@link Tone}, or a
 * {@link java.awt.Color} close to that {@link Tone}, was used. Each {@link ToneCount} tracks the target {@link Tone}
 * and the number of pixels that were roughly of that {@link Tone}.
 * </p>
 * <p>
 * To provide backwards traceability, a {@link ToneCount} also stores the count of pixels for each
 * {@link java.awt.Color} that were attributed to the {@link Tone}. These counts are used to compute the distance from
 * the {@link Tone} to the {@link java.awt.Color}s that were assigned to it.
 * </p>
 * <p>
 * Please note that ToneCounts are immutable. If the pixel count map changes after the ToneCount has been created, it
 * will not reflect those changes.
 * </p>
 */
public class ToneCount {

    private final Tone tone;
    private final int count;
    private final Map<Color, Integer> pixelCounts;

    /**
     * <p>
     * Main constructor which defines a {@link ToneCount} as a {@link Tone} and the count of pixels of each
     * {@link Color} that is part of the {@link Tone}.
     * </p>
     * <p>
     * A {@link ToneCount} is still valid if the {@link Tone} is null. This can be used to represent situations where
     * pixels do not match with any of the defined {@link Color}s. However, the pixelCounts map may not be null. 
     * </p>
     *
     * @param tone        The {@link Tone} this object is counting.
     * @param pixelCounts A map of {@link Color}s that are considered as part of this {@link Tone}, and the number
     *                    of times they each occurred. This map is copied to keep the {@link ToneCount} immutable.
     *                    Cannot be null.
     */
    public ToneCount(Tone tone, Map<Color, Integer> pixelCounts){
        if(pixelCounts == null){
            throw new IllegalArgumentException("A ToneCount was instantiated for " +
                    (tone == null ? "null" : tone.getName()) +
                    " with a null pixel counts map.");
        }

        this.tone = tone;
        this.pixelCounts = new HashMap<>(pixelCounts);
        this.count = pixelCounts.values().stream().reduce(0, (sum, x) -> x == null ? sum : sum + x);
    }

    /**
     * Gets the {@link Tone} this object is counting.
     *
     * @return The {@link Tone} this object is counting.
     */
    public Tone getTone(){
        return this.tone;
    }

    /**
     * Gets the number of pixels which have this {@link Tone}, or are close enough to be classed as it.
     *
     * @return The number of pixels which have this {@link Tone}.
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Gets a copy of the pixel counts for the {@link Tone}. This is a map of {@link Color}s that are considered as
     * part of this {@link Tone}, and the number of times they each occurred.
     *
     * @return A copy of the pixel counts for the {@link Tone}.
     */
    public Map<Color, Integer> getPixelCounts(){
        return new HashMap<>(pixelCounts);
    }

    @Override
    public String toString(){
        return this.getTone().getName() + ": " + this.count;
    }
}
