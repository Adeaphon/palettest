package com.wabradshaw.palettest.analysis;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * A {@link PaletteDistribution} is a record of the different {@link Tone}s used in an image. It stores all of the
 * {@link Tone}s that have been used alongside how many times they were used as {@link ToneCount}s. The counts can be
 * retrieved through various sorting methods such as {@link #byCount()}, by red, or by hue.
 * </p>
 * <p>
 * Please note that {@link PaletteDistribution}s are immutable. If the list of counts changes after the distribution
 * has been created, it will not reflect those changes.
 * </p>
 */
public class PaletteDistribution {

    private final List<ToneCount> counts;
    private final Map<String, ToneCount> countsByName;

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
        countsByName = new HashMap<>();
        for(ToneCount count : this.byCount()){
            String name = count.getTone().getName();
            if(!countsByName.containsKey(name)){
                countsByName.put(name, count);
            }
        };
    }

    /**
     * <p>
     * Gets the {@link ToneCount} describing how many times the {@link Tone} with the supplied name appeared in the
     * image. If no {@link Tone} with that name was used, this will return null.
     * </p>
     * <p>
     * If multiple {@link Tone}s have the same name (not recommended), then only one will be returned. Specifically,
     * this will return the {@link ToneCount} with the highest count.
     * </p>
     *
     * @param name The name of the {@link Tone} to look for.
     * @return     The {@link ToneCount} representing the number of times the named {@link Tone} was used in the
     *             image, or null if it was never used.
     */
    public ToneCount get(String name){
        return this.countsByName.get(name);
    }

    /**
     * Gets the {@link ToneCount}s representing the number of times each {@link Tone} was used within the palette.
     * These counts are returned in insertion order, so it depends on the settings of the {@link Palettester} used
     * to produce the {@link PaletteDistribution}.
     *
     * @return the {@link ToneCount}s in insertion order.
     */
    public List<ToneCount> getDistribution(){
        return new ArrayList<>(this.counts);
    }

    /**
     * Gets the distribution sorted by the number of times each {@link Tone} appeared (descending).
     *
     * @return The distribution sorted by the number of times each {@link Tone} appeared (descending).
     */
    public List<ToneCount> byCount(){
        return this.counts.stream()
                          .sorted((o1, o2) -> (o2.getCount() - o1.getCount()))
                          .collect(Collectors.toList());
    }

    /**
     * Gets the distribution sorted alphabetically by the name of each {@link Tone} (ascending).
     *
     * @return The distribution sorted alphabetically by the name of each {@link Tone} (ascending).
     */
    public List<ToneCount> byName(){
        return this.counts.stream()
                .sorted((o1, o2) -> (o1.getTone().getName().compareTo(o2.getTone().getName())))
                .collect(Collectors.toList());
    }

    @Override
    public String toString(){
        return this.counts.toString();
    }

    @Override
    public boolean equals(Object candidate){
        if(candidate instanceof PaletteDistribution){
            List<ToneCount> a = this.byCount();
            List<ToneCount> b = ((PaletteDistribution) candidate).byCount();
            return a.containsAll(b) && b.containsAll(a);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.byCount());
    }
}
