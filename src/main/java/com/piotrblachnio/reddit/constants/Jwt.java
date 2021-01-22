package com.piotrblachnio.reddit.constants;

public class Jwt {
    public static final Integer EXPIRES_IN = 900000;
    public static final String KEY = "springblog";
    public static final String JKS = "/reddit.jks";
    public static final char[] JKS_PASSWORD = "secret".toCharArray();
}