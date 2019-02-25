package com.cloudbees.rleon;

import javax.xml.bind.DatatypeConverter;

public class JaxbDependency {
    /**
     * Method that uses a class from jaxb libraries removed on Java 11
     * @param text text to parse on Base 64
     * @return
     */
    public static byte[] parse(String text) {
        return DatatypeConverter.parseBase64Binary(text);
    }
}
