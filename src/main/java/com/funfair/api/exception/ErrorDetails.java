package com.funfair.api.exception;

import java.util.Date;

public class ErrorDetails {
    private Date date;
    private String message;

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(Date timestamp) {
        this.date = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDetails(Date timestamp, String message, String details) {
        this.date = timestamp;
        this.message = message;
    }

    public ErrorDetails() {
        // TODO Auto-generated constructor stub
    }
}
