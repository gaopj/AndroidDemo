package com.gpj.rxjavademo.bus;

/**
 * Created by v-pigao on 4/14/2018.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
