package com.funfair.api.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;


@Component
public class OtpCache {
    private static final Cache<String, String> otpCache = Caffeine.newBuilder()
            .expireAfterWrite(180, TimeUnit.SECONDS)
            .build();

    public static void putOtp(String phoneNumber, String otp) {
        otpCache.put(phoneNumber, otp);
    }

    public static String getOtp(String phoneNumber) {
        return otpCache.getIfPresent(phoneNumber);
    }

    public static void removeOtp(String phoneNumber) {
        otpCache.invalidate(phoneNumber);
    }
}