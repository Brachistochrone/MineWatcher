package com.artemlikhomanov.minewatcher.fragments;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Thermometer;
import com.artemlikhomanov.minewatcher.views.MotorThermometersView;

public class ThermometersListFragment extends ListAbstractFragment {

    private boolean isReset = false;

    private Thermometer[] mThermometers = Thermometer.values();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (Thermometer thermometer : mThermometers) {
            thermometer.releaseThermometersView();
        }
    }

    @Override
    void setupRecyclerView() {
        LinearLayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new ThermometersListFragment.ThermometerAdapter(mThermometers));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override //nothing
    void valuesInitialisation() {

    }

    @Override
    void updateUI(Cls450StateDataItem dataItem) {
        isReset = false;

        int duration = getResources().getInteger(R.integer.bar_chart_update_animation_time);

        for (Thermometer mThermometer : mThermometers) {
            mThermometer.setAnimDuration(duration);
        }

        mThermometers[0].updateData(dataItem.getLeftCutterMotorStatorTemperature(), dataItem.getLeftCutterMotorStatorTemperatureString(),
                dataItem.getLeftCutterMotorBearingNTemperature(), dataItem.getLeftCutterMotorBearingNTemperatureString(),
                dataItem.getLeftCutterMotorBearingPTemperature(), dataItem.getLeftCutterMotorBearingPTemperatureString(),
                dataItem.getLeftCutterGearcaseTemperature(), dataItem.getLeftCutterGearcaseTemperatureString());

        mThermometers[1].updateData(dataItem.getRightCutterMotorStatorTemperature(), dataItem.getRightCutterMotorStatorTemperatureString(),
                dataItem.getRightCutterMotorBearingNTemperature(), dataItem.getRightCutterMotorBearingNTemperatureString(),
                dataItem.getRightCutterMotorBearingPTemperature(), dataItem.getRightCutterMotorBearingPTemperatureString(),
                dataItem.getRightCutterGearcaseTemperature(), dataItem.getRightCutterGearcaseTemperatureString());

        mThermometers[2].updateData(dataItem.getLeftHaulageMotorStatorTemperature(), dataItem.getLeftHaulageMotorStatorTemperatureString(),
                dataItem.getLeftHaulageMotorBearingNTemperature(), dataItem.getLeftHaulageMotorBearingNTemperatureString(),
                dataItem.getLeftHaulageMotorBearingPTemperature(), dataItem.getLeftHaulageMotorBearingPTemperatureString(),
                dataItem.getLeftHaulageGearcaseTemperature(), dataItem.getLeftHaulageGearcaseTemperatureString());

        mThermometers[3].updateData(dataItem.getRightHaulageMotorStatorTemperature(), dataItem.getRightHaulageMotorStatorTemperatureString(),
                dataItem.getRightHaulageMotorBearingNTemperature(), dataItem.getRightHaulageMotorBearingNTemperatureString(),
                dataItem.getRightHaulageMotorBearingPTemperature(), dataItem.getRightHaulageMotorBearingPTemperatureString(),
                dataItem.getRightHaulageGearcaseTemperature(), dataItem.getRightHaulageGearcaseTemperatureString());
    }

    @Override
    void resetUI() {
        if (!isReset){
            isReset = true;

            int duration = getResources().getInteger(R.integer.bar_chart_reset_animation_time);
            String minTemperature = getString(R.string.motor_temperature_min);

            for (Thermometer thermometer : mThermometers) {
                thermometer.setAnimDuration(duration);
                thermometer.resetData(minTemperature);
            }
        }
    }

    @NonNull
    public static Fragment newInstance() {
        return new ThermometersListFragment();
    }

    private class ThermometerHolder extends RecyclerView.ViewHolder {

        private MotorThermometersView mThermometer;

        ThermometerHolder(View itemView) {
            super(itemView);
            mThermometer = itemView.findViewById(R.id.motor_thermometers);
        }

        void bindData(Thermometer thermometer) {
            mThermometer.setStrings(getString(thermometer.getThermometerNameResourceId()),
                                    getString(thermometer.getMinTemperatureResourceId()),
                                    getString(thermometer.getMinTemperatureResourceId()),
                                    getString(thermometer.getMinTemperatureResourceId()),
                                    getString(thermometer.getMinTemperatureResourceId()),
                                    getString(thermometer.getStatorBottomTextResourceId()),
                                    getString(thermometer.getBearingNBottomTextResourceId()),
                                    getString(thermometer.getBearingPBottomTextResourceId()),
                                    getString(thermometer.getGearcaseBottomTextResourceId()));

            thermometer.setThermometersView(mThermometer);
        }
    }

    private class ThermometerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Thermometer[] mThermometers;

        ThermometerAdapter(Thermometer[] thermometers) {
            mThermometers = thermometers;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_thermometer, parent, false);
            return new ThermometersListFragment.ThermometerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ((ThermometersListFragment.ThermometerHolder)holder).bindData(mThermometers[position]);
        }

        @Override
        public int getItemCount() {
            return mThermometers.length;
        }
    }
}
