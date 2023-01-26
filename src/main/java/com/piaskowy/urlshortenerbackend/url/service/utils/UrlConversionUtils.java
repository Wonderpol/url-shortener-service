package com.piaskowy.urlshortenerbackend.url.service.utils;

import org.springframework.stereotype.Service;

@Service
public class UrlConversionUtils {

    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALLOWED_CHARS.length();
    private static final char[] ALLOWED_CHARACTERS = ALLOWED_CHARS.toCharArray();

    public static String encode(long input) {
        if (input == 0) {
            return String.valueOf(ALLOWED_CHARACTERS[0]);
        }

        StringBuilder encodedString = new StringBuilder();
        while (input > 0) {
            encodedString.append(ALLOWED_CHARACTERS[(int) (input % BASE)]);
            input = input / BASE;
        }

        return encodedString.reverse().toString();
    }

    public static long decode(String input) {
        long decoded = 0;
        for (int i = 0; i < input.length(); i++) {
            decoded += ALLOWED_CHARS.indexOf(input.charAt(i)) * Math.pow(BASE, input.length() - i - 1);
        }
        return decoded;
    }

}
