package com.github.bagiasn.bookspot.common.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashGenerator {
    private final static Logger LOGGER = Logger.getLogger(HashGenerator.class.getName());

    public static String GetPasswordHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        }
        catch (NoSuchAlgorithmException nsa) {
            LOGGER.log(Level.SEVERE,"No such algorithm exception raised.", nsa);
            return "";
        }
    }
}
