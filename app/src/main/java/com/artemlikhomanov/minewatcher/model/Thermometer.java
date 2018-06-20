package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.StringRes;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.views.MotorThermometersView;

public enum Thermometer {
    LEFT_CUTTER_MOTOR(R.string.left_cutter_motor_full_name,
                      R.string.motor_temperature_min,
                      R.string.stator,
                      R.string.bearing_n,
                      R.string.bearing_p,
                      R.string.gearcase),
    RIGHT_CUTTER_MOTOR(R.string.right_cutter_motor_full_name,
                       R.string.motor_temperature_min,
                       R.string.stator,
                       R.string.bearing_n,
                       R.string.bearing_p,
                       R.string.gearcase),
    LEFT_HAULAGE_MOTOR(R.string.left_haulage_motor_full_name,
                       R.string.motor_temperature_min,
                       R.string.stator,
                       R.string.bearing_n,
                       R.string.bearing_p,
                       R.string.gearcase),
    RIGHT_HAULAGE_MOTOR(R.string.right_haulage_motor_full_name,
                        R.string.motor_temperature_min,
                        R.string.stator,
                        R.string.bearing_n,
                        R.string.bearing_p,
                        R.string.gearcase),
    ;

    private final int mThermometerNameResourceId;
    private final int mMinTemperatureResourceId;
    private final int mStatorBottomTextResourceId;
    private final int mBearingNBottomTextResourceId;
    private final int mBearingPBottomTextResourceId;
    private final int mGearcaseBottomTextResourceId;
    private MotorThermometersView mThermometersView;

    Thermometer(@StringRes int thermometerNameResourceId, @StringRes int minTemperatureResourceId,
                @StringRes int statorBottomTextResourceId, @StringRes int bearingNBottomTextResourceId,
                @StringRes int bearingPBottomTextResourceId, @StringRes int gearcaseBottomTextResourceId) {
        mThermometerNameResourceId = thermometerNameResourceId;
        mMinTemperatureResourceId = minTemperatureResourceId;
        mStatorBottomTextResourceId = statorBottomTextResourceId;
        mBearingNBottomTextResourceId = bearingNBottomTextResourceId;
        mBearingPBottomTextResourceId = bearingPBottomTextResourceId;
        mGearcaseBottomTextResourceId = gearcaseBottomTextResourceId;
    }

    @StringRes
    public int getThermometerNameResourceId() {
        return mThermometerNameResourceId;
    }

    @StringRes
    public int getMinTemperatureResourceId() {
        return mMinTemperatureResourceId;
    }

    @StringRes
    public int getStatorBottomTextResourceId() {
        return mStatorBottomTextResourceId;
    }

    @StringRes
    public int getBearingNBottomTextResourceId() {
        return mBearingNBottomTextResourceId;
    }

    @StringRes
    public int getBearingPBottomTextResourceId() {
        return mBearingPBottomTextResourceId;
    }

    @StringRes
    public int getGearcaseBottomTextResourceId() {
        return mGearcaseBottomTextResourceId;
    }

    public void setThermometersView(MotorThermometersView view) {
        mThermometersView = view;
    }

    public void releaseThermometersView() {
        mThermometersView = null;
    }

    public void updateData(int statorTemperature, String statorTemperatureString, int bearingNTemperature, String bearingNTemperatureString,
                           int bearingPTemperature, String bearingPTemperatureString, int gearcaseTemperature, String gearcaseTemperatureString) {
        if(mThermometersView != null) {
            mThermometersView.updateData(statorTemperature, statorTemperatureString, bearingNTemperature, bearingNTemperatureString,
                    bearingPTemperature, bearingPTemperatureString, gearcaseTemperature, gearcaseTemperatureString);
        }
    }

    public void resetData(String minTemperatureString) {
        if (mThermometersView != null) {
            mThermometersView.resetData(minTemperatureString);
        }
    }

    public void setAnimDuration(int duration) {
        if (mThermometersView != null) {
            mThermometersView.setDuration(duration);
        }
    }
}
