package com.cloudbees.rleon;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class Encoder {
    public static String encode(String text) {

        // Gets the java.sql.Date
        BASE64Encoder b = new BASE64Encoder();
        return b.encode(text.getBytes());
    }

    public static String decode(String text) throws IOException {

        // Gets the java.sql.Date
        BASE64Decoder b = new BASE64Decoder();
        return new String(b.decodeBuffer(text));
    }
}
