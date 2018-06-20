package com.artemlikhomanov.minewatcher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Sensor;

import java.util.ArrayList;

public class SensorsListFragment extends ListAbstractFragment {

    private boolean isReset = false;

    private int mCounter = 4;

    private Sensor[] mSensors = Sensor.values();

    private ArrayList<String> mParameters = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        valuesInitialisation();
    }

    @Override
    void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new SensorsListFragment.SensorAdapter(mSensors));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    void updateUI(Cls450StateDataItem dataItem) {
        isReset = false;
        mCounter++;
        if (mCounter == 6) {
            mSensors[0].setValue(dataItem.getHighVoltageValueString());
            mSensors[1].setValue(dataItem.getHydraulicFluidTemperatureString());
            mSensors[2].setValue(dataItem.getHydraulicFluidLevelString());
            mSensors[3].setValue(dataItem.getPumpHydraulicFluidPressureString());
            mSensors[4].setValue(dataItem.getBrakesHydraulicFluidPressureString());
            mSensors[5].setValue(dataItem.getDustReducerWaterPressureString());
            mSensors[6].setValue(dataItem.getShearerWaterConsumptionString());
            mSensors[7].setValue(dataItem.getCuttingSystemWaterConsumptionString());
            mSensors[8].setValue(dataItem.getHaulageSystemWaterConsumptionString());

            mRecyclerView.getAdapter().notifyDataSetChanged();

            mCounter = 0;
        }
    }

    @Override
    void resetUI() {
        if (!isReset){
            isReset = true;
            mCounter = 0;
            for (Sensor sensor : mSensors) {
                sensor.setValue(null);
            }
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    void valuesInitialisation() {
        for (Sensor sensor : mSensors) {
            mParameters.add(getString(sensor.getSensorNameResourceId()));
        }
    }

    @NonNull
    public static Fragment newInstance() {
        return new SensorsListFragment();
    }

    private class SensorHolder extends RecyclerView.ViewHolder {

        private TextView mParameter;
        private TextView mValue;

        SensorHolder(View itemView) {
            super(itemView);
            mParameter = itemView.findViewById(R.id.parameter_text_view);
            mValue = itemView.findViewById(R.id.value_text_view);
        }

        void bindData(Sensor sensor, int position) {
            mParameter.setText(mParameters.get(position));
            if (sensor.getValue() == null) {
                mValue.setText(sensor.getMinValueResourceId());
            } else {
                mValue.setText(sensor.getValue());
            }
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Sensor[] mSensors;

        SensorAdapter(Sensor[] sensors) {
            mSensors = sensors;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_sensor, parent, false);
            return new SensorHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((SensorsListFragment.SensorHolder)holder).bindData(mSensors[position], position);
        }

        @Override
        public int getItemCount() {
            return mSensors.length;
        }
    }
}
