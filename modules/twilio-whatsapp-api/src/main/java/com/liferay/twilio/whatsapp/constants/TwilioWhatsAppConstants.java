package com.liferay.twilio.whatsapp.constants;

import java.util.regex.Pattern;

public class TwilioWhatsAppConstants {
    public final static String ZERO = "0";
    public final static String PHONE_NUMBER_REGEX_STRING = "^([+\\d].*)?\\d$";
    public final static Pattern PHONE_NUMBER_REGEX_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX_STRING);
}
