package com.artemlikhomanov.minewatcher.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagram;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramDot;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramSegment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class LocationDiagramFragment extends BaseAbstractFragment {

    private static final String TAG = "ShearerLocationFragment";
    private static final String PRIMARY_KEY_EXTRA = "PRIMARY_KEY_EXTRA";

    @BindView(R.id.location_diagram_line_chart)
    LineChart mShearerLocationDiagram;

    private Realm mDataBase;
    private RealmResults<LocationDiagram> mDiagrams;

    private long mPrimaryKey;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PRIMARY_KEY_EXTRA, mPrimaryKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mPrimaryKey = savedInstanceState.getLong(PRIMARY_KEY_EXTRA);
        } else {
            if (getArguments() != null) {
                mPrimaryKey = getArguments().getLong(PRIMARY_KEY_EXTRA);
            }
        }
    }

    @Override
    public void onDestroyView() {
        mDataBase.close();
        super.onDestroyView();
    }

    @Override//nothing
    void updateUI(Cls450StateDataItem dataItem) {

    }

    @Override//nothing
    void resetUI() {

    }

    @Override
    void initialiseUI(View view) {

        ButterKnife.bind(this, view);

        diagramInitialisation();
        drawDiagram();
    }

    @Override
    int getLayoutResId() {
        return R.layout.fragment_location_diagram;
    }

    @Override//nothing
    void valuesInitialisation() {

    }

    /*метод настройки диаграммы*/
    private void diagramInitialisation() {
        XAxis xAxis = mShearerLocationDiagram.getXAxis();
        YAxis yAxisLeft = mShearerLocationDiagram.getAxisLeft();
        /*установить минимальные и максимальные значения для осей*/
        xAxis.setAxisMinimum(Const.LOCATION_DIAGRAM_X_AXIS_MIN_VALUE);
        xAxis.setAxisMaximum(Const.LOCATION_DIAGRAM_X_AXIS_MAX_VALUE);
        yAxisLeft.setAxisMinimum(Const.LOCATION_DIAGRAM_Y_AXIS_MIN_VALUE);
        yAxisLeft.setAxisMaximum(Const.LOCATION_DIAGRAM_Y_AXIS_MAX_VALUE);

        YAxis yAxisRight = mShearerLocationDiagram.getAxisRight();
        /*отключить правую Y ось*/
        yAxisRight.setEnabled(false);
        /*задать ValueFormatter для X оси для изменения отображаемых значений*/
        xAxis.setValueFormatter(new XAxisValueFormatter(Const.VALUES_FOR_X_AXIS));

        /*создать настроить и задать описание для диаграммы*/
        Description description = new Description();
        description.setText(getResources().getString(R.string.location_diagram));
        description.setTextSize(Const.LOCATION_DIAGRAM_DESCRIPTION_TEXT_SIZE);
        mShearerLocationDiagram.setDescription(description);
    }

    /*метод отрисовки графиков*/
    private void drawDiagram() {
        boolean isTodayLabelCreated = false;
        boolean isYesterdayLabelCreated = false;

        ArrayList<ILineDataSet> dataSetList = new ArrayList<>();

        // Obtain a Realm instance
        mDataBase = Realm.getDefaultInstance();
        /*Найти все доступные объекты диаграмм в базе данных*/
        mDiagrams = mDataBase.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", mPrimaryKey)
                .findAll()
                .sort("absoluteTime", Sort.ASCENDING);

        if (!mDiagrams.isEmpty()) {

            for (LocationDiagram diagram : mDiagrams) {
                /*Найти все отрезки, принадлежащие этой диаграмме*/
                RealmResults<LocationDiagramSegment> segments = mDataBase.where(LocationDiagramSegment.class)
                        .equalTo("parentPrimaryKey", mPrimaryKey)
                        .equalTo("diagrams.absoluteTime", diagram.getAbsoluteTime())
                        .findAll();

                if (!segments.isEmpty()) {

                    for (LocationDiagramSegment segment : segments) {
                        /*Найти все точки, принадлежащие этому отрезку*/
                        RealmResults<LocationDiagramDot> dots = mDataBase.where(LocationDiagramDot.class)
                                .equalTo("parentPrimaryKey", mPrimaryKey)
                                .equalTo("segments.absoluteTime", segment.getAbsoluteTime())
                                .findAll()
                                .sort("absoluteTime", Sort.ASCENDING);

                        if (!dots.isEmpty()) {
                            try {
                                RealmLineDataSet<LocationDiagramDot> dataSet = new RealmLineDataSet<>(dots, "timeForChart", "location");
                                dataSet.setDrawValues(false);
                                dataSet.setDrawCircles(false);

                                if (segment.getDate().equals(getDate())) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        dataSet.setColor(getResources().getColor(R.color.colorRedForConnection, null));
                                    } else {
                                        dataSet.setColor(getResources().getColor(R.color.colorRedForConnection));
                                    }

                                    if (!isTodayLabelCreated) {
                                        dataSet.setLabel(segment.getDate());
                                        isTodayLabelCreated = true;
                                    } else {
                                        dataSet.setLabel("");
                                    }
                                } else {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        dataSet.setColor(getResources().getColor(R.color.colorLightBlue, null));
                                    } else {
                                        dataSet.setColor(getResources().getColor(R.color.colorLightBlue));
                                    }

                                    if (!isYesterdayLabelCreated) {
                                        dataSet.setLabel(segment.getDate());
                                        isYesterdayLabelCreated = true;
                                    } else {
                                        dataSet.setLabel("");
                                    }
                                }
                                dataSetList.add(dataSet);
                            } catch (NullPointerException e) {
                                Log.e(TAG, "NullPointerException caught in RealmBaseDataSet.getEntryForIndex");
                                Log.e(TAG, "Number of dots: " + dots.size());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        LineData lineData = new LineData(dataSetList);

        mShearerLocationDiagram.setData(lineData);
        mShearerLocationDiagram.invalidate();
    }

    /*метод получения даты текущих рабочих суток в формате дд.мм.гг*/
    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        /*вычислить время суток - час с дробной частью*/
        float time = (float)calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE)/60.0f);

        /*если время суток больше или равно 8 часам - это текущие рабочие сутки*/
        if (time >= 8.0f) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        } else {
            /*если время суток меньше 8 часов - наступил следующий календарный день,
            * но текущие рабочие сутки продолжаются*/
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        }
    }

    @NonNull
    public static Fragment newInstance(long primaryKey) {
        Bundle bundle = new Bundle();
        bundle.putLong(PRIMARY_KEY_EXTRA, primaryKey);
        LocationDiagramFragment fragment = new LocationDiagramFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*класс для замены значений, отображаемых по оси Х*/
    private class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] m_Values;

        XAxisValueFormatter(String[] values) {
            this.m_Values = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return m_Values[(int) value];
        }
    }
}
