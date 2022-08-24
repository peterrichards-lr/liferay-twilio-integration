package com.liferay.twilio.whatsapp.service;

import com.liferay.portal.kernel.model.Phone;
import com.liferay.twilio.whatsapp.api.TwilioException;
import com.liferay.twilio.whatsapp.api.WhatsAppNotificationService;
import com.liferay.twilio.whatsapp.configuration.WhatsAppNotificationServiceConfigurationWrapper;
import com.liferay.twilio.whatsapp.constants.TwilioWhatsAppConstants;
import com.liferay.twilio.whatsapp.model.Notification;
import com.liferay.twilio.whatsapp.model.NotificationCreator;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.jsoup.internal.StringUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.concurrent.Semaphore;

/**
 * @author peterrichards
 */
@Component(
	immediate = true,
	service = WhatsAppNotificationService.class
)
public class WhatsAppNotificationServiceImpl implements WhatsAppNotificationService {
	private static final Semaphore mutex = new Semaphore(1);
	private boolean initialised = false;

	@Override
	public Notification sendNotification(Phone sender, Phone recipient, String message) throws TwilioException {
		return sendNotification(sender, recipient, message, configurationWrapper.getDefaultCountryCode());
	}

	@Override
	public Notification sendNotification(Phone sender, Phone recipient, String message, String defaultCountryCode) throws TwilioException {
		final NotificationCreator creator = new NotificationCreator(defaultCountryCode);
		creator.withSender(sender).withRecipient(recipient).withMessage(message);
		return sendNotification(creator.create());
	}

	@Override
	public Notification sendNotification(String senderNumber, String recipientNumber, String message) throws TwilioException {
		return sendNotification(senderNumber, recipientNumber, message, configurationWrapper.getDefaultCountryCode());
	}

	@Override
	public Notification sendNotification(String senderNumber, String recipientNumber, String message, String defaultCountryCode) throws TwilioException {
		final NotificationCreator creator = new NotificationCreator(defaultCountryCode);
		creator.withSender(senderNumber).withRecipient(recipientNumber).withMessage(message);
		return sendNotification(creator.create());
	}

	@Override
	public Notification sendNotification(final Notification notification) throws TwilioException {
		try {
			mutex.acquire();
			if (!initialised) {
				throw new IllegalStateException("The init method must be before a notification can be sent");
			}
			mutex.release();

			final PhoneNumber sender = buildPhoneNumber(notification.getSender());
			final PhoneNumber recipient = buildPhoneNumber(notification.getRecipient());

			final Message message = Message.creator(recipient, sender, notification.getMessage()).create();

			return updateNotification(notification, message);
		} catch (InterruptedException ie) {
			throw new TwilioException("Interrupted while sending message", ie);
		}
	}

	private PhoneNumber buildPhoneNumber(String number) {
		return new PhoneNumber(String.format("%s%s", TwilioWhatsAppConstants.WHATSAPP_NUMBER_PREFIX, number));
	}

	private Notification updateNotification(final Notification notification, final Message message) {
		final NotificationCreator creator = new NotificationCreator();
		final Notification updatedNotification =
				creator
						.withRecipient(notification.getRecipient())
						.withSender(notification.getSender())
						.withMessage(notification.getMessage()).create();

		updatedNotification.setSid(message.getSid());
		updatedNotification.setStatus(message.getStatus().toString());

		updatedNotification.setErrorCode(message.getErrorCode());
		updatedNotification.setErrorMessage(message.getErrorMessage());

		updatedNotification.setDateCreated(message.getDateCreated());
		updatedNotification.setDateSent(message.getDateSent());

		return updatedNotification;
	}

	@Override
	public void init() {
		init(configurationWrapper.getAccountSid(), configurationWrapper.getAuthToken());
	}

	@Override
	public void init(String accountSid, String authToken) {
		try {
			mutex.acquire();
			if (StringUtil.isBlank(accountSid)) {
				throw new IllegalArgumentException("The accountSid must have a value");
			}
			if (StringUtil.isBlank(authToken)) {
				throw new IllegalArgumentException("The authToken must have a value");
			}
			Twilio.init(accountSid, authToken);
			initialised = true;
			mutex.release();
		} catch (InterruptedException ie) {
			throw new TwilioException("Interrupted while initialising Twilio", ie);
		}
	}

	@Reference
	private WhatsAppNotificationServiceConfigurationWrapper configurationWrapper;
}