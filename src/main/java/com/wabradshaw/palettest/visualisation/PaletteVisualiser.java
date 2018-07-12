package com.wabradshaw.palettest.visualisation;

import com.wabradshaw.palettest.analysis.PaletteDistribution;
import com.wabradshaw.palettest.analysis.Tone;
import com.wabradshaw.palettest.analysis.distance.ColorDistanceFunction;
import com.wabradshaw.palettest.analysis.distance.EuclideanRgbaDistance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * A PaletteVisualiser is used to visualise what an input palette looks like. In principal this means it shows your
 * pre-defined list of colors. Images are produced as grids of color-blocks.
 */
public class PaletteVisualiser {

    private final int rowHeight;
    private final int columnWidth;

    private final ColorDistanceFunction distanceFunction;
    private final Tone WHITE = new Tone(Color.WHITE);
    private final Tone BLACK = new Tone(Color.BLACK);

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
        this.distanceFunction = new EuclideanRgbaDistance();
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

        AtomicInteger counter = new AtomicInteger();
        Map<Integer, List<Tone>> rows = palette.stream()
                                               .collect(groupingBy(x -> (Integer) (counter.getAndIncrement() / columns))) ;

        BufferedImage result = new BufferedImage(columns * columnWidth,
                                                 rows.size() * rowHeight,
                                                 BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = result.createGraphics();

        for(int y = 0; y < rows.size(); y ++){
            List<Tone> row = rows.get(y);
            int yPosition = rowHeight * y;

            for(int x = 0; x < row.size(); x ++){
                Tone cell = row.get(x);
                int xPosition = columnWidth * x;

                g.setPaint(cell.getColor());
                g.fillRect(xPosition, yPosition, columnWidth, rowHeight);

                if(distanceFunction.getDistance(BLACK, cell) >
                        distanceFunction.getDistance(WHITE, cell)) {
                    g.setPaint(Color.BLACK);
                } else {
                    g.setPaint(Color.WHITE);
                }
                g.drawString(cell.getName(), xPosition + 5, yPosition + 15);
            }
        }
        g.dispose();

        return result;
    }
}
