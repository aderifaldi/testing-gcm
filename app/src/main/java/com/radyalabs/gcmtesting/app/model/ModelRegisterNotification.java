package com.radyalabs.gcmtesting.app.model;

import java.io.Serializable;

/**
 * Created by aderifaldi on 28/06/2016.
 */
public class ModelRegisterNotification implements Serializable {

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
