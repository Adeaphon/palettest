package com.wabradshaw.palettest.analysis;

import java.awt.*;
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
 */
public class ToneCount {

    private final Tone tone;
    //private final int count;
    private final Map<Color, Integer> pixelCounts;

    /**
     * Main constructor which defines a {@link ToneCount} as a {@link Tone} and the count of pixels of each
     * {@link Color} that is part of the {@link Tone}.
     *
     * @param tone        The {@link Tone} this object is counting.
     * @param pixelCounts A map of {@link Color}s that are considered as part of this {@link Tone}, and the number
     *                    of times they each occurred.
     */
    public ToneCount(Tone tone, Map<Color, Integer> pixelCounts){
        this.tone = tone;
        this.pixelCounts = pixelCounts;
    }
}
