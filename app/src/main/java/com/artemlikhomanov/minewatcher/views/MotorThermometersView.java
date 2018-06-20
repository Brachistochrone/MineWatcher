package com.artemlikhomanov.minewatcher.views;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.views.components.Bar;
import com.artemlikhomanov.minewatcher.views.components.Curtain;

public class MotorThermometersView extends BarView {

    private String mStatorTemperatureText;
    private String mBearingNTemperatureText;
    private String mBearingPTemperatureText;
    private String mGearcaseTemperatureText;
    private String mStatorTemperatureBottomText;
    private String mBearingNTemperatureBottomText;
    private String mBearingPTemperatureBottomText;
    private String mGearcaseTemperatureBottomText;

    private int mTextColor;

    private int mBoundaryColor;

    private int mTemperatureTextSize;
    private int mBottomLineTextSize;

    private int mStatorTemperatureValue;
    private int mBearingNTemperatureValue;
    private int mBearingPTemperatureValue;
    private int mGearcaseTemperatureValue;

    private int mStatorMaxTemperature;
    private int mBearingMaxTemperature;
    private int mGearcaseMaxTemperature;

    private int mThermometerWidth;

    private float mStatorTemperatureTextX;
    private float mStatorTemperatureTextY;
    private float mBearingNTemperatureTextX;
    private float mBearingNTemperatureTextY;
    private float mBearingPTemperatureTextX;
    private float mBearingPTemperatureTextY;
    private float mGearcaseTemperatureTextX;
    private float mGearcaseTemperatureTextY;
    private float mStatorTemperatureBottomTextX;
    private float mStatorTemperatureBottomTextY;
    private float mBearingNTemperatureBottomTextX;
    private float mBearingNTemperatureBottomTextY;
    private float mBearingPTemperatureBottomTextX;
    private float mBearingPTemperatureBottomTextY;
    private float mGearcaseTemperatureBottomTextX;
    private float mGearcaseTemperatureBottomTextY;

    private Rect mBoundaryRect;
    private Rect mStatorTemperatureTextArea;
    private Rect mBearingNTemperatureTextArea;
    private Rect mBearingPTemperatureTextArea;
    private Rect mGearcaseTemperatureTextArea;
    private Rect mStatorTemperatureBottomTextArea;
    private Rect mBearingNTemperatureBottomTextArea;
    private Rect mBearingPTemperatureBottomTextArea;
    private Rect mGearcaseTemperatureBottomTextArea;

    private Paint mBoundaryPaint;
    private Paint mStatorTemperatureTextPaint;
    private Paint mBearingNTemperatureTextPaint;
    private Paint mBearingPTemperatureTextPaint;
    private Paint mGearcaseTemperatureTextPaint;
    private Paint mStatorTemperatureBottomTextPaint;
    private Paint mBearingNTemperatureBottomTextPaint;
    private Paint mBearingPTemperatureBottomTextPaint;
    private Paint mGearcaseTemperatureBottomTextPaint;

    private Drawable mStatorThermometer;
    private Drawable mBearingNThermometer;
    private Drawable mBearingPThermometer;
    private Drawable mGearcaseThermometer;

    public MotorThermometersView(Context context) {
        super(context);
    }

    public MotorThermometersView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MotorThermometersView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MotorThermometersView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init(Context context, @Nullable AttributeSet attrs) {
        mTextBoundsRect = new Rect();
        mStatorTemperatureValue = 0;
        mBearingNTemperatureValue = 0;
        mBearingPTemperatureValue = 0;
        mGearcaseTemperatureValue = 0;
        mAnimationDuration = 1000;
        mDesiredHeight = 140.0f;
        mDesiredWidth = 170.0f;
        mAnimatorSet = new AnimatorSet();

        initBars();

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MotorThermometersView);

            mWithBackground = typedArray.getBoolean(R.styleable.MotorThermometersView_mtv_drawBackground, false);

