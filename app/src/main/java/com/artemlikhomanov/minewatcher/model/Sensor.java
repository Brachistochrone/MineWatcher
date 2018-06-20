package com.artemlikhomanov.minewatcher.model;

import android.support.annotation.StringRes;

import com.artemlikhomanov.minewatcher.R;

public enum Sensor {
    HIGH_VOLTAGE(R.string.high_voltage_name, R.string.voltage_min_value),
    HYDRAULIC_FLUID_TEMPERATURE(R.string.hydraulic_fluid_temperature_name, R.string.motor_temperature_min),
    HYDRAULIC_FLUID_LEVEL(R.string.hydraulic_fluid_level_name, R.string.hydraulic_fluid_level_min_value),
    PUMP_HYDRAULIC_FLUID_PRESSURE(R.string.pump_hydraulic_fluid_pressure_name, R.string.pressure_min_value),
    BRAKES_HYDRAULIC_FLUID_PRESSURE(R.string.brakes_hydraulic_fluid_pressure_name, R.string.pressure_min_value),
    DUST_REDUCER_WATER_PRESSURE(R.string.dust_reducer_water_pressure_name, R.string.pressure_min_value),
    SHEARER_WATER_CONSUMPTION(R.string.shearer_water_consumption_name, R.string.water_consumption_min_value),
    CUTTING_SYSTEM_WATER_CONSUMPTION(R.string.cutting_system_water_consumption_name, R.string.water_consumption_min_value),
    HAULAGE_SYSTEM_WATER_CONSUMPTION(R.string.haulage_system_water_consumption_name, R.string.water_consumption_min_value),
    ;

    private final int mSensorNameResourceId;
    private final int mMinValueResourceId;
    private String mValue;

    Sensor(@StringRes int sensorNameResourceId, @StringRes int minValueResourceId) {
        mSensorNameResourceId = sensorNameResourceId;
        mMinValueResourceId = minValueResourceId;
    }

    @StringRes
    public int getSensorNameResourceId() {
        return mSensorNameResourceId;
    }

    @StringRes
    public int getMinValueResourceId() {
        return mMinValueResourceId;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
