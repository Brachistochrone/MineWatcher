package com.artemlikhomanov.minewatcher.views;

import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.views.components.Bar;
import com.artemlikhomanov.minewatcher.views.components.Curtain;

public class MotorCurrentAndTemperatureView extends BarView {

    private String mCurrentText;//!
    private String mTemperatureText;//!

    private int mTextColor;

    private int mBoundaryColor;

    private int mCurrentTextSize;
    private int mTemperatureTextSize;

    private int mCurrentValue;//!
    private int mTemperatureValue;//!

    private int mMaxCurrent;//!
    private int mMaxTemperature;//!

    private float mCurrentTextX;
    private float mCurrentTextY;
    private float mTemperatureTextX;
    private float mTemperatureTextY;

    private Rect mBoundaryRect;
    private Rect mCurrentTextArea;
    private Rect mTemperatureTextArea;

    private Paint mBoundaryPaint;
    private Paint mCurrentTextPaint;
    private Paint mTemperatureTextPaint;

    public MotorCurrentAndTemperatureView(Context context) {
        super(context);
}

    public MotorCurrentAndTemperatureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MotorCurrentAndTemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MotorCurrentAndTemperatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        canvas.drawRect(mBoundaryRect, mBoundaryPaint);
        drawHeader(canvas);
        canvas.drawText(mCurrentText, 0, mCurrentText.length(), mCurrentTextX, mCurrentTextY, mCurrentTextPaint);
        canvas.drawText(mTemperatureText, 0, mTemperatureText.length(), mTemperatureTextX, mTemperatureTextY, mTemperatureTextPaint);
        drawBars(canvas);
        drawCurtains(canvas);
    }

    @Override
    void init(Context context, @Nullable AttributeSet attrs) {
        mTextBoundsRect = new Rect();
        mCurrentValue = 0;
        mTemperatureValue = 0;
        mAnimationDuration = 1000;
        mDesiredHeight = 120.0f;
        mDesiredWidth = 60.0f;
        mAnimatorSet = new AnimatorSet();

        initBars();

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MotorCurrentAndTemperatureView);

            mWithBackground = typedArray.getBoolean(R.styleable.MotorCurrentAndTemperatureView_mctv_drawBackground, false);

            String header = typedArray.getString(R.styleable.MotorCurrentAndTemperatureView_mctv_headerText);
            if (header == null) {
                setHeader("Motor");
            } else {
                setHeader(header);
            }
            mCurrentText = typedArray.getString(R.styleable.MotorCurrentAndTemperatureView_mctv_currentText);
            if (mCurrentText == null) {
                mCurrentText = "%";
            }
            mTemperatureText = typedArray.getString(R.styleable.MotorCurrentAndTemperatureView_mctv_temperatureText);
            if (mTemperatureText == null) {
                mTemperatureText = "°C";
            }

            setHeaderColor(typedArray.getColor(R.styleable.MotorCurrentAndTemperatureView_mctv_headerTextColor, Color.BLACK));
            mTextColor = typedArray.getColor(R.styleable.MotorCurrentAndTemperatureView_mctv_textColor, Color.BLACK);

            setHeaderSize(typedArray.getDimensionPixelSize(R.styleable.MotorCurrentAndTemperatureView_mctv_headerTextSize, 10));
            mCurrentTextSize = typedArray.getDimensionPixelSize(R.styleable.MotorCurrentAndTemperatureView_mctv_currentTextSize, 10);
            mTemperatureTextSize = typedArray.getDimensionPixelSize(R.styleable.MotorCurrentAndTemperatureView_mctv_temperatureTextSize, 10);

            setBackColor(typedArray.getColor(R.styleable.MotorCurrentAndTemperatureView_mctv_backgroundColor, Color.WHITE));

            mBoundaryColor = typedArray.getColor(R.styleable.MotorCurrentAndTemperatureView_mctv_boundaryColor, Color.BLACK);

            mBars[0].setColor(fetchBarColor(typedArray, R.styleable.MotorCurrentAndTemperatureView_mctv_currentBarColor, Color.GREEN));
            mBars[1].setColor(fetchBarColor(typedArray, R.styleable.MotorCurrentAndTemperatureView_mctv_temperatureBarColor, Color.BLUE));

            mMaxCurrent = typedArray.getInteger(R.styleable.MotorCurrentAndTemperatureView_mctv_maxCurrent, 300);
            mMaxTemperature = typedArray.getInteger(R.styleable.MotorCurrentAndTemperatureView_mctv_maxTemperature, 150);

            typedArray.recycle();
        } else {
            mWithBackground = false;

            setHeader("Motor");
            mCurrentText = "%";
            mTemperatureText = "°C";

            setHeaderColor(Color.BLACK);
            mTextColor = Color.BLACK;

            setHeaderSize(10);
            mCurrentTextSize = 10;
            mTemperatureTextSize = 10;

            setBackColor(Color.WHITE);

            mBoundaryColor = Color.BLACK;

            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.setPixel(0, 0, Color.GREEN);
            mBars[0].setColor(new BitmapDrawable(context.getResources(), bitmap));
            bitmap.setPixel(0, 0, Color.BLUE);
            mBars[1].setColor(new BitmapDrawable(context.getResources(), bitmap));

            mMaxCurrent = 300;
            mMaxTemperature = 150;
        }

        initBackground();
        initBoundaries();
        initHeader();
        initCurrentLine();
        initTemperatureLine();
        initCurtains();
    }

    @Override
    void initCurtains() {
        mCurtains = new Curtain[2];
        Curtain currentCurtain = new Curtain();
        Curtain temperatureCurtain = new Curtain();
        mCurtains[0] = currentCurtain;
        mCurtains[1] = temperatureCurtain;
    }

    @Override
    void initBars() {
        mBars = new Bar[2];
        Bar currentBar = new Bar();
        Bar temperatureBar = new Bar();
        mBars[0] = currentBar;
        mBars[1] = temperatureBar;
    }

    @Override
    void measureChildren() {
        //границы рамки
        measureBoundaries();
        measureHeaderText();
        //границы текста тока
        measureCurrentText();
        //границы текста температуры
        measureTemperatureText();
        measureBars();
        measureCurtains();
    }

    @Override
    void measureBars() {
        mBars[0].setBounds(getPaddingStart() + mInternalViewWidth / 8,
                      mCurrentTextArea.bottom,
                getPaddingStart() + (mInternalViewWidth / 8) * 3,
                (int) (getPaddingTop() + mInternalViewHeight - mBoundaryPaint.getStrokeWidth() * 2));
        mBars[1].setBounds(getPaddingStart() + (mInternalViewWidth / 8) * 5,
                      mTemperatureTextArea.bottom,
                getPaddingStart() + (mInternalViewWidth / 8) * 7,
                (int) (getPaddingTop() + mInternalViewHeight - mBoundaryPaint.getStrokeWidth() * 2));
    }

    @Override
    void measureHeaderText() {
        mHeaderTextArea.set(getPaddingStart(),
                getPaddingTop(),
                getPaddingStart() + mInternalViewWidth,
                (int) (getPaddingTop() + mHeaderTextSize + mBoundaryPaint.getStrokeWidth() * 2));
        mHeaderTextPaint.getTextBounds(mHeaderText, 0, mHeaderText.length(), mTextBoundsRect);
        mHeaderTextX = getTextX(mHeaderTextArea, mTextBoundsRect);
        mHeaderTextY = getTextY(mHeaderTextArea, mTextBoundsRect);
    }

    private void initBoundaries() {
        mBoundaryRect = new Rect();
        mBoundaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundaryPaint.setStyle(Paint.Style.STROKE);
        mBoundaryPaint.setColor(mBoundaryColor);
    }

    private void initCurrentLine() {
        mCurrentTextArea = new Rect();
        mCurrentTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurrentTextPaint.setColor(mTextColor);
        mCurrentTextPaint.setTextSize(mCurrentTextSize);
        mCurrentTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initTemperatureLine() {
        mTemperatureTextArea = new Rect();
        mTemperatureTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTemperatureTextPaint.setColor(mTextColor);
        mTemperatureTextPaint.setTextSize(mTemperatureTextSize);
        mTemperatureTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void measureBoundaries() {
        mBoundaryRect.set(getPaddingStart(), getPaddingTop(), getPaddingStart() + mInternalViewWidth, getPaddingTop() + mInternalViewHeight);
        mBoundaryPaint.setStrokeWidth(convertDpToPixels(2.0f));
    }

    private void measureCurrentText() {
        mCurrentTextArea.set(getPaddingStart(),
                      mHeaderTextArea.bottom,
                getPaddingStart() + mInternalViewWidth / 2,
                (int) (getPaddingTop() + mHeaderTextArea.height() + mCurrentTextSize + mBoundaryPaint.getStrokeWidth() * 2));
        mCurrentTextPaint.getTextBounds(mCurrentText, 0, mCurrentText.length(), mTextBoundsRect);
        mCurrentTextX = getTextX(mCurrentTextArea, mTextBoundsRect);
        mCurrentTextY = getTextY(mCurrentTextArea, mTextBoundsRect);
    }

    private void measureTemperatureText() {
        mTemperatureTextArea.set(mCurrentTextArea.right, mCurrentTextArea.top,getPaddingStart() + mInternalViewWidth, mCurrentTextArea.bottom);
        mTemperatureTextPaint.getTextBounds(mTemperatureText, 0, mTemperatureText.length(), mTextBoundsRect);
        mTemperatureTextX = getTextX(mTemperatureTextArea, mTextBoundsRect);
        mTemperatureTextY = getTextY(mTemperatureTextArea, mTextBoundsRect);
    }

    public void updateData(int current, String currentText, int temperature, String temperatureText) {

        if (!mAnimatorSet.isRunning()) {
            if (mCurrentValue != current || mTemperatureValue != temperature) {

                if (mCurrentValue != current) {
                    if (current > mMaxCurrent) {
                        mCurrentValue = mMaxCurrent;
                    } else if (current < 0) {
                        mCurrentValue = 0;
                    } else {
                        mCurrentValue = current;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mCurrentValue, mMaxCurrent);
                    mCurtains[0].setNewBottom(newBottom);
                    mCurrentText = currentText;
                }

                if (mTemperatureValue != temperature) {
                    if (temperature > mMaxTemperature) {
                        mTemperatureValue = mMaxTemperature;
                    } else if (temperature < 0) {
                        mTemperatureValue = 0;
                    } else {
                        mTemperatureValue = temperature;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mTemperatureValue, mMaxTemperature);
                    mCurtains[1].setNewBottom(newBottom);
                    mTemperatureText = temperatureText;
                }
                invalidate();
            }
        }
    }

    public void resetData(String currentText, String temperatureText) {
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        if (mCurrentValue != 0 || mTemperatureValue != 0) {

            if (mCurrentValue != 0) {
                mCurrentValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mCurrentValue, mMaxCurrent);
                mCurtains[0].setNewBottom(newBottom);
                mCurrentText = currentText;
            }

            if (mTemperatureValue != 0) {
                mTemperatureValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mTemperatureValue, mMaxTemperature);
                mCurtains[1].setNewBottom(newBottom);
                mTemperatureText = temperatureText;
            }
            invalidate();
        }
    }
}
