package com.liferay.twilio.whatsapp.api;

import com.liferay.portal.kernel.model.Phone;
import com.liferay.twilio.whatsapp.model.Notification;

/**
 * @author peterrichards
 */
public interface WhatsAppNotificationService {
    Notification sendNotification(final Phone sender, final Phone recipient, final String message) throws TwilioException;
    Notification sendNotification(final Phone sender, final Phone recipient, final String message, String defaultCountryCode) throws TwilioException;
    Notification sendNotification(final String senderNumber, final String recipientNumber, final String message) throws TwilioException;
    Notification sendNotification(final String senderNumber, final String recipientNumber, final String message, String defaultCountryCode) throws TwilioException;
    Notification sendNotification(final Notification notification) throws TwilioException;
    void init();
    void init(final String accountSid, final String authToken);
}