package com.wabradshaw.palettest.analysis.naming;

import com.wabradshaw.palettest.analysis.Tone;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

/**
 * A ColorNamer is used to produce named {@link Tone}s for arbitrary {@link java.awt.Color}s. Different implementations
 * have different strategies for how names are chosen.
 */
public interface ColorNamer {

    /**
     * Takes in a collection of {@link Color}s to name, and names each {@link Color} to produce a {@link Tone}. The
     * exact method of naming is based on the implementation being used. Typically names can be chosen based on the
     * {@link Color} itself, the other {@link Color}s in the palette, and the names in a pre-defined palette.
     *
     * @param colors      The {@link Color}s to name.
     * @param basePalette A palette of already named {@link Tone}s
     * @return            A list of the {@link Color}s as named {@link Tone}s.
     */
    public List<Tone> nameTones(Collection<Color> colors, List<Tone> basePalette);

}
