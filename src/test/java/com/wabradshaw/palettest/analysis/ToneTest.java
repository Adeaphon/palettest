package com.wabradshaw.palettest.analysis;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A set of tests for the {@link Tone} class.
 */
public class ToneTest {

    /**
     * Tests that the fields supplied to the main constructor are saved.
     */
    @Test
    public void testMainConstructor(){
        Tone tone = new Tone("red", Color.RED);
        assertEquals(Color.RED, tone.getColor());
        assertEquals("red", tone.getName());
    }

    /**
     * Tests the unnamed constructor will represent red correctly.
     */
    @Test
    public void testUnnamedConstructor_Red(){
        Tone tone = new Tone(Color.RED);
        assertEquals(Color.RED, tone.getColor());
        assertEquals("#ff0000", tone.getName());
    }
    
    /**
     * Tests the unnamed constructor will represent green correctly.
     */
    @Test
    public void testUnnamedConstructor_Green(){
        Tone tone = new Tone(Color.GREEN);
        assertEquals(Color.GREEN, tone.getColor());
        assertEquals("#00ff00", tone.getName());
    }

    /**
     * Tests the unnamed constructor will represent blue correctly.
     */
    @Test
    public void testUnnamedConstructor_Blue(){
        Tone tone = new Tone(Color.BLUE);
        assertEquals(Color.BLUE, tone.getColor());
        assertEquals("#0000ff", tone.getName());
    }

    /**
     * Tests the unnamed constructor will represent a complex color correctly.
     */
    @Test
    public void testUnnamedConstructor_Composite(){
        Tone tone = new Tone(new Color(222,250,206));
        assertEquals("#deface", tone.getName());
    }


    /**
     * Tests the unnamed constructor will ignore the alpha component of the Color.
     */
    @Test
    public void testUnnamedConstructor_Alpha(){
        Tone tone = new Tone(new Color(222,250,206,50));
        assertEquals("#deface", tone.getName());
    }

}
