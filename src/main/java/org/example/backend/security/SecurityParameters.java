package org.example.backend.security;

public class SecurityParameters {
    public static final long EXPIRATION_TIME = 3 * 24 * 60 * 60 * 1000;
    public static final String SECRET = "c2VjcmV0X2tleV9mb3Jfand0"; // base64-encoded string
    public static final String PREFIX = "Bearer";
}
