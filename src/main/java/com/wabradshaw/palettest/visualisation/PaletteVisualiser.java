package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * A PaletteVisualiser is used to visualise what an input palette looks like. In principal this means it shows your
 * pre-defined list of colors. Images are produced as grids of color-blocks.
 */
public class PaletteVisualiser {

    private int rowHeight;
    private int columnWidth;

    /**
     * Default constructor. Sets up a PaletteVisualiser with the default cell sizes. This is a row height of
     * 50 and a column width of 100.
     */
    public PaletteVisualiser() {
        this(50, 100);
    }

    /**
     * Manual constructor. Sets up a PaletteVisualiser with the supplied cell size.
     *
     * @param rowHeight   How tall each row should be.
     * @param columnWidth How wide each column should be.
     */
    public PaletteVisualiser(int rowHeight, int columnWidth) {
        this.rowHeight = rowHeight;
        this.columnWidth = columnWidth;
    }

    /**
     * Creates a visualisation of a palette, showing what each color looks like. Colors in a palette are arranged as a
     * grid, with each cell containing a color and its name.
     *
     * @param palette The palette to draw.
     * @param columns The number of columns in the grid.
     * @return        A {@link BufferedImage} containing the visualisation.
     */
    public BufferedImage visualise(List<Tone> palette, int columns) {
        int rows = (int) Math.ceil(palette.size() * 1.0 / columns);

        BufferedImage result = new BufferedImage(columns * columnWidth,
                                                 rows * rowHeight,
                                                 BufferedImage.TYPE_INT_ARGB);

        return result;
    }
}
