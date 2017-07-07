/*
 * Copyright (C) 2016 The AndroidSupport Project
 */

package com.hyena.framework.app.drawable;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by yangzc on 16/12/16.
 */
public class RectangleDrawable extends ShapeDrawable {

    public RectangleDrawable(Shape s, int color, int alpha, float strokeWidth, Paint.Style style) {
        super(s);
        Paint paint = getPaint();
        if (paint != null) {
            paint.setStyle(style);
            paint.setColor(color);
            paint.setAlpha(alpha);
            paint.setStrokeWidth(strokeWidth);
        }
    }
}
