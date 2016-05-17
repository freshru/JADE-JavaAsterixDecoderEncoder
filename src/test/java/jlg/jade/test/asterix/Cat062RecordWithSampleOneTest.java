/* 
* Created by dan-geabunea on 5/17/2016.
* This code is the property of JLG Consulting. Please
* check the license terms for this product to see under what
* conditions you can use or modify this source code.
*/
package jlg.jade.test.asterix;

import jlg.jade.asterix.cat062.AsterixCat062Record;
import jlg.jade.test.utils.MandatoryFspecAsterixTests;

import static org.junit.Assert.assertEquals;

/**
 * Test the Asterix record class with sample one of data
 */
public class Cat062RecordWithSampleOneTest extends MandatoryFspecAsterixTests<AsterixCat062Record> {
    @Override
    protected AsterixCat062Record setDataFieldInstance() {
        return new AsterixCat062Record();
    }

    @Override
    protected int setExpectedMaxFspecSize() {
        return 5;
    }

    private final byte[] sampleInput = {
            (byte) 191,
            79,
            (byte) 173,
            3,
            2,
            70,
            16,
            0,
            103,
            (byte) 172,
            (byte) 233,
            0,
            (byte) 146,
            96,
            (byte) 221,
            0,
            45,
            (byte) 149,
            81,
            (byte) 239,
            91,
            63,
            (byte) 243,
            (byte) 158,
            (byte) 167,
            (byte) 253,
            121,
            2,
            108,
            11,
            12,
            5,
            (byte) 193,
            1,
            1,
            1,
            0,
            32,
            3,
            (byte) 248,
            5,
            (byte) 172,
            (byte) 133,
            (byte) 172,
            (byte) 255,
            105,
            24,
            5,
            (byte) 172,
            11,
            12,
            4,
            2,
            0,
            32
    };

    @Override
    public void the_decode_method_should_increment_offset_after_data_decoding() {
        super.the_decode_method_should_increment_offset_after_data_decoding();
    }

    @Override
    public void the_decode_method_should_correctly_decode_data() {
        super.the_decode_method_should_correctly_decode_data();
    }

    @Override
    public void the_decode_method_should_correctly_populate_fspec_field() {
        //arrange
        int offset = 0;
        AsterixCat062Record cat062Record = new AsterixCat062Record();

        //act
        cat062Record.decode(sampleInput, offset, sampleInput.length);

        //assert
        assertEquals("FSPEC actual size invalid", 5, cat062Record.getActualFspecSizeInBytes());

        assertEquals("FSPEC not decoded correctly - 010", true, cat062Record.fspecDataAtIndex(7));
        assertEquals("FSPEC not decoded correctly - 015", true, cat062Record.fspecDataAtIndex(5));
        assertEquals("FSPEC not decoded correctly - 070", true, cat062Record.fspecDataAtIndex(4));
        assertEquals("FSPEC not decoded correctly - 105", true, cat062Record.fspecDataAtIndex(3));
        assertEquals("FSPEC not decoded correctly - 100", true, cat062Record.fspecDataAtIndex(2));
        assertEquals("FSPEC not decoded correctly - 185", true, cat062Record.fspecDataAtIndex(1));
        assertEquals("FSPEC not decoded correctly - 060", true, cat062Record.fspecDataAtIndex(14));
        assertEquals("FSPEC not decoded correctly - 060", true, cat062Record.fspecDataAtIndex(14));
        assertEquals("FSPEC not decoded correctly - 040", true, cat062Record.fspecDataAtIndex(11));
        assertEquals("FSPEC not decoded correctly - 080", true, cat062Record.fspecDataAtIndex(10));
        assertEquals("FSPEC not decoded correctly - 290", true, cat062Record.fspecDataAtIndex(9));
        assertEquals("FSPEC not decoded correctly - 200", true, cat062Record.fspecDataAtIndex(23));
        assertEquals("FSPEC not decoded correctly - 136", true, cat062Record.fspecDataAtIndex(21));
        assertEquals("FSPEC not decoded correctly - 135", true, cat062Record.fspecDataAtIndex(19));
        assertEquals("FSPEC not decoded correctly - 220", true, cat062Record.fspecDataAtIndex(18));
        assertEquals("FSPEC not decoded correctly - 340", true, cat062Record.fspecDataAtIndex(25));
        assertEquals("FSPEC not decoded correctly - SP", true, cat062Record.fspecDataAtIndex(33));

        assertEquals("Nb of present data fields invalid", 16, cat062Record.getNbPresentDataFields());
    }
}