            String header = typedArray.getString(R.styleable.MotorThermometersView_mtv_headerText);
            if (header == null) {
                setHeader("Motor");
            } else {
                setHeader(header);
            }
            mStatorTemperatureText = typedArray.getString(R.styleable.MotorThermometersView_mtv_statorTemperatureText);
            if (mStatorTemperatureText == null) {
                mStatorTemperatureText = "°C";
            }
            mBearingNTemperatureText = typedArray.getString(R.styleable.MotorThermometersView_mtv_bearingNTemperatureText);
            if (mBearingNTemperatureText == null) {
                mBearingNTemperatureText = "°C";
            }
            mBearingPTemperatureText = typedArray.getString(R.styleable.MotorThermometersView_mtv_bearingPTemperatureText);
            if (mBearingPTemperatureText == null) {
                mBearingPTemperatureText = "°C";
            }
            mGearcaseTemperatureText = typedArray.getString(R.styleable.MotorThermometersView_mtv_gearcaseTemperatureText);
            if (mGearcaseTemperatureText == null) {
                mGearcaseTemperatureText = "°C";
            }
            mStatorTemperatureBottomText = typedArray.getString(R.styleable.MotorThermometersView_mtv_statorTemperatureBottomText);
            if (mStatorTemperatureBottomText == null) {
                mStatorTemperatureBottomText = "Stator";
            }
            mBearingNTemperatureBottomText = typedArray.getString(R.styleable.MotorThermometersView_mtv_bearingNTemperatureBottomText);
            if (mBearingNTemperatureBottomText == null) {
                mBearingNTemperatureBottomText = "N";
            }
            mBearingPTemperatureBottomText = typedArray.getString(R.styleable.MotorThermometersView_mtv_bearingPTemperatureBottomText);
            if (mBearingPTemperatureBottomText == null) {
                mBearingPTemperatureBottomText = "P";
            }
            mGearcaseTemperatureBottomText = typedArray.getString(R.styleable.MotorThermometersView_mtv_gearcaseTemperatureBottomText);
            if (mGearcaseTemperatureBottomText == null) {
                mGearcaseTemperatureBottomText = "Gear";
            }

            setHeaderColor(typedArray.getColor(R.styleable.MotorThermometersView_mtv_headerTextColor, Color.BLACK));
            mTextColor = typedArray.getColor(R.styleable.MotorThermometersView_mtv_textColor, Color.BLACK);

            setHeaderSize(typedArray.getDimensionPixelSize(R.styleable.MotorThermometersView_mtv_headerTextSize, 10));
            mTemperatureTextSize = typedArray.getDimensionPixelSize(R.styleable.MotorThermometersView_mtv_temperatureTextSize, 10);
            mBottomLineTextSize = typedArray.getDimensionPixelSize(R.styleable.MotorThermometersView_mtv_bottomLineTextSize, 10);

            setBackColor(typedArray.getColor(R.styleable.MotorThermometersView_mtv_backgroundColor, Color.WHITE));

            mBoundaryColor = typedArray.getColor(R.styleable.MotorThermometersView_mtv_boundaryColor, Color.BLACK);

            mBars[0].setColor(fetchBarColor(typedArray, R.styleable.MotorThermometersView_mtv_temperatureBarColor, Color.RED));
            mBars[1].setColor(fetchBarColor(typedArray, R.styleable.MotorThermometersView_mtv_temperatureBarColor, Color.RED));
            mBars[2].setColor(fetchBarColor(typedArray, R.styleable.MotorThermometersView_mtv_temperatureBarColor, Color.RED));
            mBars[3].setColor(fetchBarColor(typedArray, R.styleable.MotorThermometersView_mtv_temperatureBarColor, Color.RED));

            mStatorThermometer = fetchThermometerImage(typedArray);
            mBearingNThermometer = fetchThermometerImage(typedArray);
            mBearingPThermometer = fetchThermometerImage(typedArray);
            mGearcaseThermometer = fetchThermometerImage(typedArray);

