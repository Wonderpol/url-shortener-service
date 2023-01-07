package com.piaskowy.urlshortenerbackend.auth.token;

import java.util.UUID;

public class Utils {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
