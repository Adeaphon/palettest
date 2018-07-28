package com.wabradshaw.palettest.analysis.naming;

import com.wabradshaw.palettest.analysis.Tone;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * A {@link HexcodeColorNamer} is a type of {@link ColorNamer} that names every {@link Color} using its hexcode. For
 * example, red would be named #ff0000.
 * </p>
 */
public class HexcodeColorNamer implements ColorNamer {

    @Override
    public List<Tone> nameTones(Collection<Color> colors, List<Tone> basePalette) {
        return colors.stream().map(x -> new Tone(x)).collect(Collectors.toList());
    }
}
