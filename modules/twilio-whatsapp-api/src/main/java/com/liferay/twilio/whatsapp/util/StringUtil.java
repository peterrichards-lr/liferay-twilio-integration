package com.liferay.twilio.whatsapp.util;

public final class StringUtil {
    public static boolean isBlank(final String string) {
        return string == null || string.trim().isEmpty();
    }
}
