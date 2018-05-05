package com.gpj.commandpatterndemo.draw;

import android.graphics.Canvas;

/**
 * Created by v-pigao on 5/5/2018.
 */

// 绘制命令接口
public interface IDraw {

    /**
     * 绘制命令
     * @param canvas 画布对象
     */
    void draw(Canvas canvas);

    /**
     * 撤销命令
     */
    void undo();
}
