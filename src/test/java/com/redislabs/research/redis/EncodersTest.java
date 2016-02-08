package com.redislabs.research.redis;

import com.redislabs.research.text.NaiveNormalizer;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import junit.framework.TestCase;

import java.util.List;

/**
 * Created by dvirsky on 07/02/16.
 */
public class EncodersTest extends TestCase {

    public void testNumericEncoder() {


        Encoders.Numeric enc = new Encoders.Numeric();
        byte []bs = enc.encode((3.4456)).get(0);

        assertEquals("400B9096BB98C7E3",HexBin.encode(enc.encode((3.4456)).get(0)));
        assertEquals("400B9096C0000000",HexBin.encode(enc.encode((float)3.4456).get(0)));
        assertTrue(HexBin.encode(enc.encode(3.4456).get(0)).compareTo(HexBin.encode(enc.encode((2.4456)).get(0))) > 0);

        assertEquals("0000000000000005",HexBin.encode(enc.encode(5).get(0)));


    }


    public void testStringNormalizer() {


        Encoders.Prefix enc = new Encoders.Prefix(new NaiveNormalizer(), true);

        NaiveNormalizer nrml = new NaiveNormalizer();
        String s =  "Hello ...  El Niño";
        System.out.println(nrml.normalize(s));
        List<byte[]> out = enc.encode(s);

        assertEquals(3, out.size());
        assertEquals(nrml.normalize(s), new String(out.get(0)));
        assertEquals("el nino", new String(out.get(1)));
        assertEquals("nino", new String(out.get(2)));


    }


}