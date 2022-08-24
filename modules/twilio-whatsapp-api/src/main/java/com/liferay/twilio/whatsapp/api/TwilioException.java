package com.liferay.twilio.whatsapp.api;

public class TwilioException extends RuntimeException {
    public TwilioException(String message) {
        super(message);
    }

    public TwilioException(String message, Throwable cause) {
        super(message, cause);
    }
}
