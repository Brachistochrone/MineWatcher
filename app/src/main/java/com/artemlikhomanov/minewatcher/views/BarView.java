package com.artemlikhomanov.minewatcher.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;

import com.artemlikhomanov.minewatcher.views.components.Bar;
import com.artemlikhomanov.minewatcher.views.components.Curtain;

public abstract class BarView extends View{

    String mHeaderText;

    int mHeaderTextColor;
    int mHeaderTextSize;
    int mBackgroundColor;
    int mAnimationDuration;
    int mInternalViewHeight;
    int mInternalViewWidth;

    float mHeaderTextX;
    float mHeaderTextY;

    float mDesiredHeight;
    float mDesiredWidth;

    boolean mOnMeasure;
    boolean mWithBackground;

    Rect mBackgroundRect;
    Rect mTextBoundsRect;
    Rect mHeaderTextArea;

    Paint mBackgroundPaint;
    Paint mHeaderTextPaint;

    AnimatorSet mAnimatorSet;

    Curtain[] mCurtains;

    Bar[] mBars;

    public BarView(Context context) {
        super(context);
        init(context, null);
    }

    public BarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    abstract void init(Context context, @Nullable AttributeSet attrs);

    abstract void initCurtains();

    abstract void initBars();

    abstract void measureBars();

    abstract void measureHeaderText();

    abstract void measureChildren();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        int viewWidth;
        if (specMode == MeasureSpec.EXACTLY) {
            viewWidth = specSize;
        } else {
            viewWidth = (int) (getPaddingStart() + getPaddingEnd() + convertDpToPixels(mDesiredWidth));
            if (specMode == MeasureSpec.AT_MOST) {
                viewWidth = Math.min(viewWidth, specSize);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        int viewHeight;
        if (specMode == MeasureSpec.EXACTLY) {
            viewHeight = specSize;
        } else {
            viewHeight = (int) (getPaddingTop() + getPaddingBottom() + convertDpToPixels(mDesiredHeight));
            if (specMode == MeasureSpec.AT_MOST) {
                viewHeight = Math.min(viewHeight, specSize);
            }
        }

        if (viewWidth < 0) {
            viewWidth = 0;
        }

        if (viewHeight < 0) {
            viewHeight = 0;
        }
        //границы внутренней области для рисования
        mInternalViewWidth = viewWidth - getPaddingStart() - getPaddingEnd();
        mInternalViewHeight = viewHeight - getPaddingTop() - getPaddingBottom();

        if (mWithBackground) {
            setBackgroundBounds(0, 0, viewWidth, viewHeight);
        }

        measureChildren();

        mOnMeasure = true;

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutlineProvider(w, h));
        }
    }

    void measureCurtains() {
        for (int i = 0; i < mCurtains.length; i++) {
            if (mCurtains[i].getActualBottom() == 0) {
                mCurtains[i].setActualBottom(mBars[i].getBottom());
                mCurtains[i].setNewBottom(mBars[i].getBottom());
                mCurtains[i].setPreviousBottom(mBars[i].getBottom());
            }
            mCurtains[i].set(mBars[i].getLeft(), mBars[i].getTop(), mBars[i].getRight(), mCurtains[i].getActualBottom());
        }
    }

    void drawBars(Canvas canvas) {
        for (Bar bar : mBars) {
            bar.draw(canvas);
        }
    }

    void drawCurtains(Canvas canvas) {

        if (mOnMeasure) {
            if (mAnimatorSet.isRunning()) {
                mAnimatorSet.cancel();
            }
            for (Curtain curtain : mCurtains) {
                curtain.setPreviousBottom(curtain.getActualBottom());
            }
            mOnMeasure = false;
        }

        for (Curtain curtain : mCurtains) {
            canvas.drawRect(curtain.getRect(), curtain.getPaint());
        }

        if (!mAnimatorSet.isRunning()) {
            mAnimatorSet = new AnimatorSet();

            for (Curtain curtain : mCurtains) {
                if (curtain.getNewBottom() != curtain.getPreviousBottom()) {
                    ValueAnimator animator = animateCurtain(
                            curtain.getRect(),
                            curtain.getPreviousBottom(),
                            curtain.getNewBottom());
                    mAnimatorSet.play(animator);
                }
            }

            if (!mAnimatorSet.getChildAnimations().isEmpty()) {
                mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        for (Curtain curtain : mCurtains) {
                            curtain.setPreviousBottom(curtain.getNewBottom());
                        }
                    }
                });
                mAnimatorSet.start();
            }
        }
        for (Curtain curtain : mCurtains) {
            curtain.saveActualBottom();
        }
    }

    void initHeader() {
        mHeaderTextArea = new Rect();
        mHeaderTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeaderTextPaint.setColor(mHeaderTextColor);
        mHeaderTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mHeaderTextPaint.setTextSize(mHeaderTextSize);
        mHeaderTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    public void setHeader(String header) {
        mHeaderText = header;
    }

    void setHeaderColor(int color) {
        mHeaderTextColor = color;
    }

    void setHeaderSize(int size) {
        mHeaderTextSize = size;
    }

    void drawHeader(Canvas canvas) {
        canvas.drawText(mHeaderText, 0, mHeaderText.length(), mHeaderTextX, mHeaderTextY, mHeaderTextPaint);
    }

    public void setDuration(int duration) {
        if (!mAnimatorSet.isRunning()) {
            if (duration > 0){
                mAnimationDuration = duration;
            }
        }
    }

    int calculateCurtainBottom(Rect curtain, Rect bar, int value, int maxValue) {
        int tempInt = (maxValue - value) * bar.height() / maxValue;
        return curtain.top + tempInt;
    }

    ValueAnimator animateCurtain(final Rect curtain, int start, int stop) {

        ValueAnimator animator = ValueAnimator.ofInt(start, stop);
        animator.setDuration(mAnimationDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                curtain.bottom = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        return animator;
    }

    void initBackground() {
        mBackgroundRect = new Rect();
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(mBackgroundColor);
    }

    void setBackgroundBounds(int left, int top, int right, int bottom) {
        mBackgroundRect.set(left, top, right, bottom);
    }

    void setBackColor(int color) {
        mBackgroundColor = color;
    }

    void drawBackground(Canvas canvas) {
        if (mWithBackground) {
            canvas.drawRect(mBackgroundRect, mBackgroundPaint);
        }
    }

    float getTextX(Rect textArea, Rect textBounds) {
        return  textArea.width() / 2.0F -
                textBounds.width() / 2.0F -
                textBounds.left +
                textArea.left;
    }

    float getTextY(Rect textArea, Rect textBounds) {
        return  textArea.height() / 2.0F +
                textBounds.height() / 2.0F -
                textBounds.bottom +
                textArea.top;
    }

    float convertDpToPixels(float dpValue) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, metrics);
    }

    Drawable fetchBarColor(TypedArray typedArray, int id, int defaultColor) {
        Drawable barColor = typedArray.getDrawable(id);
        if (barColor == null) {
            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.setPixel(0, 0, defaultColor);
            barColor = new BitmapDrawable(getResources(), bitmap);
            return barColor;
        } else {
            return barColor;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class CustomOutlineProvider extends ViewOutlineProvider {

        int width;
        int height;

        CustomOutlineProvider(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, width, height);
        }
    }
}
