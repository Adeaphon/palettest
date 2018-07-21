package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.palettes.StandardPalettes;
import com.wabradshaw.palettest.utils.ImageFileUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;
import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;
import static com.wabradshaw.palettest.utils.ImageFileUtils.save;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A set of tests for the {@link PaletteVisualiser} class.
 */
public class PaletteReplacerTest {

    /**
     * Tests that the image returned is the same size as the input image.
     */
    @Test
    public void testSameSize(){
        List<Tone> palette = Arrays.asList(new Tone(Color.red));

        BufferedImage image = ImageFileUtils.loadImageResource("/sampleImages/dimensions/2x4.png");

        PaletteReplacer replacer = new PaletteReplacer();

        BufferedImage result = replacer.replace(image, palette);

        assertDimensions(result, 2, 4);
    }
}
