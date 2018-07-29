# Palettest

## What is Palettest

A while ago I was working on a project that generated images, but when it came to writing the unit tests, I was stumped.
It seemed like I was going to have to write more code to write a single test, than I would need to write to generate
the images in the first place. That's where Palettest comes in.

Palettest is a Java library designed to help people test images. It contains a whole suite of functions designed to
make every part of testing images easier. Some of these functions are designed to save you writing a few lines of code,
but the bit that is unique is the ability to easily test the color palette being used in the image.

## Contents

- [What is Palettest](#what-is-palettest)
- [Contents](#contents)
- [Installing Palettest](#installing-palettest)
- [Terminology](#terminology)
- [Quick Start Guide](#quick-start-guide)
  * [ImageFileUtils: Save an image to a file](#imagefileutils--save-an-image-to-a-file)
  * [ImageFileUtils: Load an image from a file](#imagefileutils--load-an-image-from-a-file)
  * [AssertDimensions: Asserting the image is the right size](#assertdimensions--asserting-the-image-is-the-right-size)
  * [AssertPixelsMatch: Asserting an image matches exactly](#assertpixelsmatch--asserting-an-image-matches-exactly)
  * [Palettester: Getting the exact colors in an image](#palettester--getting-the-exact-colors-in-an-image)
  * [PaletteDistribution: Checking what's in the image](#palettedistribution--checking-what-s-in-the-image)
  * [Assertions: Asserting what's in the image](#assertions--asserting-what-s-in-the-image)
  * [Palettester: Getting similar colors to an image](#palettester--getting-similar-colors-to-an-image)
  * [StandardPalettes: Choosing a different palette](#standardpalettes--choosing-a-different-palette)
  * [Palettes: Defining a custom palette](#palettes--defining-a-custom-palette)
  * [Palettester: Defining a palette from an image](#palettester--defining-a-palette-from-an-image)
  * [PaletteVisualiser: Visualising a palette](#palettevisualiser--visualising-a-palette)
  * [PaletteReplacer: Visualising how an image maps to different Tones](#palettereplacer--visualising-how-an-image-maps-to-different-tones)
  * [DistributionPainter: Visualising a distribution](#distributionpainter--visualising-a-distribution)

## Installing Palettest

## Terminology

## Quick Start Guide

### ImageFileUtils: Save an image to a file

`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        ImageFileUtils.save(myImage, "src/test/resources/resultImages/examples/SaveExample.png", "png");
```

### ImageFileUtils: Load an image from a file

`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/helloWorld.png");
```

### AssertDimensions: Asserting the image is the right size


`import com.wabradshaw.palettest.utils.ImageFileUtils;`
`import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        assertDimensions(exampleImage, 100, 50);
```

### AssertPixelsMatch: Asserting an image matches exactly

`import com.wabradshaw.palettest.utils.ImageFileUtils;`
`import static com.wabradshaw.palettest.assertions.AssertPixelsMatch.assertPixelsMatch;`

```java
        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        assertPixelsMatch(exampleImage, myImage);
```

### Palettester: Getting the exact colors in an image

`import com.wabradshaw.palettest.analysis.Palettester;`
`import com.wabradshaw.palettest.analysis.PaletteDistribution;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println(distribution);
```

`[#ffffff: 4829, #ff0000: 171]`

### PaletteDistribution: Checking what's in the image

`import com.wabradshaw.palettest.analysis.Palettester;`
`import com.wabradshaw.palettest.analysis.PaletteDistribution;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println("Original Distribution: " + distribution.getDistribution());
        System.out.println("By Count: " + distribution.byCount());
        System.out.println("By Name: " + distribution.byName());

        System.out.println("Red by name: " + distribution.get("#ff0000"));
        System.out.println("White by color: " + distribution.get(Color.WHITE));
        System.out.println("Blue by color: " + distribution.get(Color.BLUE));
```

```
Original Distribution: [#ffffff: 4829, #ff0000: 171]
By Count: [#ffffff: 4829, #ff0000: 171]
By Name: [#ff0000: 171, #ffffff: 4829]
Red by name: #ff0000: 171
White by color: #ffffff: 4829
Blue by color: null
```

### Assertions: Asserting what's in the image


```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);

        assertEquals(Color.WHITE, distribution.byCount().get(0).getTone().getColor());
        assertTrue(distribution.get(Color.WHITE).getCount() > 2500);
        assertTrue(distribution.get(Color.RED) != null);
```

`import com.wabradshaw.palettest.analysis.Palettester;`
`import com.wabradshaw.palettest.analysis.PaletteDistribution;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`
`import static com.wabradshaw.palettest.assertions.AssertMostly.assertMostly;`
`import static com.wabradshaw.palettest.assertions.AssertMainColor.assertMainColor;`
`import static com.wabradshaw.palettest.assertions.AssertContainsColor.assertContainsColor;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);

        assertMainColor(Color.WHITE, distribution);
        assertMostly(Color.WHITE, distribution);
        assertContainsColor(Color.RED, distribution);
```

### Palettester: Getting similar colors to an image

`import com.wabradshaw.palettest.analysis.Palettester;`
`import com.wabradshaw.palettest.analysis.PaletteDistribution;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println(distribution.getDistribution());
```

```
[#ffffff: 4440, #fffefe: 5, #fffdfd: 4, #fffcfc: 4, #fffbfb: 3, #fffafa: 1, #fff9f9: 2, #fff7f7: 6, #fff6f6: 1,
#fff5f5: 11, #fff4f4: 3, #fff3f3: 1, #fff2f2: 1, #fff1f1: 3, #fff0f0: 2, #ffefef: 2, #ffeeee: 2, #ffeded: 8,
#ffecec: 1, #ffebeb: 4, #ffeaea: 1, #ffe7e7: 4, #ffe6e6: 2, #ffe5e5: 7, #ffe4e4: 2, #ffe3e3: 1, #ffe2e2: 1, #ffe1e1: 2,
#ffe0e0: 1, #ffdfdf: 2, #ffdddd: 9, #ffdcdc: 1, #ffdbdb: 7, #ffdada: 3, #ffd9d9: 1, #ffd8d8: 2, #ffd7d7: 3, #ffd5d5: 1,
#ffd4d4: 4, #ffd3d3: 1, #ffd2d2: 1, #ffd1d1: 1, #ffd0d0: 2, #ffcfcf: 1, #ffcdcd: 4, #ffcccc: 2, #ffcbcb: 1,
#ffcaca: 18, #ffc9c9: 11, #ffc7c7: 1, #ffc6c6: 2, #ffc5c5: 1, #ffc4c4: 2, #ffc3c3: 2, #ffc2c2: 2, #ffc1c1: 2,
#ffc0c0: 1, #ffbfbf: 3, #ffbdbd: 3, #ffbcbc: 2, #ffbaba: 2, #ffb9b9: 1, #ffb7b7: 3, #ffb4b4: 1, #ffb2b2: 2,
#ffb1b1: 11, #ffb0b0: 1, #ffafaf: 7, #ffaeae: 1, #ffaaaa: 10, #ffa9a9: 1, #ffa8a8: 2, #ffa6a6: 1, #ffa5a5: 2,
#ffa4a4: 2, #ffa3a3: 1, #ffa2a2: 2, #ffa1a1: 2, #ff9e9e: 4, #ff9d9d: 1, #ff9c9c: 1, #ff9a9a: 1, #ff9999: 4, #ff9797: 1,
#ff9595: 1, #ff9494: 2, #ff9393: 1, #ff9292: 1, #ff9191: 2, #ff8f8f: 1, #ff8e8e: 1, #ff8c8c: 2, #ff8a8a: 1, #ff8989: 1,
#ff8888: 5, #ff8787: 2, #ff8686: 10, #ff8585: 2, #ff8484: 3, #ff8383: 1, #ff8080: 1, #ff7f7f: 13, #ff7e7e: 2,
#ff7d7d: 1, #ff7b7b: 1, #ff7a7a: 3, #ff7676: 10, #ff7575: 2, #ff6a6a: 1, #ff6868: 9, #ff6767: 1, #ff6666: 8,
#ff6565: 1, #ff6464: 4, #ff6363: 1, #ff6262: 1, #ff6161: 1, #ff5f5f: 1, #ff5e5e: 1, #ff5d5d: 2, #ff5c5c: 1, #ff5a5a: 2,
#ff5959: 2, #ff5858: 1, #ff5757: 1, #ff5555: 2, #ff5454: 2, #ff5252: 2, #ff5151: 2, #ff4f4f: 3, #ff4d4d: 1, #ff4c4c: 1,
#ff4b4b: 1, #ff4848: 2, #ff4747: 2, #ff4646: 2, #ff4545: 1, #ff4444: 3, #ff4040: 1, #ff3f3f: 7, #ff3e3e: 1, #ff3c3c: 3,
#ff3737: 2, #ff3636: 1, #ff3535: 1, #ff3434: 2, #ff3333: 2, #ff3232: 1, #ff3131: 13, #ff2e2e: 1, #ff2d2d: 1,
#ff2c2c: 2, #ff2b2b: 3, #ff2a2a: 1, #ff2727: 15, #ff2626: 1, #ff2525: 2, #ff2424: 3, #ff2222: 3, #ff2121: 2,
#ff2020: 2, #ff1f1f: 1, #ff1e1e: 2, #ff1d1d: 1, #ff1b1b: 2, #ff1a1a: 1, #ff1919: 1, #ff1818: 3, #ff1717: 1, #ff1414: 2,
#ff1212: 3, #ff1111: 1, #ff1010: 1, #ff0e0e: 11, #ff0c0c: 3, #ff0b0b: 1, #ff0a0a: 4, #ff0909: 1, #ff0808: 4,
#ff0606: 3, #ff0505: 1, #ff0404: 1, #ff0303: 1, #ff0202: 2, #ff0101: 3, #ff0000: 60]
```

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(exampleImage);
        System.out.println(distribution);
```

```
[Light Silver: 36, White: 4459, Pink: 69, Dark Pink: 75, Light Red: 97, Light Orange: 31, Light Pink: 74, Red: 107,
Light Yellow: 6, Ivory: 46]
```

### StandardPalettes: Choosing a different palette

### Palettes: Defining a custom palette

### Palettester: Defining a palette from an image

### PaletteVisualiser: Visualising a palette

### PaletteReplacer: Visualising how an image maps to different Tones

### DistributionPainter: Visualising a distribution

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>
