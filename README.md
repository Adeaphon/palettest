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
- [How To Use It](#quick-start-guide)
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

Palettest is provided as a Maven Build, with the following coordinates:

```xml
    <groupId>com.wabradshaw</groupId>
    <artifactId>palettest</artifactId>
    <version>1.0.0-SNAPSHOT</version>
```

As Palettest is currently in development, it has not yet been released to Maven central. To incorporate it into your
projects, download the codebase and use `mvn install` to build a local copy.

## Terminology

**Color** - Color with a capital C, the Java representation of a color. This is defined as a particular combination of
red, blue, green and transparency (alpha).

**Tone** - A `Tone` is a specific named `Color`. The idea is that you can define what "Red" means to you, then map
different exact `Color`s to your Red `Tone`. Sometimes `Tone`s are named based on the Color (e.g. "Red", "Purple",
"Lime Green"), other times you may want to name them semantically (e.g. "Background", "Font", "Alert").

**Palette** - A pre-defined list of `Tone`s. These are the `Tone`s that are, or could be, in one of your images.
`Palette`s can be chosen from the standard library of `Palette`s, created as a custom list of `Tone`s, or even
generated from an image.

**Tone Count** - A count of the number of times a particular `Tone` was used in an image. It also stores which exact
`Color`s were mapped to the `Tone`.

**Palette Distribution** - A representation of how a `Palette` was used in an image. This is stored as a collection of
`ToneCount`s, that can be retrieved in various orders (e.g. by frequency or alphabetically).

**Palettester** - The main class used to analyse the color `Palette` of an image.

## How To Use It

### ImageFileUtils: Save an image to a file

If you're generating images, the most important thing to test is that they actually look right. No matter how many
automated tests you create, remember to periodically check what you're producing.

Palettest adds a one liner that lets you save `BufferedImages` to files. Use the `ImageFileUtils` class and call
`save`. The arguments are the image itself, the location where you want to save it, and the image type.

`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = myImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        graphics.setColor(Color.RED);
        graphics.drawString("Hello, World!", 15, 28);

        ImageFileUtils.save(myImage, "src/test/resources/resultImages/examples/saveExample.png", "png");
```

In the above example we set up a simple "Hello, World!" image. Running that will create the following file:

![The image file this generates](src/test/resources/resultImages/examples/saveExample.png?raw=true)

### ImageFileUtils: Load an image from a file

If you want to test a generated image, it can be useful to load an existing image to make comparisons. Like saving,
`ImageFileUtils` also adds a convenient one liner to load image resources. Call 'loadImageResource' with the desired
image resource to create a `BufferedImage` from disk.

`import com.wabradshaw.palettest.utils.ImageFileUtils;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/helloWorld.png");
```

This example loads a copy of our hello world image so that it can be tested (or compared with a programmatically
generated version).

### AssertDimensions: Asserting the image is the right size

One of the first automated tests for a generated image is to make sure that it has the correct dimensions. Palettest
provides a number of ready to use assertions for testing, including `assertDimensions`. This method takes the image
under test and it's desired width & height (in that order).

`import static com.wabradshaw.palettest.assertions.AssertDimensions.assertDimensions;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");
        assertDimensions(exampleImage, 100, 50);
```

This example checks that our hello world image is 100 pixels wide, and 50 pixels tall.

### AssertPixelsMatch: Asserting an image matches exactly

If your image generator is deterministic, you are able to check that the generated image exactly matches a saved image.
To do this you can use the `assertPixelsMatch` method, which takes the target image and the actual image, and asserts
that they both use the same pixels.

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

This example checks that the hello world image generated using Java graphics is the same as the one in the sample
images folder.

### Palettester: Getting the exact colors in an image

It isn't always convenient to produce the target image for each test. In particular, it makes TDD almost impossible.
Instead, it's often easier to analyse what the generated image should be like, rather than saying exactly what it should
be.

The main analysis class for Palettest is the `Palettester` class. This class is used to analyse the colors and `Tone`s
that make up your images. The basic test method is `analyseAllColors` which returns a `PaletteDistribution` that lists
every single `Color` used in an image, and the number of times it appeared.

`import com.wabradshaw.palettest.analysis.Palettester;`
`import com.wabradshaw.palettest.analysis.PaletteDistribution;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);
        System.out.println(distribution);
```

Result:
`[#ffffff: 4829, #ff0000: 171]`

This example produces a `PaletteDistribution` containing a list of all of the colors in the image, and the number of
times they appeared. Specifically, white (#ffffff) appeared nearly 4829 times, and red (#ff0000) appeared 171 times.

### PaletteDistribution: Checking what's in the image

A `PaletteDistribution` gives you all of the details on the `Color`s and `Tone`s used in an image. The number of times
a particular `Tone` was used is known as a `ToneCount`. The distribution can is a list of `ToneCount`s, which can be
retrieved three ways: `getDistribution`, `byCount`, and `byName`.

If you do not need a particular order, use `getDistribution`. This returns the underlying distribution list, which is
typically sorted by the order that different `Tone`s were encountered in the image.

To get the list sorted based on how many times each `Tone` was used, use `byCount`. The first `ToneCount` will be the
one that appeared most often, and the number of appearances will descrease beyond that.

To get the list sorted by name, use `byName`. This returns the `ToneCount`s in alphabetical order.

You can also use the `get` methods to return the `ToneCount` for a particular `Color`, or the name of particular `Tone`.
If you call get on a `Tone` that wasn't used in the image, it will return null.

`import com.wabradshaw.palettest.analysis.PaletteDistribution;`

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

Result:
```
Original Distribution: [#ffffff: 4829, #ff0000: 171]
By Count: [#ffffff: 4829, #ff0000: 171]
By Name: [#ff0000: 171, #ffffff: 4829]
Red by name: #ff0000: 171
White by color: #ffffff: 4829
Blue by color: null
```

This example shows the different ways of accessing a `PaletteDistribution`.

### Assertions: Asserting what's in the image

 Once you know how to access the data within a `PaletteDistribution` you can start writing assertions to test your
 images.

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/helloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analyseAllColors(exampleImage);

        assertEquals(Color.WHITE, distribution.byCount().get(0).getTone().getColor());
        assertTrue(distribution.get(Color.WHITE).getCount() > 2500);
        assertTrue(distribution.get(Color.RED) != null);
```

This example uses standard JUnit assertions to check that the most common color is white, white makes up more than half
of the image, and the image contains red. However, Palettest offers several shortcut assertions to make that easier.

Palettest provides three `PaletteDistribution` assertions: `assertMainColor`, `assertMostly` and `assertContainsColor`.
All three of these take in a desired `Tone`, either as it's name or its `Color`, as well as the image's
`PaletteDistribution`.

The `assertMainColor` method asserts that the single largest `Tone` in the image is the one specified. This is the same
as asserting that it is the first `Tone` returned from `byCount`.

The `assertMostly` method is a more extreme version of `assertMainColor`. It asserts that the specified `Tone` makes up
more than 50% of the image.

The `assertContainsColor` method is a quick way to check that an image contains a certain `Tone`. It will be true if
there's at least one pixel of the specified `Tone`.

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

The above example shows how you can use the Palettest assertions to test the image. They check that the most common
color is white, white makes up more than half of the image, and the image contains red.

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

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, exampleImage);
        System.out.println(distribution);
```

```
[Pink: 238, White: 4553, Red: 209]
```

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        PaletteDistribution javaDistribution = tester.analysePalette(StandardPalettes.JAVA_COLORS, exampleImage);
        PaletteDistribution rainbowDistribution = tester.analysePalette(StandardPalettes.RAINBOW, exampleImage);
        PaletteDistribution rainbowBWDistribution = tester.analysePalette(StandardPalettes.RAINBOW_BW, exampleImage);
        PaletteDistribution pwgDistribution = tester.analysePalette(StandardPalettes.PWG_STANDARD, exampleImage);
        PaletteDistribution x11Distribution = tester.analysePalette(StandardPalettes.X11_NUMBERED, exampleImage);
        System.out.println("JAVA: " + javaDistribution);
        System.out.println("RAINBOW: " + rainbowDistribution);
        System.out.println("RAINBOW_BW: " + rainbowBWDistribution);
        System.out.println("PWG_STANDARD: " + pwgDistribution);
        System.out.println("X11_NUMBERED: " + x11Distribution);
```

```
JAVA: [Pink: 238, White: 4553, Red: 209]

RAINBOW: [Red: 183, Orange: 201, Yellow: 4616]

RAINBOW_BW: [White: 4673, Red: 183, Orange: 144]

PWG_STANDARD: [Light Silver: 36, White: 4459, Pink: 69, Dark Pink: 75, Light Red: 97, Light Orange: 31, Light Pink: 74,
Red: 107, Light Yellow: 6, Ivory: 46]

X11_NUMBERED: [White: 4449, Firebrick 2: 7, Light Pink 2: 20, Light Salmon 1: 8, Snow 1: 17, Light Pink 1: 37,
Salmon: 34, Lavender Blush 1: 22, Firebrick 1: 62, Light Coral: 28, Seashell 1: 11, Pink: 37, Indian Red 1: 31,
Tomato 1: 15, Misty Rose 2: 10, Linen: 6, Light Pink: 6, Rosy Brown 1: 21, Misty Rose 1: 48, Red 1: 103, Brown 1: 28]
```

### Palettes: Defining a custom palette

`import com.wabradshaw.palettest.analysis.Tone;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        List<Tone> palette = new ArrayList<>();
        palette.add(new Tone("Background", Color.WHITE));
        palette.add(new Tone("Text", new Color(255,200,200)));

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(palette, exampleImage);
        System.out.println(distribution);
```

```
[Background: 4520, Text: 480]
```

### Palettester: Defining a palette from an image

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        Palettester tester = new Palettester();
        List<Tone> palette = tester.definePalette(exampleImage, 2);
        System.out.println(palette);
```

`[Light Red (#ff3c3c), White (#fffcfc)]`

### PaletteVisualiser: Visualising a palette

`import com.wabradshaw.palettest.visualisation.PaletteVisualiser;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/simple/realHelloWorld.png");

        PaletteVisualiser paletteVisualiser = new PaletteVisualiser();
        BufferedImage paletteImage = paletteVisualiser.visualise(StandardPalettes.JAVA_COLORS, 4);

        ImageFileUtils.save(paletteImage, "src/test/resources/resultImages/examples/examplePalette.png", "png");
```

### PaletteReplacer: Visualising how an image maps to different Tones

`import com.wabradshaw.palettest.visualisation.PaletteReplacer;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/complex/smallSheep.jpg");

PaletteReplacer replacer = new PaletteReplacer();
        BufferedImage replacedImage = replacer.replace(exampleImage, StandardPalettes.PWG_STANDARD);

        ImageFileUtils.save(replacedImage, "src/test/resources/resultImages/examples/replacedImage.png", "png");
```

### DistributionPainter: Visualising a distribution

`import com.wabradshaw.palettest.visualisation.DistributionPainter;`

```java
        BufferedImage exampleImage = ImageFileUtils.loadImageResource("/sampleImages/complex/smallSheep.jpg");

        Palettester tester = new Palettester();
        PaletteDistribution distribution = tester.analysePalette(exampleImage);

        DistributionPainter painter = new DistributionPainter();

        BufferedImage replacedImage = painter.paintTones(distribution.byCount(),
                                                         exampleImage.getWidth(),
                                                         exampleImage.getHeight());

        ImageFileUtils.save(replacedImage, "src/test/resources/resultImages/examples/distribution.png", "png");
```

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>
