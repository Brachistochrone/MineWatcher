package com.artemlikhomanov.minewatcher.views.components;

import android.graphics.Paint;
import android.graphics.Rect;

public class Curtain {

    private int previousCurtainBottom;
    private int newCurtainBottom;
    private int actualCurtainBottom;

    private Rect curtainRect;

    private Paint curtainPaint;

    public Curtain() {
        previousCurtainBottom = 0;
        newCurtainBottom = 0;
        actualCurtainBottom = 0;

        curtainRect = new Rect();
        curtainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curtainPaint.setColor(0xFFFFFFFF);
    }

    public void setColor(int color) {
        curtainPaint.setColor(color);
    }

    public int getPreviousBottom() {
        return previousCurtainBottom;
    }

    public void setPreviousBottom(int previousCurtainBottom) {
        this.previousCurtainBottom = previousCurtainBottom;
    }

    public int getNewBottom() {
        return newCurtainBottom;
    }

    public void setNewBottom(int newCurtainBottom) {
        this.newCurtainBottom = newCurtainBottom;
    }

    public int getActualBottom() {
        return actualCurtainBottom;
    }

    public void setActualBottom(int actualCurtainBottom) {
        this.actualCurtainBottom = actualCurtainBottom;
    }

    public void saveActualBottom() {
        actualCurtainBottom = curtainRect.bottom;
    }

    public void set(int left, int top, int right, int bottom) {
        curtainRect.set(left, top, right, bottom);
    }

    public void set(Rect rect) {
        curtainRect.set(rect);
    }

    public Rect getRect () {
        return curtainRect;
    }

    public Paint getPaint() {
        return curtainPaint;
    }
}
