package com.gpj.mediaplayerdemo.mediaplayer;

import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by v-pigao on 4/4/2018.
 */

public interface IMediaDisplay extends IMediaPlayListener{
    View getDisplayView();
    SurfaceHolder getHolder();
}
