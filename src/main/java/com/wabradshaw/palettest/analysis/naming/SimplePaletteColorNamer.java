package com.wabradshaw.palettest.analysis.naming;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.CompuPhaseDistance;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * <p>
 * A {@link SimplePaletteColorNamer} is a type of {@link ColorNamer} that tries to assign colors to the names in an
 * existing color palette. For each {@link Color} the algorithm looks for the closest existing {@link Tone} in the
 * palette.
 * </p>
 * <p>If there aren't any {@link Tone}s in the palette close to the {@link Color}, it will be named using a hex
 * code.
 * </p>
 * <p>If there are multiple {@link Color}s close to the same {@link Tone}, then they will get numbers appended to the
 * end of their name.
 * </p>
 */
public class SimplePaletteColorNamer implements ColorNamer {

    private final ColorDistanceFunction distanceFunction;
    private final double max_name_distance;

    /**
     * Default constructor. Creates a {@link SimplePaletteColorNamer} with the default settings. Practically this means
     * that it uses {@link CompuPhaseDistance} to measure distance, and has a maximum naming distance of 50.
     */
    public SimplePaletteColorNamer(){
        this(new CompuPhaseDistance());
    }

    /**
     * Distance function constructor. Creates a {@link SimplePaletteColorNamer} which uses the supplied distance
     * function to compare {@link Color}s. This has a maximum naming distance of 50.
     *
     * @param distanceFunction The {@link ColorDistanceFunction} to use when comparing a {@link Color} to name and
     *                         a {@link Tone} in the palette.
     */
    public SimplePaletteColorNamer(ColorDistanceFunction distanceFunction){
        this.distanceFunction = distanceFunction;
        this.max_name_distance = 50;
    }

    @Override
    public List<Tone> nameTones(Collection<Color> colors, List<Tone> basePalette) {
        List<Tone> results = new ArrayList<>();

        Map<String, Integer> usedColors = new HashMap<>();

        for(Color color : colors){
            Tone protoTone = new Tone(color);
            Tone closest = getClosestTone(basePalette, protoTone);

            if(distanceFunction.getDistance(closest, protoTone) > max_name_distance){
                results.add(protoTone);
            } else {
                String suggestedName = closest.getName();
                if(usedColors.containsKey(suggestedName)){
                    int nextNumber = usedColors.get(suggestedName);
                    results.add(new Tone(suggestedName + nextNumber, color));
                    usedColors.put(suggestedName, nextNumber + 1);
                } else {
                    results.add(new Tone(suggestedName, color));
                    usedColors.put(suggestedName, 2);
                }
            }
        }

        return results;
    }

    /**
     * Finds the {@link Tone} closest to the target color.
     *
     * @param palette The list of possible Tones in the palette.
     * @return        The closest Tone in the palette.
     */
    private Tone getClosestTone(List<Tone> palette, Tone targetTone) {
       return palette.stream()
               .max((o1, o2) -> (int) Math.signum(distanceFunction.getDistance(o2, targetTone) -
                                                  distanceFunction.getDistance(o1, targetTone)))
               .get();
    }
}
