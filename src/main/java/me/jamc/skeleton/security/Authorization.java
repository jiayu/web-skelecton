package me.jamc.skeleton.security;

public interface Authorization {

    public boolean verify(String uri, String method, String params, long timestamp, String secretKey,
                          String signature);

    public String sign(String uri, String method, String params, String secretKey,
                       long timestamp);
}
