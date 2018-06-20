package com.artemlikhomanov.minewatcher.views.components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Bar {

    private Drawable mBarColor;
    private Rect mBarRect;

    public Bar() {
        mBarRect = new Rect();
    }

    public void setColor(Drawable color) {
        mBarColor = color;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        mBarRect.set(left, top, right, bottom);
        mBarColor.setBounds(mBarRect);
    }

    public Rect getRect() {
        return mBarRect;
    }

    public int getLeft() {
        return mBarRect.left;
    }

    public int getTop() {
        return mBarRect.top;
    }

    public int getRight() {
        return mBarRect.right;
    }

    public int getBottom() {
        return mBarRect.bottom;
    }

    public void draw(Canvas canvas) {
        mBarColor.draw(canvas);
    }
}
