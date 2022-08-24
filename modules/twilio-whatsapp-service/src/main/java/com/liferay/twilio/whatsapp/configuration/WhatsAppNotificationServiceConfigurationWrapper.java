package com.liferay.twilio.whatsapp.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collectors;

@Component(
        configurationPid = WhatsAppNotificationServiceConfiguration.PID,
        immediate = true, service = WhatsAppNotificationServiceConfigurationWrapper.class
)
public class WhatsAppNotificationServiceConfigurationWrapper {
    protected final Logger _log = LoggerFactory.getLogger(WhatsAppNotificationServiceConfigurationWrapper.class);
    private volatile WhatsAppNotificationServiceConfiguration configuration;

    public String getAccountSid() {
        return configuration.accountSid();
    }

    public String getAuthToken() {
        return configuration.authToken();
    }

    public String getDefaultCountryCode() {
        return configuration.defaultCountryCode();
    }

    @Activate
    @Modified
    protected void activate(final Map<String, Object> properties) {
        _log.trace("Activating {} : {}", getClass().getSimpleName(), properties.keySet().stream().map(key -> key + "=" + properties.get(key).toString()).collect(Collectors.joining(", ", "{", "}")));
        configuration = ConfigurableUtil.createConfigurable(
                WhatsAppNotificationServiceConfiguration.class, properties);
    }
}
