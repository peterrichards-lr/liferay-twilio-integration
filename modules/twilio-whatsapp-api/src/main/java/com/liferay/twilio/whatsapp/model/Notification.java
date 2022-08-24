package com.liferay.twilio.whatsapp.model;

import java.time.ZonedDateTime;

public class Notification {
    private final String sender;
    private final String recipient;
    private final String message;

    private String sid;
    private String status;
    private ZonedDateTime dateSent;
    private ZonedDateTime dateCreated;
    private Integer errorCode;
    private String errorMessage;

    Notification(final String sender, final String recipient, final String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSender() {
        return sender;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(ZonedDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
