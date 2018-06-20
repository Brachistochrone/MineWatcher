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

public class InverterFrequencyView extends BarView {

    private String mSubHeaderText;
    private String mGivenFrequencyText;
    private String mActualFrequencyText;
    private String mGivenFrequencyBottomText;
    private String mActualFrequencyBottomText;

    private int mTextColor;

    private int mBoundaryColor;

    private int mSubHeaderTextSize;
    private int mFrequencyTextSize;
    private int mBottomLineTextSize;

    private int mGivenFrequencyValue;
    private int mActualFrequencyValue;

    private int mMaxFrequency;

    private float mSubHeaderTextX;
    private float mSubHeaderTextY;
    private float mGivenFrequencyTextX;
    private float mGivenFrequencyTextY;
    private float mActualFrequencyTextX;
    private float mActualFrequencyTextY;
    private float mGivenFrequencyBottomTextX;
    private float mGivenFrequencyBottomTextY;
    private float mActualFrequencyBottomTextX;
    private float mActualFrequencyBottomTextY;

    private Rect mBoundaryRect;
    private Rect mSubHeaderTextArea;
    private Rect mGivenFrequencyTextArea;
    private Rect mActualFrequencyTextArea;
    private Rect mGivenFrequencyBottomTextArea;
    private Rect mActualFrequencyBottomTextArea;

    private Paint mBoundaryPaint;
    private Paint mSubHeaderTextPaint;
    private Paint mGivenFrequencyTextPaint;
    private Paint mActualFrequencyTextPaint;
    private Paint mGivenFrequencyBottomTextPaint;
    private Paint mActualFrequencyBottomTextPaint;

    public InverterFrequencyView(Context context) {
        super(context);
    }

    public InverterFrequencyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InverterFrequencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InverterFrequencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    void init(Context context, @Nullable AttributeSet attrs) {
        mTextBoundsRect = new Rect();
        mGivenFrequencyValue = 0;
        mActualFrequencyValue = 0;
        mAnimationDuration = 1000;
        mDesiredHeight = 120.0f;
        mDesiredWidth = 60.0f;
        mAnimatorSet = new AnimatorSet();

        initBars();

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InverterFrequencyView);

            mWithBackground = typedArray.getBoolean(R.styleable.InverterFrequencyView_ifv_drawBackground, false);

            String header = typedArray.getString(R.styleable.InverterFrequencyView_ifv_headerText);
            if (header == null) {
                setHeader("Inverter");
            } else {
                setHeader(header);
            }
            mSubHeaderText = typedArray.getString(R.styleable.InverterFrequencyView_ifv_subHeaderText);
            if (mSubHeaderText == null) {
                mSubHeaderText = "frequency, Hz";
            }
            mGivenFrequencyText = typedArray.getString(R.styleable.InverterFrequencyView_ifv_givenFrequencyText);
            if (mGivenFrequencyText == null) {
                mGivenFrequencyText = "0.0";
            }
            mActualFrequencyText = typedArray.getString(R.styleable.InverterFrequencyView_ifv_actualFrequencyText);
            if (mActualFrequencyText == null) {
                mActualFrequencyText = "0.0";
            }
            mGivenFrequencyBottomText = typedArray.getString(R.styleable.InverterFrequencyView_ifv_givenFrequencyBottomText);
            if (mGivenFrequencyBottomText == null) {
                mGivenFrequencyBottomText = "Given";
            }
            mActualFrequencyBottomText = typedArray.getString(R.styleable.InverterFrequencyView_ifv_actualFrequencyBottomText);
            if (mActualFrequencyBottomText == null) {
                mActualFrequencyBottomText = "Actual";
            }

            setHeaderColor(typedArray.getColor(R.styleable.InverterFrequencyView_ifv_headerTextColor, Color.BLACK));
            mTextColor = typedArray.getColor(R.styleable.InverterFrequencyView_ifv_textColor, Color.BLACK);

            setHeaderSize(typedArray.getDimensionPixelSize(R.styleable.InverterFrequencyView_ifv_headerTextSize, 10));
            mSubHeaderTextSize = typedArray.getDimensionPixelSize(R.styleable.InverterFrequencyView_ifv_subHeaderTextSize, 10);
            mFrequencyTextSize = typedArray.getDimensionPixelSize(R.styleable.InverterFrequencyView_ifv_frequencyTextSize, 10);
            mBottomLineTextSize = typedArray.getDimensionPixelSize(R.styleable.InverterFrequencyView_ifv_bottomLineTextSize, 10);

