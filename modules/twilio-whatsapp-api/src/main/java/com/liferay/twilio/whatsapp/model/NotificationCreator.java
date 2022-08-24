package com.liferay.twilio.whatsapp.model;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.twilio.whatsapp.util.StringUtil;

import static com.liferay.twilio.whatsapp.constants.TwilioWhatsAppConstants.PHONE_NUMBER_REGEX_PATTERN;
import static com.liferay.twilio.whatsapp.constants.TwilioWhatsAppConstants.ZERO;

public class NotificationCreator {
    private String defaultCountryCode;
    private String sender;
    private String recipient;
    private String message;

    public NotificationCreator() {
        defaultCountryCode = null;
    }

    public NotificationCreator(String defaultCountryCode) {
        this.defaultCountryCode = normaliseCountryCode(defaultCountryCode);
    }

    public NotificationCreator withDefaultCountryCode(String defaultCountryCode) {
        this.defaultCountryCode = normaliseCountryCode(defaultCountryCode);
        return this;
    }

    private String normaliseCountryCode(String countryCode) {
        if (StringUtil.isBlank(countryCode)) {
            return null;
        }
        if (!PHONE_NUMBER_REGEX_PATTERN.matcher(countryCode).matches()) {
            throw new IllegalArgumentException("The country code must be a valid number with or without a plus prefix");
        }
        return countryCode.startsWith(StringPool.PLUS) ? countryCode : StringPool.PLUS + countryCode;
    }

    public NotificationCreator withSender(String phoneNumber) {
        sender = normalisePhoneNumber("sender", phoneNumber);
        return this;
    }

    public NotificationCreator withSender(Phone phoneNumber) {
        sender = normalisePhoneNumber("sender", phoneNumber.getNumber());
        return this;
    }

    public NotificationCreator withRecipient(String phoneNumber) {
        recipient = normalisePhoneNumber("recipient", phoneNumber);
        return this;
    }

    public NotificationCreator withRecipient(Phone phoneNumber) {
        recipient = normalisePhoneNumber("recipient", phoneNumber.getNumber());
        return this;
    }

    public NotificationCreator withMessage(String message) {
        this.message = message;
        return this;
    }

    private String normalisePhoneNumber(String type, String phoneNumber) {
        if (StringUtil.isBlank(phoneNumber)) {
            throw new IllegalArgumentException(String.format("The %s's phone number must have a value. It can be a local or international format", type));
        }
        if (!PHONE_NUMBER_REGEX_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException(String.format("The %s's phone number must be a valid a local or international format number", type));
        }
        if (phoneNumber.startsWith(ZERO) && defaultCountryCode == null) {
            throw new IllegalArgumentException(String.format("The %s's phone number is in local format but the default country code has not been set", type));
        }
        final String internationalNumber = phoneNumber.startsWith(ZERO) ? phoneNumber.replaceFirst(ZERO, defaultCountryCode) : phoneNumber;
       return internationalNumber.startsWith(StringPool.PLUS) ? internationalNumber : StringPool.PLUS + internationalNumber;
    }

    public Notification create() {
        return new Notification(sender, recipient, message);
    }
}
