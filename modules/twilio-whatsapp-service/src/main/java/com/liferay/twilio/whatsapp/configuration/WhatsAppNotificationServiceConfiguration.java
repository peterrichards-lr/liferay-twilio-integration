package com.liferay.twilio.whatsapp.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(category = "third-party", scope = ExtendedObjectClassDefinition.Scope.GROUP)
@Meta.OCD(id = WhatsAppNotificationServiceConfiguration.PID, localization = "content/Language", name = "config-whatsapp-notifier-name", description = "config-whatsapp-notifier-description")
public interface WhatsAppNotificationServiceConfiguration {
    String PID = "com.liferay.twilio.whatsapp.configuration.WhatsAppNotificationServiceConfiguration";

    @Meta.AD(
            deflt = "",
            description = "config-account-sid-description",
            name = "config-account-sid-name",
            required = false
    )
    String accountSid();

    @Meta.AD(
            deflt = "",
            description = "config-auth-token-description",
            name = "config-auth-token-name",
            required = false
    )
    String authToken();


    @Meta.AD(
            deflt = "+1",
            description = "config-default-country-code-description",
            name = "config-default-country-code-name",
            required = false
    )
    String defaultCountryCode();
}