            setBackColor(typedArray.getColor(R.styleable.InverterFrequencyView_ifv_backgroundColor, Color.WHITE));

            mBoundaryColor = typedArray.getColor(R.styleable.InverterFrequencyView_ifv_boundaryColor, Color.BLACK);

            mBars[0].setColor(fetchBarColor(typedArray, R.styleable.InverterFrequencyView_ifv_givenFrequencyBarColor, Color.YELLOW));
            mBars[1].setColor(fetchBarColor(typedArray, R.styleable.InverterFrequencyView_ifv_givenFrequencyBarColor, Color.YELLOW));

            mMaxFrequency = typedArray.getInteger(R.styleable.InverterFrequencyView_ifv_maxFrequency, 100);

            typedArray.recycle();
        } else {
            mWithBackground = false;

            setHeader("Inverter");
            mSubHeaderText = "frequency, Hz";
            mGivenFrequencyText = "0.0";
            mActualFrequencyText = "0.0";
            mGivenFrequencyBottomText = "Given";
            mGivenFrequencyBottomText = "Actual";

            setHeaderColor(Color.BLACK);
            mTextColor = Color.BLACK;

            setHeaderSize(10);
            mSubHeaderTextSize = 10;
            mFrequencyTextSize = 10;
            mBottomLineTextSize = 10;

            setBackColor(Color.WHITE);

            mBoundaryColor = Color.BLACK;

            Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            bitmap.setPixel(0, 0, Color.YELLOW);
            mBars[0].setColor(new BitmapDrawable(context.getResources(), bitmap));
            mBars[1].setColor(new BitmapDrawable(context.getResources(), bitmap));

            mMaxFrequency = 100;
        }

        initBackground();
        initBoundaries();
        initHeader();
        initSubHeader();
        initGivenFrequencyLine();
        initActualFrequencyLine();
        initCurtains();
        initGivenFrequencyBottomLine();
        initActualFrequencyBottomLine();
    }

    @Override
    void initCurtains() {
        mCurtains = new Curtain[2];
        Curtain givenFrequencyCurtain = new Curtain();
        Curtain actualFrequencyCurtain = new Curtain();
        mCurtains[0] = givenFrequencyCurtain;
        mCurtains[1] = actualFrequencyCurtain;
    }

    @Override
    void initBars() {
        mBars = new Bar[2];
        Bar givenFrequencyBar = new Bar();
        Bar actualFrequencyBar = new Bar();
        mBars[0] = givenFrequencyBar;
        mBars[1] = actualFrequencyBar;
    }

    @Override
    void measureChildren() {
        measureBoundaries();
        measureHeaderText();
        measureSubHeaderText();
        measureGivenFrequencyText();
        measureActualFrequencyText();
        measureGivenFrequencyBottomText();
        measureActualFrequencyBottomText();
        measureBars();
        measureCurtains();
    }

    @Override
    void measureBars() {
        mBars[0].setBounds(getPaddingStart() + mInternalViewWidth / 8,
                mGivenFrequencyTextArea.bottom,
                getPaddingStart() + (mInternalViewWidth / 8) * 3,
                mGivenFrequencyBottomTextArea.top);
        mBars[1].setBounds(getPaddingStart() + (mInternalViewWidth / 8) * 5,
                mActualFrequencyTextArea.bottom,
                getPaddingStart() + (mInternalViewWidth / 8) * 7,
                mActualFrequencyBottomTextArea.top);
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
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        canvas.drawRect(mBoundaryRect, mBoundaryPaint);
        drawHeader(canvas);
        canvas.drawText(mSubHeaderText, 0, mSubHeaderText.length(), mSubHeaderTextX, mSubHeaderTextY, mSubHeaderTextPaint);
        canvas.drawText(mGivenFrequencyText, 0, mGivenFrequencyText.length(), mGivenFrequencyTextX, mGivenFrequencyTextY, mGivenFrequencyTextPaint);
        canvas.drawText(mActualFrequencyText, 0, mActualFrequencyText.length(), mActualFrequencyTextX, mActualFrequencyTextY, mActualFrequencyTextPaint);
        canvas.drawText(mGivenFrequencyBottomText, 0, mGivenFrequencyBottomText.length(), mGivenFrequencyBottomTextX, mGivenFrequencyBottomTextY, mGivenFrequencyBottomTextPaint);
        canvas.drawText(mActualFrequencyBottomText, 0, mActualFrequencyBottomText.length(), mActualFrequencyBottomTextX, mActualFrequencyBottomTextY, mActualFrequencyBottomTextPaint);
        drawBars(canvas);
        drawCurtains(canvas);
    }

    private void initBoundaries() {
        mBoundaryRect = new Rect();
        mBoundaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundaryPaint.setStyle(Paint.Style.STROKE);
        mBoundaryPaint.setColor(mBoundaryColor);
    }

    private void initSubHeader() {
        mSubHeaderTextArea = new Rect();
        mSubHeaderTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSubHeaderTextPaint.setColor(mTextColor);
        mSubHeaderTextPaint.setTextSize(mSubHeaderTextSize);
        mSubHeaderTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initGivenFrequencyLine() {
        mGivenFrequencyTextArea = new Rect();
        mGivenFrequencyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGivenFrequencyTextPaint.setColor(mTextColor);
        mGivenFrequencyTextPaint.setTextSize(mFrequencyTextSize);
        mGivenFrequencyTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initActualFrequencyLine() {
        mActualFrequencyTextArea = new Rect();
        mActualFrequencyTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mActualFrequencyTextPaint.setColor(mTextColor);
        mActualFrequencyTextPaint.setTextSize(mFrequencyTextSize);
        mActualFrequencyTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initGivenFrequencyBottomLine() {
        mGivenFrequencyBottomTextArea = new Rect();
        mGivenFrequencyBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGivenFrequencyBottomTextPaint.setColor(mTextColor);
        mGivenFrequencyBottomTextPaint.setTextSize(mBottomLineTextSize);
        mGivenFrequencyBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void initActualFrequencyBottomLine() {
        mActualFrequencyBottomTextArea = new Rect();
        mActualFrequencyBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mActualFrequencyBottomTextPaint.setColor(mTextColor);
        mActualFrequencyBottomTextPaint.setTextSize(mBottomLineTextSize);
        mActualFrequencyBottomTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    private void measureBoundaries() {
        mBoundaryRect.set(getPaddingStart(), getPaddingTop(), getPaddingStart() + mInternalViewWidth, getPaddingTop() + mInternalViewHeight);
        mBoundaryPaint.setStrokeWidth(convertDpToPixels(2.0f));
    }

    private void measureSubHeaderText() {
        mSubHeaderTextArea.set(mHeaderTextArea.left,
                mHeaderTextArea.bottom,
                mHeaderTextArea.right,
                (int) (getPaddingTop() + mSubHeaderTextSize + mHeaderTextArea.height() + mBoundaryPaint.getStrokeWidth() * 2));
        mSubHeaderTextPaint.getTextBounds(mSubHeaderText, 0, mSubHeaderText.length(), mTextBoundsRect);
        mSubHeaderTextX = getTextX(mSubHeaderTextArea, mTextBoundsRect);
        mSubHeaderTextY = getTextY(mSubHeaderTextArea, mTextBoundsRect);
    }

    private void measureGivenFrequencyText() {
        mGivenFrequencyTextArea.set(mSubHeaderTextArea.left,
                      mSubHeaderTextArea.bottom,
                getPaddingStart() + mInternalViewWidth / 2,
                (int) (getPaddingTop() + mFrequencyTextSize + mHeaderTextArea.height() + mSubHeaderTextArea.height() + mBoundaryPaint.getStrokeWidth() * 2));
        mGivenFrequencyTextPaint.getTextBounds(mGivenFrequencyText, 0, mGivenFrequencyText.length(), mTextBoundsRect);
        mGivenFrequencyTextX = getTextX(mGivenFrequencyTextArea, mTextBoundsRect);
        mGivenFrequencyTextY = getTextY(mGivenFrequencyTextArea, mTextBoundsRect);
    }

    private void measureActualFrequencyText() {
        mActualFrequencyTextArea.set(mGivenFrequencyTextArea.right,
                      mGivenFrequencyTextArea.top,
                getPaddingStart() + mInternalViewWidth,
                      mGivenFrequencyTextArea.bottom);
        mActualFrequencyTextPaint.getTextBounds(mActualFrequencyText, 0, mActualFrequencyText.length(), mTextBoundsRect);
        mActualFrequencyTextX = getTextX(mActualFrequencyTextArea, mTextBoundsRect);
        mActualFrequencyTextY = getTextY(mActualFrequencyTextArea, mTextBoundsRect);
    }

    private void measureGivenFrequencyBottomText() {
        mGivenFrequencyBottomTextArea.set(mGivenFrequencyTextArea.left,
                (int) (getPaddingTop() + mInternalViewHeight - mBottomLineTextSize - mBoundaryPaint.getStrokeWidth() * 3),
                mGivenFrequencyTextArea.right,
                (int) (getPaddingTop() + mInternalViewHeight - mBoundaryPaint.getStrokeWidth()));
        mGivenFrequencyBottomTextPaint.getTextBounds(mGivenFrequencyBottomText, 0, mGivenFrequencyBottomText.length(), mTextBoundsRect);
        mGivenFrequencyBottomTextX = getTextX(mGivenFrequencyBottomTextArea, mTextBoundsRect);
        mGivenFrequencyBottomTextY = getTextY(mGivenFrequencyBottomTextArea, mTextBoundsRect);
    }

    private void measureActualFrequencyBottomText() {
        mActualFrequencyBottomTextArea.set(mActualFrequencyTextArea.left,
                mGivenFrequencyBottomTextArea.top,
                mActualFrequencyTextArea.right,
                mGivenFrequencyBottomTextArea.bottom);
        mActualFrequencyBottomTextPaint.getTextBounds(mActualFrequencyBottomText, 0, mActualFrequencyBottomText.length(), mTextBoundsRect);
        mActualFrequencyBottomTextX = getTextX(mActualFrequencyBottomTextArea, mTextBoundsRect);
        mActualFrequencyBottomTextY = getTextY(mActualFrequencyBottomTextArea, mTextBoundsRect);
    }

    public void updateData(int givenFrequency, String givenFrequencyText, int actualFrequency, String actualFrequencyText) {
        givenFrequency = givenFrequency / 10;
        actualFrequency = actualFrequency / 10;

        if (!mAnimatorSet.isRunning()) {
            if (mGivenFrequencyValue != givenFrequency || mActualFrequencyValue != actualFrequency) {

                if (mGivenFrequencyValue != givenFrequency) {
                    if (givenFrequency > mMaxFrequency) {
                        mGivenFrequencyValue = mMaxFrequency;
                    } else if (givenFrequency < 0) {
                        mGivenFrequencyValue = 0;
                    } else {
                        mGivenFrequencyValue = givenFrequency;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mGivenFrequencyValue, mMaxFrequency);
                    mCurtains[0].setNewBottom(newBottom);
                    mGivenFrequencyText = givenFrequencyText;
                }

                if (mActualFrequencyValue != actualFrequency) {
                    if (actualFrequency > mMaxFrequency) {
                        mActualFrequencyValue = mMaxFrequency;
                    } else if (actualFrequency < 0) {
                        mActualFrequencyValue = 0;
                    } else {
                        mActualFrequencyValue = actualFrequency;
                    }
                    int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mActualFrequencyValue, mMaxFrequency);
                    mCurtains[1].setNewBottom(newBottom);
                    mActualFrequencyText = actualFrequencyText;
                }
                invalidate();
            }
        }
    }

    public void resetData(String givenFrequencyText, String actualFrequencyText) {
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }

        if (mGivenFrequencyValue != 0 || mActualFrequencyValue != 0) {

            if (mGivenFrequencyValue != 0) {
                mGivenFrequencyValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[0].getRect(), mBars[0].getRect(), mGivenFrequencyValue, mMaxFrequency);
                mCurtains[0].setNewBottom(newBottom);
                mGivenFrequencyText = givenFrequencyText;
            }

            if (mActualFrequencyValue != 0) {
                mActualFrequencyValue = 0;
                int newBottom = calculateCurtainBottom(mCurtains[1].getRect(), mBars[1].getRect(), mActualFrequencyValue, mMaxFrequency);
                mCurtains[1].setNewBottom(newBottom);
                mActualFrequencyText = actualFrequencyText;
            }
            invalidate();
        }
    }
}
