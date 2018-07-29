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


`import com.wabradshaw.palettest.assertions.AssertDimensions;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        AssertDimensions.assertDimensions(exampleImage, 100, 50);
```

### AssertPixelsMatch: Asserting an image matches exactly

`import com.wabradshaw.palettest.assertions.AssertPixelsMatch;`
`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        AssertPixelsMatch.assertPixelsMatch(exampleImage, myImage);
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

### Palettester: Getting similar colors to an image

### StandardPalettes: Choosing a different palette

### Palettes: Defining a custom palette

### Palettester: Defining a palette from an image

### PaletteVisualiser: Visualising a palette

### PaletteReplacer: Visualising how an image maps to different Tones

### DistributionPainter: Visualising a distribution

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>
