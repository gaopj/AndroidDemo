package com.gpj.commandpatterndemo.brush;

import android.graphics.Path;

/**
 * Created by v-pigao on 5/5/2018.
 */

public class CircleBrush implements IBrush {
    @Override
    public void down(Path path, float x, float y) {

    }

    @Override
    public void move(Path path, float x, float y) {
        path.addCircle(x,y,10,Path.Direction.CW);
    }

    @Override
    public void up(Path path, float x, float y) {

    }
}
