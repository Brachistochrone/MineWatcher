package com.artemlikhomanov.minewatcher.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.realm.CauseOfStop;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class MalfunctionsListFragment extends ListAbstractFragment {

    private static final String PRIMARY_KEY_EXTRA = "PRIMARY_KEY_EXTRA";

    public static final int ITEM_TYPE_SECTION_HEADER_MALFUNCTIONS = 0;
    public static final int ITEM_TYPE_SECTION_CONTENT_MALFUNCTIONS = 1;
//    public static final int ITEM_TYPE_SECTION_HEADER_STOPS = 2;
//    public static final int ITEM_TYPE_SECTION_CONTENT_STOPS = 3;

    private int[] mListItems = new int[]{
            ITEM_TYPE_SECTION_HEADER_MALFUNCTIONS,
            ITEM_TYPE_SECTION_CONTENT_MALFUNCTIONS,
//            ITEM_TYPE_SECTION_HEADER_STOPS,
//            ITEM_TYPE_SECTION_CONTENT_STOPS
    };

    private String[] mStrings = new String[4];

//    private Realm mDataBase;
//    private RealmResults<CauseOfStop> mStopsDB;

    private int mCounter = 6;

    private boolean isReset = false;

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

        valuesInitialisation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mDataBase.close();
    }

    @Override
    void valuesInitialisation() {
        mStrings[0] = getString(R.string.current_malfunctions);
        mStrings[1] = getString(R.string.no_data);
//        mStrings[2] = getString(R.string.last_stop_cause);
//        mStrings[3] = getString(R.string.no_data);

//        mDataBase = Realm.getDefaultInstance();
//        mStopsDB = mDataBase.where(CauseOfStop.class)
//                .equalTo("parentPrimaryKey", mPrimaryKey)
//                .findAll();
    }

    @Override
    void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MalfunctionsListFragment.MalfunctionsAdapter(mListItems, mStrings));
    }

    @Override
    void updateUI(Cls450StateDataItem dataItem) {
        isReset = false;
        mCounter++;
        if (mCounter == 8) {
            mStrings[1] = updateMalfunctions(dataItem);
//            mStrings[3] = updateStops();

            mRecyclerView.getAdapter().notifyDataSetChanged();

            mCounter = 0;
        }
    }

    @Override
    void resetUI() {
        if (!isReset){
            mStrings[1] = getString(R.string.no_data);
//            mStrings[3] = getString(R.string.no_data);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @NonNull
    private String updateMalfunctions(Cls450StateDataItem dataItem) {
        return  dataItem.getMalfunctionOne() + "\n" +
                dataItem.getMalfunctionTwo() + "\n" +
                dataItem.getMalfunctionThree() + "\n" +
                dataItem.getMalfunctionFour() + "\n" +
                dataItem.getMalfunctionFive();
    }

//    @NonNull
//    private String updateStops() {
//        StringBuilder s = new StringBuilder();
//        for (CauseOfStop stop : mStopsDB) {
//            s.append(stop.getCause()).append("\n");
//        }
//        return s.toString();
//    }

    @NonNull
    public static Fragment newInstance(long primaryKey) {
        Bundle bundle = new Bundle();
        bundle.putLong(PRIMARY_KEY_EXTRA, primaryKey);
        MalfunctionsListFragment fragment = new MalfunctionsListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

//    /*метод получения даты текущих рабочих суток в формате дд.мм.гг*/
//    private String getDate(){
//        Calendar calendar = Calendar.getInstance();
//        /*вычислить время суток - час с дробной частью*/
//        float time = (float)calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE)/60.0f);
//
//        /*если время суток больше или равно 8 часам - это текущие рабочие сутки*/
//        if (time >= 8.0f) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
//            return dateFormat.format(calendar.getTime());
//        } else {
//            /*если время суток меньше 8 часов - наступил следующий календарный день,
//            * но текущие рабочие сутки продолжаются*/
//            calendar.add(Calendar.DAY_OF_MONTH, -1);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
//            return dateFormat.format(calendar.getTime());
//        }
//    }

    /*ViewHolder тип 1*/
    private class SectionHeaderHolder extends RecyclerView.ViewHolder {

        private TextView mHeaderTextView;

        SectionHeaderHolder(View itemView) {
            super(itemView);
            mHeaderTextView = itemView.findViewById(R.id.section_header_text_view);
        }

        void bindData(String header) {
            mHeaderTextView.setText(header);
        }
    }

    /*ViewHolder тип 2*/
    private class SectionContentHolder extends RecyclerView.ViewHolder {

        private TextView mContentTextView;

        SectionContentHolder(View itemView) {
            super(itemView);
            mContentTextView = itemView.findViewById(R.id.section_content_text_view);
        }

        void bindData(String string) {
            mContentTextView.setText(string);
        }
    }

    /*Адаптер для RecyclerView*/
    private class MalfunctionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int[] mListItems;
        private String[] mStrings;

        MalfunctionsAdapter(int[] listItems, String[] strings) {
            mListItems = listItems;
            mStrings = strings;
        }

        /*Определить тип отображаемой View в зависимости от позиции*/
        @Override
        public int getItemViewType(int position) {
            return mListItems[position];
        }

        /*Создать соответствующий ViewHolder в зависимости от типа*/
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view;
            switch (viewType) {
                case ITEM_TYPE_SECTION_HEADER_MALFUNCTIONS:
                    view = inflater.inflate(R.layout.list_item_section_header, parent, false);
                    return new SectionHeaderHolder(view);
                case ITEM_TYPE_SECTION_CONTENT_MALFUNCTIONS:
                    view = inflater.inflate(R.layout.list_item_section_content, parent, false);
                    return new SectionContentHolder(view);
//                case ITEM_TYPE_SECTION_HEADER_STOPS:
//                    view = inflater.inflate(R.layout.list_item_section_header, parent, false);
//                    return new SectionHeaderHolder(view);
//                case ITEM_TYPE_SECTION_CONTENT_STOPS:
//                    view = inflater.inflate(R.layout.list_item_section_content, parent, false);
//                    return new SectionContentHolder(view);
            }
            return new SectionHeaderHolder(inflater.inflate(R.layout.list_item_section_header, parent, false));
        }

        /*Передать данные во ViewHolder для отображения*/
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            switch (holder.getItemViewType()) {
                case ITEM_TYPE_SECTION_HEADER_MALFUNCTIONS:
                    ((SectionHeaderHolder)holder).bindData(mStrings[ITEM_TYPE_SECTION_HEADER_MALFUNCTIONS]);
                    break;
                case ITEM_TYPE_SECTION_CONTENT_MALFUNCTIONS:
                    ((SectionContentHolder)holder).bindData(mStrings[ITEM_TYPE_SECTION_CONTENT_MALFUNCTIONS]);
                    break;
//                case ITEM_TYPE_SECTION_HEADER_STOPS:
//                    ((SectionHeaderHolder)holder).bindData(mStrings[ITEM_TYPE_SECTION_HEADER_STOPS]);
//                    break;
//                case ITEM_TYPE_SECTION_CONTENT_STOPS:
//                    ((SectionContentHolder)holder).bindData(mStrings[ITEM_TYPE_SECTION_CONTENT_STOPS]);
//                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mListItems.length;
        }
    }
}
