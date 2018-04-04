package com.gpj.mediaplayerdemo.mediaplayer;

/**
 * Created by v-pigao on 4/4/2018.
 */

public class MediaPlayerException extends Exception {

    public MediaPlayerException(String detailMessage) {
        super(detailMessage);
    }

    public MediaPlayerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public MediaPlayerException(Throwable throwable) {
        super(throwable);
    }
}