            mStatorMaxTemperature = typedArray.getInteger(R.styleable.MotorThermometersView_mtv_statorMaxTemperature, 160);
            mBearingMaxTemperature = typedArray.getInteger(R.styleable.MotorThermometersView_mtv_bearingMaxTemperature, 80);
            mGearcaseMaxTemperature = typedArray.getInteger(R.styleable.MotorThermometersView_mtv_gearcaseMaxTemperature, 80);

            typedArray.recycle();
        } else {
            mWithBackground = false;

            setHeader("Motor");
            mStatorTemperatureText = "°C";
            mBearingNTemperatureText = "°C";
            mBearingPTemperatureText = "°C";
            mGearcaseTemperatureText = "°C";
            mStatorTemperatureBottomText = "Stator";
            mBearingNTemperatureBottomText = "N";
            mBearingPTemperatureBottomText = "P";
            mGearcaseTemperatureBottomText = "Gear";

            setHeaderColor(Color.BLACK);
            mTextColor = Color.BLACK;

            setHeaderSize(10);
            mTemperatureTextSize = 10;
            mBottomLineTextSize = 10;

            setBackColor(Color.WHITE);

            mBoundaryColor = Color.BLACK;

            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.setPixel(0, 0, Color.RED);
            mBars[0].setColor(new BitmapDrawable(context.getResources(), bitmap));
            mBars[1].setColor(new BitmapDrawable(context.getResources(), bitmap));
            mBars[2].setColor(new BitmapDrawable(context.getResources(), bitmap));
            mBars[3].setColor(new BitmapDrawable(context.getResources(), bitmap));

            mStatorThermometer = fetchThermometerImage(null);
            mBearingNThermometer = fetchThermometerImage(null);
            mBearingPThermometer = fetchThermometerImage(null);
            mGearcaseThermometer = fetchThermometerImage(null);

            mStatorMaxTemperature = 160;
            mBearingMaxTemperature = 80;
            mGearcaseMaxTemperature = 80;
        }

        initBackground();
        initBoundaries();
        initHeader();
        initStatorTemperatureLine();
        initBearingNTemperatureLine();
        initBearingPTemperatureLine();
        initGearcaseTemperatureLine();
        initStatorTemperatureBottomLine();
        initBearingNTemperatureBottomLine();
        initBearingPTemperatureBottomLine();
        initGearcaseTemperatureBottomLine();
        initCurtains();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        canvas.drawRect(mBoundaryRect, mBoundaryPaint);
        drawHeader(canvas);
        canvas.drawText(mStatorTemperatureText, 0, mStatorTemperatureText.length(), mStatorTemperatureTextX, mStatorTemperatureTextY, mStatorTemperatureTextPaint);
        canvas.drawText(mBearingNTemperatureText, 0, mBearingNTemperatureText.length(), mBearingNTemperatureTextX, mBearingNTemperatureTextY, mBearingNTemperatureTextPaint);
        canvas.drawText(mBearingPTemperatureText, 0, mBearingPTemperatureText.length(), mBearingPTemperatureTextX, mBearingPTemperatureTextY, mBearingPTemperatureTextPaint);
        canvas.drawText(mGearcaseTemperatureText, 0, mGearcaseTemperatureText.length(), mGearcaseTemperatureTextX, mGearcaseTemperatureTextY, mGearcaseTemperatureTextPaint);
        canvas.drawText(mStatorTemperatureBottomText, 0, mStatorTemperatureBottomText.length(), mStatorTemperatureBottomTextX, mStatorTemperatureBottomTextY, mStatorTemperatureBottomTextPaint);
        canvas.drawText(mBearingNTemperatureBottomText, 0, mBearingNTemperatureBottomText.length(), mBearingNTemperatureBottomTextX, mBearingNTemperatureBottomTextY, mBearingNTemperatureBottomTextPaint);
        canvas.drawText(mBearingPTemperatureBottomText, 0, mBearingPTemperatureBottomText.length(), mBearingPTemperatureBottomTextX, mBearingPTemperatureBottomTextY, mBearingPTemperatureBottomTextPaint);
        canvas.drawText(mGearcaseTemperatureBottomText, 0, mGearcaseTemperatureBottomText.length(), mGearcaseTemperatureBottomTextX, mGearcaseTemperatureBottomTextY, mGearcaseTemperatureBottomTextPaint);
        mStatorThermometer.draw(canvas);
        mBearingNThermometer.draw(canvas);
        mBearingPThermometer.draw(canvas);
        mGearcaseThermometer.draw(canvas);
        drawBars(canvas);
        drawCurtains(canvas);
    }

    @Override
    void initCurtains() {
        mCurtains = new Curtain[4];
        Curtain statorTemperatureCurtain = new Curtain();
        Curtain bearingNTemperatureCurtain = new Curtain();
        Curtain bearingPTemperatureCurtain = new Curtain();
        Curtain gearcaseTemperatureCurtain = new Curtain();
        mCurtains[0] = statorTemperatureCurtain;
        mCurtains[1] = bearingNTemperatureCurtain;
        mCurtains[2] = bearingPTemperatureCurtain;
        mCurtains[3] = gearcaseTemperatureCurtain;
    }

    @Override
    void initBars() {
        mBars = new Bar[4];
        Bar statorTemperatureBar = new Bar();
        Bar bearingNTemperatureBar = new Bar();
        Bar bearingPTemperatureBar = new Bar();
        Bar gearcaseTemperatureBar = new Bar();
        mBars[0] = statorTemperatureBar;
        mBars[1] = bearingNTemperatureBar;
        mBars[2] = bearingPTemperatureBar;
        mBars[3] = gearcaseTemperatureBar;
    }

    @Override
    void measureBars() {
        Rect thermometerBounds = new Rect();
        thermometerBounds.set(mStatorThermometer.getBounds());
        int left = (int) Math.ceil(thermometerBounds.width() / 4.0f);
        int right = Math.round(thermometerBounds.width() * 0.393f);
        int top = Math.round(thermometerBounds.top + thermometerBounds.height() / 20.0f);
        int bottom = Math.round(top + thermometerBounds.height() / 1.22f);
        mBars[0].setBounds(thermometerBounds.left + left, top, thermometerBounds.left + right, bottom);
        thermometerBounds.set(mBearingNThermometer.getBounds());
        mBars[1].setBounds(thermometerBounds.left + left, top, thermometerBounds.left + right, bottom);
        thermometerBounds.set(mBearingPThermometer.getBounds());
        mBars[2].setBounds(thermometerBounds.left + left, top, thermometerBounds.left + right, bottom);
        thermometerBounds.set(mGearcaseThermometer.getBounds());
        mBars[3].setBounds(thermometerBounds.left + left, top, thermometerBounds.left + right, bottom);
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

    @Override
    void measureChildren() {
        measureBoundaries();
        measureHeaderText();
        measureStatorTemperatureLine();
        measureBearingNTemperatureLine();
        measureBearingPTemperatureLine();
        measureGearcaseTemperatureLine();
        measureStatorTemperatureBottomLine();
        measureBearingNTemperatureBottomLine();
        measureBearingPTemperatureBottomLine();
        measureGearcaseTemperatureBottomLine();
        measureStatorThermometer();
        measureBearingNThermometer();
        measureBearingPThermometer();
        measureGearcaseThermometer();
        measureBars();
        measureCurtains();
    }

    private void initBoundaries() {
        mBoundaryRect = new Rect();
        mBoundaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundaryPaint.setStyle(Paint.Style.STROKE);
        mBoundaryPaint.setColor(mBoundaryColor);
    }

    private void initStatorTemperatureLine() {
        mStatorTemperatureTextArea = new Rect();
        mStatorTemperatureTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatorTemperatureTextPaint.setColor(mTextColor);
        mStatorTemperatureTextPaint.setTextSize(mTemperatureTextSize);
        mStatorTemperatureTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initBearingNTemperatureLine() {
        mBearingNTemperatureTextArea = new Rect();
        mBearingNTemperatureTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBearingNTemperatureTextPaint.setColor(mTextColor);
        mBearingNTemperatureTextPaint.setTextSize(mTemperatureTextSize);
        mBearingNTemperatureTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initBearingPTemperatureLine() {
        mBearingPTemperatureTextArea = new Rect();
        mBearingPTemperatureTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBearingPTemperatureTextPaint.setColor(mTextColor);
        mBearingPTemperatureTextPaint.setTextSize(mTemperatureTextSize);
        mBearingPTemperatureTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initGearcaseTemperatureLine() {
        mGearcaseTemperatureTextArea = new Rect();
        mGearcaseTemperatureTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGearcaseTemperatureTextPaint.setColor(mTextColor);
        mGearcaseTemperatureTextPaint.setTextSize(mTemperatureTextSize);
        mGearcaseTemperatureTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initStatorTemperatureBottomLine() {
        mStatorTemperatureBottomTextArea = new Rect();
        mStatorTemperatureBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStatorTemperatureBottomTextPaint.setColor(mTextColor);
        mStatorTemperatureBottomTextPaint.setTextSize(mBottomLineTextSize);
        mStatorTemperatureBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initBearingNTemperatureBottomLine() {
        mBearingNTemperatureBottomTextArea = new Rect();
        mBearingNTemperatureBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBearingNTemperatureBottomTextPaint.setColor(mTextColor);
        mBearingNTemperatureBottomTextPaint.setTextSize(mBottomLineTextSize);
        mBearingNTemperatureBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initBearingPTemperatureBottomLine() {
        mBearingPTemperatureBottomTextArea = new Rect();
        mBearingPTemperatureBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBearingPTemperatureBottomTextPaint.setColor(mTextColor);
        mBearingPTemperatureBottomTextPaint.setTextSize(mBottomLineTextSize);
        mBearingPTemperatureBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initGearcaseTemperatureBottomLine() {
        mGearcaseTemperatureBottomTextArea = new Rect();
        mGearcaseTemperatureBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGearcaseTemperatureBottomTextPaint.setColor(mTextColor);
        mGearcaseTemperatureBottomTextPaint.setTextSize(mBottomLineTextSize);
        mGearcaseTemperatureBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void measureBoundaries() {
        mBoundaryRect.set(getPaddingStart(), getPaddingTop(), getPaddingStart() + mInternalViewWidth, getPaddingTop() + mInternalViewHeight);
        mBoundaryPaint.setStrokeWidth(convertDpToPixels(2.0f));
    }

    private void measureStatorTemperatureLine() {
        mStatorTemperatureTextArea.set(getPaddingStart(),
                      mHeaderTextArea.bottom,
                getPaddingStart() + mInternalViewWidth / 4,
                (int) (mHeaderTextArea.bottom + mTemperatureTextSize + mBoundaryPaint.getStrokeWidth() * 2));
        mStatorTemperatureTextPaint.getTextBounds(mStatorTemperatureText, 0, mStatorTemperatureText.length(), mTextBoundsRect);
        mStatorTemperatureTextX = getTextX(mStatorTemperatureTextArea, mTextBoundsRect);
        mStatorTemperatureTextY = getTextY(mStatorTemperatureTextArea, mTextBoundsRect);
    }

    private void measureBearingNTemperatureLine() {
        mBearingNTemperatureTextArea.set(mStatorTemperatureTextArea.right,
                      mStatorTemperatureTextArea.top,
                mStatorTemperatureTextArea.right + mInternalViewWidth / 4,
                      mStatorTemperatureTextArea.bottom);
        mBearingNTemperatureTextPaint.getTextBounds(mBearingNTemperatureText, 0, mBearingNTemperatureText.length(), mTextBoundsRect);
        mBearingNTemperatureTextX = getTextX(mBearingNTemperatureTextArea, mTextBoundsRect);
        mBearingNTemperatureTextY = getTextY(mBearingNTemperatureTextArea, mTextBoundsRect);
    }

    private void measureBearingPTemperatureLine() {
        mBearingPTemperatureTextArea.set(mBearingNTemperatureTextArea.right,
                      mStatorTemperatureTextArea.top,
                mBearingNTemperatureTextArea.right + mInternalViewWidth / 4,
                      mStatorTemperatureTextArea.bottom);
        mBearingPTemperatureTextPaint.getTextBounds(mBearingPTemperatureText, 0, mBearingPTemperatureText.length(), mTextBoundsRect);
        mBearingPTemperatureTextX = getTextX(mBearingPTemperatureTextArea, mTextBoundsRect);
        mBearingPTemperatureTextY = getTextY(mBearingPTemperatureTextArea, mTextBoundsRect);
    }

    private void measureGearcaseTemperatureLine() {
        mGearcaseTemperatureTextArea.set(mBearingPTemperatureTextArea.right,
                      mStatorTemperatureTextArea.top,
                getPaddingLeft() + mInternalViewWidth,
                      mStatorTemperatureTextArea.bottom);
        mGearcaseTemperatureTextPaint.getTextBounds(mGearcaseTemperatureText, 0, mGearcaseTemperatureText.length(), mTextBoundsRect);
        mGearcaseTemperatureTextX = getTextX(mGearcaseTemperatureTextArea, mTextBoundsRect);
        mGearcaseTemperatureTextY = getTextY(mGearcaseTemperatureTextArea, mTextBoundsRect);
    }

    private void measureStatorTemperatureBottomLine() {
        mStatorTemperatureBottomTextArea.set(mStatorTemperatureTextArea.left,
                (int) (getPaddingTop() + mInternalViewHeight - mBottomLineTextSize - mBoundaryPaint.getStrokeWidth() * 3),
                       mStatorTemperatureTextArea.right,
                (int) (getPaddingTop() + mInternalViewHeight - mBoundaryPaint.getStrokeWidth()));
        mStatorTemperatureBottomTextPaint.getTextBounds(mStatorTemperatureBottomText, 0, mStatorTemperatureBottomText.length(), mTextBoundsRect);
        mStatorTemperatureBottomTextX = getTextX(mStatorTemperatureBottomTextArea, mTextBoundsRect);
        mStatorTemperatureBottomTextY = getTextY(mStatorTemperatureBottomTextArea, mTextBoundsRect);
    }

    private void measureBearingNTemperatureBottomLine() {
        mBearingNTemperatureBottomTextArea.set(mStatorTemperatureBottomTextArea.right,
                      mStatorTemperatureBottomTextArea.top,
                      mBearingNTemperatureTextArea.right,
                      mStatorTemperatureBottomTextArea.bottom);
        mBearingNTemperatureBottomTextPaint.getTextBounds(mBearingNTemperatureBottomText, 0, mBearingNTemperatureBottomText.length(), mTextBoundsRect);
        mBearingNTemperatureBottomTextX = getTextX(mBearingNTemperatureBottomTextArea, mTextBoundsRect);
        mBearingNTemperatureBottomTextY = getTextY(mBearingNTemperatureBottomTextArea, mTextBoundsRect);
    }

    private void measureBearingPTemperatureBottomLine() {
        mBearingPTemperatureBottomTextArea.set(mBearingNTemperatureBottomTextArea.right,
                      mStatorTemperatureBottomTextArea.top,
                      mBearingPTemperatureTextArea.right,
                      mStatorTemperatureBottomTextArea.bottom);
        mBearingPTemperatureBottomTextPaint.getTextBounds(mBearingPTemperatureBottomText, 0, mBearingPTemperatureBottomText.length(), mTextBoundsRect);
        mBearingPTemperatureBottomTextX = getTextX(mBearingPTemperatureBottomTextArea, mTextBoundsRect);
        mBearingPTemperatureBottomTextY = getTextY(mBearingPTemperatureBottomTextArea, mTextBoundsRect);
    }

    private void measureGearcaseTemperatureBottomLine() {
        mGearcaseTemperatureBottomTextArea.set(mBearingPTemperatureBottomTextArea.right,
                      mStatorTemperatureBottomTextArea.top,
                      mGearcaseTemperatureTextArea.right,
                      mStatorTemperatureBottomTextArea.bottom);
        mGearcaseTemperatureBottomTextPaint.getTextBounds(mGearcaseTemperatureBottomText, 0, mGearcaseTemperatureBottomText.length(), mTextBoundsRect);
        mGearcaseTemperatureBottomTextX = getTextX(mGearcaseTemperatureBottomTextArea, mTextBoundsRect);
        mGearcaseTemperatureBottomTextY = getTextY(mGearcaseTemperatureBottomTextArea, mTextBoundsRect);
    }

    private void measureStatorThermometer() {
        mThermometerWidth = (int) Math.ceil((mStatorTemperatureBottomTextArea.top - mStatorTemperatureTextArea.bottom) / 3.7f);//округлить в большую сторону
        int left = mStatorTemperatureTextArea.left + ((mStatorTemperatureTextArea.width() - mThermometerWidth) / 2);
        mStatorThermometer.setBounds(left,
                      mStatorTemperatureTextArea.bottom,
                left + mThermometerWidth,
                      mStatorTemperatureBottomTextArea.top);
    }

    private void measureBearingNThermometer() {
        int left = mBearingNTemperatureTextArea.left + ((mBearingNTemperatureTextArea.width() - mThermometerWidth) / 2);
        mBearingNThermometer.setBounds(left,
                      mBearingNTemperatureTextArea.bottom,
                left + mThermometerWidth,
                      mBearingNTemperatureBottomTextArea.top);
    }

    private void measureBearingPThermometer() {
        int left = mBearingPTemperatureTextArea.left + ((mBearingPTemperatureTextArea.width() - mThermometerWidth) / 2);
        mBearingPThermometer.setBounds(left,
                      mBearingPTemperatureTextArea.bottom,
                left + mThermometerWidth,
                      mBearingPTemperatureBottomTextArea.top);
    }

    private void measureGearcaseThermometer() {
        int left = mGearcaseTemperatureTextArea.left + ((mGearcaseTemperatureTextArea.width() - mThermometerWidth) / 2);
        mGearcaseThermometer.setBounds(left,
                mGearcaseTemperatureTextArea.bottom,
                left + mThermometerWidth,
                mGearcaseTemperatureBottomTextArea.top);
    }

    private Drawable fetchThermometerImage(TypedArray typedArray) {
        if (typedArray != null) {
            Drawable drawable = typedArray.getDrawable(R.styleable.MotorThermometersView_mtv_thermometerImage);
            if (drawable == null) {
                Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                bitmap.setPixel(0, 0, Color.WHITE);
                drawable = new BitmapDrawable(getResources(), bitmap);
            }
            return drawable;
        } else {
            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.setPixel(0, 0, Color.WHITE);
            return new BitmapDrawable(getResources(), bitmap);
        }
    }

    public void updateData(int statorTemperature, String statorTemperatureString, int bearingNTemperature, String bearingNTemperatureString,
                           int bearingPTemperature, String bearingPTemperatureString, int gearcaseTemperature, String gearcaseTemperatureString) {

        if (!mAnimatorSet.isRunning()) {
            if (mStatorTemperatureValue != statorTemperature ||
                    mBearingNTemperatureValue != bearingNTemperature  ||
                    mBearingPTemperatureValue != bearingPTemperature ||
                    mGearcaseTemperatureValue != gearcaseTemperature) {

                if (mStatorTemperatureValue != statorTemperature) {
                    if (statorTemperature > mStatorMaxTemperature) {
                        mStatorTemperatureValue = mStatorMaxTemperature;
                    } else if (statorTemperature < 0) {
                        mStatorTemperatureValue = 0;
                    } else {
                        mStatorTemperatureValue = statorTemperature;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mStatorTemperatureValue, mStatorMaxTemperature);
                    mCurtains[0].setNewBottom(newBottom);
                    mStatorTemperatureText = statorTemperatureString;
                }

                if (mBearingNTemperatureValue != bearingNTemperature) {
                    if (bearingNTemperature > mBearingMaxTemperature) {
                        mBearingNTemperatureValue = mBearingMaxTemperature;
                    } else if (bearingNTemperature < 0) {
                        mBearingNTemperatureValue = 0;
                    } else {
                        mBearingNTemperatureValue = bearingNTemperature;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mBearingNTemperatureValue, mBearingMaxTemperature);
                    mCurtains[1].setNewBottom(newBottom);
                    mBearingNTemperatureText = bearingNTemperatureString;
                }

                if (mBearingPTemperatureValue != bearingPTemperature) {
                    if (bearingPTemperature > mBearingMaxTemperature) {
                        mBearingPTemperatureValue = mBearingMaxTemperature;
                    } else if (bearingPTemperature < 0) {
                        mBearingPTemperatureValue = 0;
                    } else {
                        mBearingPTemperatureValue = bearingPTemperature;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[2].getRect(), mBars[2].getRect(), mBearingPTemperatureValue, mBearingMaxTemperature);
                    mCurtains[2].setNewBottom(newBottom);
                    mBearingPTemperatureText = bearingPTemperatureString;
                }

                if (mGearcaseTemperatureValue != gearcaseTemperature) {
                    if (gearcaseTemperature > mGearcaseMaxTemperature) {
                        mGearcaseTemperatureValue = mGearcaseMaxTemperature;
                    } else if (gearcaseTemperature < 0) {
                        mGearcaseTemperatureValue = 0;
                    } else {
                        mGearcaseTemperatureValue = gearcaseTemperature;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[3].getRect(), mBars[3].getRect(), mGearcaseTemperatureValue, mGearcaseMaxTemperature);
                    mCurtains[3].setNewBottom(newBottom);
                    mGearcaseTemperatureText = gearcaseTemperatureString;
                }
                invalidate();
            }
        }
    }

    public void resetData(String minTemperatureString) {
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        if (mStatorTemperatureValue != 0 ||
                mBearingNTemperatureValue != 0  ||
                mBearingPTemperatureValue != 0 ||
                mGearcaseTemperatureValue != 0) {

            if (mStatorTemperatureValue != 0) {
                mStatorTemperatureValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mStatorTemperatureValue, mStatorMaxTemperature);
                mCurtains[0].setNewBottom(newBottom);
                mStatorTemperatureText = minTemperatureString;
            }

            if (mBearingNTemperatureValue != 0) {
                mBearingNTemperatureValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mBearingNTemperatureValue, mBearingMaxTemperature);
                mCurtains[1].setNewBottom(newBottom);
                mBearingNTemperatureText = minTemperatureString;
            }

            if (mBearingPTemperatureValue != 0) {
                mBearingPTemperatureValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[2].getRect(), mBars[2].getRect(), mBearingPTemperatureValue, mBearingMaxTemperature);
                mCurtains[2].setNewBottom(newBottom);
                mBearingPTemperatureText = minTemperatureString;
            }

            if (mGearcaseTemperatureValue != 0) {
                mGearcaseTemperatureValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[3].getRect(), mBars[3].getRect(), mGearcaseTemperatureValue, mGearcaseMaxTemperature);
                mCurtains[3].setNewBottom(newBottom);
                mGearcaseTemperatureText = minTemperatureString;
            }
            invalidate();
        }
    }

    public void setStrings(String header, String statorTemp, String BearingNTemp, String BearingPTemp, String gearcaseTemp,
                           String statorBottom, String BearingNBottom, String BearingPBottom, String gearcaseBottom) {
        setHeader(header);
        mStatorTemperatureText = statorTemp;
        mBearingNTemperatureText = BearingNTemp;
        mBearingPTemperatureText = BearingPTemp;
        mGearcaseTemperatureText = gearcaseTemp;
        mStatorTemperatureBottomText = statorBottom;
        mBearingNTemperatureBottomText = BearingNBottom;
        mBearingPTemperatureBottomText = BearingPBottom;
        mGearcaseTemperatureBottomText = gearcaseBottom;
    }
}
