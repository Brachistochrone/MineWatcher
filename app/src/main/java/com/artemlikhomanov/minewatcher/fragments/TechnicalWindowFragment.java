package com.artemlikhomanov.minewatcher.fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.BothCuttersOffTrammingLeftState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.BothCuttersOffTrammingRightState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.BothCuttersOnTrammingLeftState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.BothCuttersOnTrammingOffState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.BothCuttersOnTrammingRightState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.LeftCutterOnTrammingLeftState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.LeftCutterOnTrammingOffState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.LeftCutterOnTrammingRightState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.RightCutterOnTrammingLeftState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.RightCutterOnTrammingOffState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.RightCutterOnTrammingRightState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.ShearerState;
import com.artemlikhomanov.minewatcher.fragments.shearer_states.TurnedOffState;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.model.ShearerType;
import com.artemlikhomanov.minewatcher.views.InverterFrequencyView;
import com.artemlikhomanov.minewatcher.views.MotorCurrentAndTemperatureView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TechnicalWindowFragment extends BaseAbstractFragment {

    private static final String TAG = "TechnicalWindowFragment";
    private static final String SHEARER_TYPE_KEY = "SHEARER_TYPE_KEY";

    private String mNormalPumpMotorTemperature;
    private String mBreakdownPumpMotorTemperature;
    private String mMotorCurrentMin;
    private String mMotorTemperatureMin;
    private String mInverterFrequencyMin;

    private boolean isReset = false;
    private boolean isLocationInit = false;

    private float mShearerLocationCoef = 0.0f;

    private int mPreviousLocation;
    private int mShearerTypeInt;

    private ShearerType mShearerType;

    private ShearerState mShearerState;
    private ShearerState mTurnedOffState;
    private ShearerState mLeftCutterOnTrammingOffState;
    private ShearerState mLeftCutterOnTrammingLeftState;
    private ShearerState mLeftCutterOnTrammingRightState;
    private ShearerState mRightCutterOnTrammingOffState;
    private ShearerState mRightCutterOnTrammingLeftState;
    private ShearerState mRightCutterOnTrammingRightState;
    private ShearerState mBothCuttersOnTrammingOffState;
    private ShearerState mBothCuttersOnTrammingLeftState;
    private ShearerState mBothCuttersOnTrammingRightState;
    private ShearerState mBothCuttersOffTrammingLeftState;
    private ShearerState mBothCuttersOffTrammingRightState;

    private AnimationDrawable mLeftCutterAnimationDrawable;
    private AnimationDrawable mRightCutterAnimationDrawable;
    private AnimationDrawable mClockwiseStarsAnimationDrawable;
    private AnimationDrawable mAnticlockwiseStarsAnimationDrawable;

    @BindView(R.id.left_cutter_motor_current_and_temperature)
              MotorCurrentAndTemperatureView mLeftCutterMotorCurrentAndTemperatureView;
    @BindView(R.id.left_driving_motor_current_and_temperature)
              MotorCurrentAndTemperatureView mLeftHaulageMotorCurrentAndTemperatureView;
    @BindView(R.id.right_cutter_motor_current_and_temperature)
              MotorCurrentAndTemperatureView mRightCutterMotorCurrentAndTemperatureView;
    @BindView(R.id.right_driving_motor_current_and_temperature)
              MotorCurrentAndTemperatureView mRightHaulageMotorCurrentAndTemperatureView;
    @BindView(R.id.oil_pump_motor_current_and_temperature)
              MotorCurrentAndTemperatureView mPumpMotorCurrentAndTemperatureView;
    @BindView(R.id.inverter_frequency_state)
              InverterFrequencyView mInverterFrequencyStateView;

    @BindView(R.id.left_cutter_image_view)
              ImageView mLeftCutterImageView;
    @BindView(R.id.right_cutter_image_view)
              ImageView mRightCutterImageView;
    @BindView(R.id.shearer_image_view)
              ImageView mShearerImageView;

    @BindView(R.id.arrow_leftward_motion_image_view)
              ImageView mArrowLeftwardMotionImageView;
    @BindView(R.id.arrow_rightward_motion_image_view)
              ImageView mArrowRightwardMotionImageView;
    @BindView(R.id.position_longwall_image_view)
              ImageView mLongwallRulerImageView;
    @BindView(R.id.position_shearer_image_view)
              ImageView mShearerThumbnailImageView;

    @BindView(R.id.velocity_value_text_view)
              TextView mVelocityValueTextView;
    @BindView(R.id.code_number_text_view)
              TextView mCodeDisplayTextView;
    @BindView(R.id.position_distance_value_text_view)
              TextView mShearerLocationDistanceTextView;
    @BindView(R.id.position_roof_support_number_text_view)
              TextView mShearerLocationRoofSupportNumberTextView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SHEARER_TYPE_KEY, mShearerTypeInt);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractExtraValues(savedInstanceState);
        valuesInitialisation();
    }

    @Override
    public void onStop() {
        super.onStop();
        isLocationInit = false;
    }

    @Override
    public void onDestroyView() {
        isReset = false;
        turnOffShearer();
        super.onDestroyView();
    }

    @Override
    void initialiseUI(View view) {

        ButterKnife.bind(this, view);

        shearerInitialisation();
        velocityAndDirectionInitialisation();
    }

    @Override
    void hook() {
        mNormalPumpMotorTemperature = getResources().getString(R.string.normal_pump_motor_temperature);
        mBreakdownPumpMotorTemperature = getResources().getString(R.string.breakdown_pump_motor_temperature);
        mMotorCurrentMin = getResources().getString(R.string.motor_current_min);
        mMotorTemperatureMin = getResources().getString(R.string.motor_temperature_min);
        mInverterFrequencyMin = getResources().getString(R.string.inverter_frequency_value_min);
    }

    @Override
    void valuesInitialisation() {
        switch (mShearerTypeInt) {
            case Const.SHEARER_TYPE_CLS450_first:
                mShearerType = ShearerType.CLS450_first;
                break;
            case Const.SHEARER_TYPE_CLS450:
                mShearerType = ShearerType.CLS450;
                break;
            case Const.SHEARER_TYPE_KDK500:
                mShearerType = ShearerType.KDK500;
                break;
        }

        mTurnedOffState = new TurnedOffState(this);
        mLeftCutterOnTrammingOffState = new LeftCutterOnTrammingOffState(this);
        mLeftCutterOnTrammingLeftState = new LeftCutterOnTrammingLeftState(this);
        mLeftCutterOnTrammingRightState = new LeftCutterOnTrammingRightState(this);
        mRightCutterOnTrammingOffState = new RightCutterOnTrammingOffState(this);
        mRightCutterOnTrammingLeftState = new RightCutterOnTrammingLeftState(this);
        mRightCutterOnTrammingRightState = new RightCutterOnTrammingRightState(this);
        mBothCuttersOnTrammingOffState = new BothCuttersOnTrammingOffState(this);
        mBothCuttersOnTrammingLeftState = new BothCuttersOnTrammingLeftState(this);
        mBothCuttersOnTrammingRightState = new BothCuttersOnTrammingRightState(this);
        mBothCuttersOffTrammingLeftState = new BothCuttersOffTrammingLeftState(this);
        mBothCuttersOffTrammingRightState = new BothCuttersOffTrammingRightState(this);

        mShearerState = mTurnedOffState;
    }

    @Override
    void updateUI(Cls450StateDataItem dataItem) {
        if (!isLocationInit){
            shearerLocationInitialisation();
            isLocationInit = true;
        }

        isReset = false;
        updateMotorBarCharts(dataItem);
        updateInverterFrequencyBarCharts(dataItem);
        updateVelocityAndDirection(dataItem);
        updateCodeDisplay(dataItem);
        updateShearerLocation(dataItem);
        updateShearer(dataItem);
    }

    @Override
    void resetUI() {
        if (!isReset){
            resetMotorBarCharts();
            resetInverterFrequencyBarCharts();
            resetVelocityAndDirection();
            resetCodeDisplay();
            resetShearerLocation();
            turnOffShearer();
            isReset = true;
        }
    }

    @Override
    int getLayoutResId() {
        return R.layout.fragment_technical_window;
    }

    /*Метод инициализации направления движения и скорости*/
    private void velocityAndDirectionInitialisation() {
        mArrowLeftwardMotionImageView.setVisibility(View.INVISIBLE);
        mArrowRightwardMotionImageView.setVisibility(View.INVISIBLE);
        mVelocityValueTextView.setText(R.string.velocity_value_example);
    }

    private void shearerLocationInitialisation() {
        /*Высчитать сколькло в 1 метре пикселей*/
        mShearerThumbnailImageView.setImageResource(mShearerType.getShearerPositionImage());
        mShearerLocationCoef = mLongwallRulerImageView.getWidth() / Const.LONGWALL_LENGTH;
        mPreviousLocation = 0;
    }

    /*Метод инициализации изображения комбайна*/
    private void shearerInitialisation() {
        mLeftCutterImageView.setImageResource(mShearerType.getLeftCutterGrey());
        mRightCutterImageView.setImageResource(mShearerType.getRightCutterGrey());
        mShearerImageView.setImageResource(mShearerType.getShearerGrey());
    }

    /*Метод обновления токовых столбцов*/
    private void updateMotorBarCharts(Cls450StateDataItem dataItem) {

        boolean isPumpMotorBreakDownTemperature = dataItem.getPumpMotorBreakDownTemperature();

        int duration = getResources().getInteger(R.integer.bar_chart_update_animation_time);

        mLeftCutterMotorCurrentAndTemperatureView.setDuration(duration);
        mLeftHaulageMotorCurrentAndTemperatureView.setDuration(duration);
        mRightCutterMotorCurrentAndTemperatureView.setDuration(duration);
        mRightHaulageMotorCurrentAndTemperatureView.setDuration(duration);
        mPumpMotorCurrentAndTemperatureView.setDuration(duration);

        mLeftCutterMotorCurrentAndTemperatureView.updateData(
                dataItem.getLeftCutterMotorCurrent(),
                dataItem.getLeftCutterMotorCurrentString(),
                dataItem.getLeftCutterMotorStatorTemperature(),
                dataItem.getLeftCutterMotorStatorTemperatureString());
        mLeftHaulageMotorCurrentAndTemperatureView.updateData(
                dataItem.getLeftHaulageMotorCurrent(),
                dataItem.getLeftHaulageMotorCurrentString(),
                dataItem.getLeftHaulageMotorStatorTemperature(),
                dataItem.getLeftHaulageMotorStatorTemperatureString());
        mRightCutterMotorCurrentAndTemperatureView.updateData(
                dataItem.getRightCutterMotorCurrent(),
                dataItem.getRightCutterMotorCurrentString(),
                dataItem.getRightCutterMotorStatorTemperature(),
                dataItem.getRightCutterMotorStatorTemperatureString());
        mRightHaulageMotorCurrentAndTemperatureView.updateData(
                dataItem.getRightHaulageMotorCurrent(),
                dataItem.getRightHaulageMotorCurrentString(),
                dataItem.getRightHaulageMotorStatorTemperature(),
                dataItem.getRightHaulageMotorStatorTemperatureString());

        if (isPumpMotorBreakDownTemperature){
            mPumpMotorCurrentAndTemperatureView.updateData(
                    dataItem.getPumpMotorCurrent(),
                    dataItem.getPumpMotorCurrentString(),
                    Const.BREAKDOWN_PUMP_MOTOR_TEMPERATURE,
                    mBreakdownPumpMotorTemperature);
        } else {
            mPumpMotorCurrentAndTemperatureView.updateData(
                    dataItem.getPumpMotorCurrent(),
                    dataItem.getPumpMotorCurrentString(),
                    Const.NORMAL_PUMP_MOTOR_TEMPERATURE,
                    mNormalPumpMotorTemperature);
        }
    }

    /*Метод обнуления токовых столбцов*/
    private void resetMotorBarCharts() {
        int duration = getResources().getInteger(R.integer.bar_chart_reset_animation_time);

        mLeftCutterMotorCurrentAndTemperatureView.setDuration(duration);
        mLeftHaulageMotorCurrentAndTemperatureView.setDuration(duration);
        mRightCutterMotorCurrentAndTemperatureView.setDuration(duration);
        mRightHaulageMotorCurrentAndTemperatureView.setDuration(duration);
        mPumpMotorCurrentAndTemperatureView.setDuration(duration);

        mLeftCutterMotorCurrentAndTemperatureView.resetData(mMotorCurrentMin, mMotorTemperatureMin);
        mLeftHaulageMotorCurrentAndTemperatureView.resetData(mMotorCurrentMin, mMotorTemperatureMin);
        mRightCutterMotorCurrentAndTemperatureView.resetData(mMotorCurrentMin, mMotorTemperatureMin);
        mRightHaulageMotorCurrentAndTemperatureView.resetData(mMotorCurrentMin, mMotorTemperatureMin);
        mPumpMotorCurrentAndTemperatureView.resetData(mMotorCurrentMin, mMotorTemperatureMin);
    }

    /*Метод обновления частотный столбцов*/
    private void updateInverterFrequencyBarCharts(Cls450StateDataItem dataItem) {
        int duration = getResources().getInteger(R.integer.bar_chart_update_animation_time);

        mInverterFrequencyStateView.setDuration(duration);

        mInverterFrequencyStateView.updateData(
                dataItem.getGivenInverterFrequency(),
                dataItem.getGivenInverterFrequencyString(),
                dataItem.getActualInverterFrequency(),
                dataItem.getActualInverterFrequencyString());
    }

    /*Метод обнуления частотный столбцов*/
    private void resetInverterFrequencyBarCharts() {
        int duration = getResources().getInteger(R.integer.bar_chart_reset_animation_time);

        mInverterFrequencyStateView.setDuration(duration);

        mInverterFrequencyStateView.resetData(mInverterFrequencyMin, mInverterFrequencyMin);
    }

    /*Метод обновления направления движения и скорости*/
    private void updateVelocityAndDirection(Cls450StateDataItem dataItem) {
        /*проверить, включена ли подача*/
        if (dataItem.isTramOn()) {
            /*проверить в какую сторону подача*/
            if (dataItem.isTrammingLeft()) {
                if (mArrowLeftwardMotionImageView.getVisibility() == View.INVISIBLE){

                    mArrowLeftwardMotionImageView.setVisibility(View.VISIBLE);
                    mArrowRightwardMotionImageView.setVisibility(View.INVISIBLE);

                    /*Задать цвет стрелки вручную если API < 21, т.к. не подтягивает цвет из векторной картинки*/
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        mArrowLeftwardMotionImageView.setColorFilter(getResources().getColor(R.color.colorGreenForArrows));
                    }
                }
            } else {
                if (mArrowRightwardMotionImageView.getVisibility() == View.INVISIBLE) {

                    mArrowRightwardMotionImageView.setVisibility(View.VISIBLE);
                    mArrowLeftwardMotionImageView.setVisibility(View.INVISIBLE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        mArrowRightwardMotionImageView.setColorFilter(getResources().getColor(R.color.colorGreenForArrows));
                    }
                }
            }
            /*вычислить скорость комбайна*/
            String velocity = "";
            switch (mShearerTypeInt) {
                case Const.SHEARER_TYPE_CLS450_first:
                    velocity = String.valueOf(dataItem.getActualInverterFrequency() * Const.VELOCITY_CALC_COEF_CLS450_1);
                    break;
                case Const.SHEARER_TYPE_CLS450:
                    velocity = String.valueOf(dataItem.getActualInverterFrequency() * Const.VELOCITY_CALC_COEF_CLS450);
                    break;
                case Const.SHEARER_TYPE_KDK500:
                    velocity = String.valueOf(dataItem.getActualInverterFrequency() * Const.VELOCITY_CALC_COEF_KDK500);
                    break;
            }

            if (velocity.length() > 4) {
                velocity = new StringBuilder().append(velocity).substring(0, 4);
            }

            setText(mVelocityValueTextView, velocity);
        } else {
            resetVelocityAndDirection();
        }
    }

    /*Метод обнуления направления движения и скорости*/
    private void resetVelocityAndDirection() {
        if (mArrowLeftwardMotionImageView.getVisibility() == View.VISIBLE || mArrowRightwardMotionImageView.getVisibility() == View.VISIBLE){
            mArrowLeftwardMotionImageView.setVisibility(View.INVISIBLE);
            mArrowRightwardMotionImageView.setVisibility(View.INVISIBLE);
            setText(mVelocityValueTextView, getString(R.string.velocity_value_example));
        }
    }

    /*Метод обновляет индикатор кодов*/
    private void updateCodeDisplay(Cls450StateDataItem dataItem) {
        setText(mCodeDisplayTextView, dataItem.getIndicationCode());
    }

    /*Метод обнуляет индикатор кодов*/
    private void resetCodeDisplay() {
        setText(mCodeDisplayTextView, "");
    }

    /*Метод обновляет положение комбайна*/
    private void updateShearerLocation(Cls450StateDataItem dataItem) {

        animateShearerTramming(mLongwallRulerImageView,
                mShearerThumbnailImageView,
                dataItem.getShearerLocation(),
                mPreviousLocation,
                Const.BAR_CHART_UPDATE_ANIMATION_TIME,
                mShearerLocationCoef,
                dataItem.isLongwallStartRight());

        displayShearerLocation(dataItem.getShearerLocation(), dataItem.isLongwallStartRight());

        mPreviousLocation = dataItem.getShearerLocation();
    }

    /*Метод обнуляет положение комбайна*/
    private void resetShearerLocation() {

        animateShearerTramming(mLongwallRulerImageView,
                mShearerThumbnailImageView,
                0,
                mPreviousLocation,
                Const.BAR_CHART_RESET_ANIMATION_TIME,
                mShearerLocationCoef,
                null);

        displayShearerLocation(0, null);

        mPreviousLocation = 0;
    }

    /*Метод обновляет вид комбайна*/
    private void updateShearer(Cls450StateDataItem dataItem) {
        if (dataItem.isShearerOn()) {

            if (dataItem.isFirstMainBreakerOn()) {
                startLeftCutter();
            }

            if (dataItem.isSecondMainBreakerOn()) {
                startRightCutter();
            }

            if (dataItem.isTramOn()) {
                if (dataItem.isTrammingLeft()){
                    startTrammingLeft();
                } else {
                    startTrammingRight();
                }
            } else {
                turnOffTramming();
            }

        } else {
            turnOffShearer();
        }
    }

    /*Метод делает комбайн цветным*/
    public void startShearer() {
        /*Сделать комбайн цветным*/
        mShearerImageView.setImageDrawable(null);
        mShearerImageView.setImageResource(mShearerType.getShearerColored());
        /*Сделать просто цветным*/
        mLeftCutterImageView.setImageDrawable(null);
        mLeftCutterImageView.setImageResource(mShearerType.getLeftCutterColored());
        /*Сделать просто цветным*/
        mRightCutterImageView.setImageDrawable(null);
        mRightCutterImageView.setImageResource(mShearerType.getRightCutterColored());
    }

    /*Метод останавливает анимации и выключает комбайн*/
    public void stopShearerAnimations() {

        if (mLeftCutterAnimationDrawable != null) {
            mLeftCutterAnimationDrawable.stop();
        }

        if (mRightCutterAnimationDrawable != null) {
            mRightCutterAnimationDrawable.stop();
        }

        mLeftCutterAnimationDrawable = null;
        mRightCutterAnimationDrawable = null;

        mLeftCutterImageView.setImageDrawable(null);
        mRightCutterImageView.setImageDrawable(null);

        stopStarsAnimation();
        mShearerImageView.setImageDrawable(null);

        mLeftCutterImageView.setImageResource(mShearerType.getLeftCutterGrey());
        mRightCutterImageView.setImageResource(mShearerType.getRightCutterGrey());
        mShearerImageView.setImageResource(mShearerType.getShearerGrey());
    }

    /*Метод останавливает анимации звезд*/
    public void stopStarsAnimation() {
        if (mClockwiseStarsAnimationDrawable != null) {
            mClockwiseStarsAnimationDrawable.stop();
            mClockwiseStarsAnimationDrawable = null;
            mShearerImageView.setImageDrawable(null);
            mShearerImageView.setImageResource(mShearerType.getShearerColored());
        } else if (mAnticlockwiseStarsAnimationDrawable != null){
            mAnticlockwiseStarsAnimationDrawable.stop();
            mAnticlockwiseStarsAnimationDrawable = null;
            mShearerImageView.setImageDrawable(null);
            mShearerImageView.setImageResource(mShearerType.getShearerColored());
        }
    }

    /*Метод запускает левый шнек*/
    public void startLeftCutterAnimation() {
        /*задать список кадров для анимации*/
        mLeftCutterImageView.setImageDrawable(null);
        mLeftCutterImageView.setBackgroundResource(mShearerType.getLeftCutterAnimation());
        /*получить обьекты AnimationDrawable для управления анимацией*/
        mLeftCutterAnimationDrawable = (AnimationDrawable) mLeftCutterImageView.getBackground();

        mLeftCutterAnimationDrawable.start();
    }

    /*Метод запускает правый шнек*/
    public void startRightCutterAnimation() {
        /*задать список кадров для анимации*/
        mRightCutterImageView.setImageDrawable(null);
        mRightCutterImageView.setBackgroundResource(mShearerType.getRightCutterAnimation());
        /*получить обьекты AnimationDrawable для управления анимацией*/
        mRightCutterAnimationDrawable = (AnimationDrawable) mRightCutterImageView.getBackground();

        mRightCutterAnimationDrawable.start();
    }

    /*Метод запускает звезды против часовой*/
    public void startAnticlockwiseStarsAnimation() {

        if (mAnticlockwiseStarsAnimationDrawable == null) {
            /*задать список кадров для анимации*/
            mClockwiseStarsAnimationDrawable = null;
            mShearerImageView.setImageDrawable(null);
            mShearerImageView.setBackgroundResource(mShearerType.getStarsAnticlockwiseAnimation());
            /*получить обьекты AnimationDrawable для управления анимацией*/
            mAnticlockwiseStarsAnimationDrawable = (AnimationDrawable) mShearerImageView.getBackground();

            mAnticlockwiseStarsAnimationDrawable.start();
        }
    }

    /*Метод запускает звезды по часовой*/
    public void startClockwiseStarsAnimation() {

        if (mClockwiseStarsAnimationDrawable == null) {
            /*задать список кадров для анимации*/
            mAnticlockwiseStarsAnimationDrawable = null;
            mShearerImageView.setImageDrawable(null);
            mShearerImageView.setBackgroundResource(mShearerType.getStarsClockwiseAnimation());
        /*получить обьекты AnimationDrawable для управления анимацией*/
            mClockwiseStarsAnimationDrawable = (AnimationDrawable) mShearerImageView.getBackground();

            mClockwiseStarsAnimationDrawable.start();
        }
    }

    public void turnOffShearer() {
        mShearerState.turnOffShearer();
    }

    public void startLeftCutter() {
        mShearerState.startLeftCutter();
    }

    public void startRightCutter() {
        mShearerState.startRightCutter();
    }

    public void turnOffTramming() {
        mShearerState.turnOffTramming();
    }

    public void startTrammingLeft() {
        mShearerState.startTrammingLeft();
    }

    public void startTrammingRight() {
        mShearerState.startTrammingRight();
    }

    public void setShearerState(ShearerState state) {
        mShearerState = state;
    }

    public ShearerState getTurnedOffState() {
        return mTurnedOffState;
    }

    public ShearerState getLeftCutterOnTrammingOffState() {
        return mLeftCutterOnTrammingOffState;
    }

    public ShearerState getLeftCutterOnTrammingLeftState() {
        return mLeftCutterOnTrammingLeftState;
    }

    public ShearerState getLeftCutterOnTrammingRightState() {
        return mLeftCutterOnTrammingRightState;
    }

    public ShearerState getRightCutterOnTrammingOffState() {
        return mRightCutterOnTrammingOffState;
    }

    public ShearerState getRightCutterOnTrammingLeftState() {
        return mRightCutterOnTrammingLeftState;
    }

    public ShearerState getRightCutterOnTrammingRightState() {
        return mRightCutterOnTrammingRightState;
    }

    public ShearerState getBothCuttersOnTrammingOffState() {
        return mBothCuttersOnTrammingOffState;
    }

    public ShearerState getBothCuttersOnTrammingLeftState() {
        return mBothCuttersOnTrammingLeftState;
    }

    public ShearerState getBothCuttersOnTrammingRightState() {
        return mBothCuttersOnTrammingRightState;
    }

    public ShearerState getBothCuttersOffTrammingLeftState() {
        return mBothCuttersOffTrammingLeftState;
    }

    public ShearerState getBothCuttersOffTrammingRightState() {
        return mBothCuttersOffTrammingRightState;
    }

    /*Метод анимирует передвижение комбайна по линейке*/
    private void animateShearerTramming(ImageView ruler, ImageView shearer, int newLocation, int oldLocation, int duration, float coef, Boolean isLongwallStartRight){

        if (newLocation != oldLocation){
            if (isLongwallStartRight != null) {
                /*если начало справа*/
                if (isLongwallStartRight) {
                    /*Высчитать новые координаты*/
                    int finish = ruler.getRight() - (int)(newLocation * coef);
                    /*Анимировать передвижение комбайна на новое место*/
                    shearer.animate()
                            .x(finish - shearer.getWidth())
                            .setDuration(duration)
                            .setInterpolator(new AccelerateDecelerateInterpolator());
                } else {
                    /*Высчитать новые координаты*/
                    int finish = ruler.getLeft() + (int)(newLocation * coef);
                    /*Анимировать передвижение комбайна на новое место*/
                    shearer.animate()
                            .x(finish)
                            .setDuration(duration)
                            .setInterpolator(new AccelerateDecelerateInterpolator());
                }
            } else {
                int finish = ruler.getLeft();
                shearer.animate()
                        .x(finish)
                        .setDuration(duration)
                        .setInterpolator(new AccelerateDecelerateInterpolator());
            }
        }
    }

    /*Метод вычисляет и отображает положение комбайна*/
    private void displayShearerLocation(int location, Boolean isLongwallStartRight) {

        if (isLongwallStartRight != null) {
            if (isLongwallStartRight) {
                /*Отобразить дистанцию в метрах*/
                String str = String.valueOf(location + Const.SHEARER_LENGTH) + "-" + location;
                setText(mShearerLocationDistanceTextView, str);
                /*Отобразить номер секции*/
                str = String.valueOf((int)((location + Const.SHEARER_LENGTH) / Const.WIDTH_OF_ROOF_SUPPORT)) + "-" +
                                     (int)(location / Const.WIDTH_OF_ROOF_SUPPORT);
                setText(mShearerLocationRoofSupportNumberTextView, str);
            } else {
                /*Отобразить дистанцию в метрах*/
                String str = String.valueOf(location) + "-" + (location + Const.SHEARER_LENGTH);
                setText(mShearerLocationDistanceTextView, str);
                /*Отобразить номер секции*/
                str = String.valueOf((int)(location / Const.WIDTH_OF_ROOF_SUPPORT)) + "-" +
                                     (int)((location + Const.SHEARER_LENGTH) / Const.WIDTH_OF_ROOF_SUPPORT);
                setText(mShearerLocationRoofSupportNumberTextView, str);
            }
        } else {
            setText(mShearerLocationDistanceTextView, "");
            setText(mShearerLocationRoofSupportNumberTextView, "");
        }
    }

    private void extractExtraValues(Bundle savedState) {
        if (savedState != null) {
            mShearerTypeInt = savedState.getInt(SHEARER_TYPE_KEY);
        } else {
            Bundle args = getArguments();
            mShearerTypeInt = args.getInt(SHEARER_TYPE_KEY);
        }
    }

    @NonNull
    public static Fragment newInstance(int shearerType) {
        Fragment fragment = new TechnicalWindowFragment();
        Bundle args = new Bundle();
        args.putInt(SHEARER_TYPE_KEY, shearerType);
        fragment.setArguments(args);
        return fragment;
    }
}
