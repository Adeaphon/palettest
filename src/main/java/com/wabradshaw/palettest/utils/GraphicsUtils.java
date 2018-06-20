package com.wabradshaw.palettest.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A set of helper methods to assist when dealing with {@link BufferedImage}s and {@link Graphics}.
 */
public class GraphicsUtils {

    /**
     * Creates a copy of a {@link BufferedImage} to prevent outside changes to the image and to ensure an ARGB image
     * type. BufferedImages can use different image types, including custom types, which can affect comparisons.
     *
     * @see <a href="https://stackoverflow.com/a/22391951">https://stackoverflow.com/a/22391951</a>
     *
     * @param image The image to copy. Cannot be null.
     * @return      An ARGB copy of the image
     */
    public static BufferedImage createCopy(BufferedImage image) {
        if(image == null) {
            throw new IllegalArgumentException("The buffered image supplied to createCopy was null.");
        }
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return copy;
    }
}
