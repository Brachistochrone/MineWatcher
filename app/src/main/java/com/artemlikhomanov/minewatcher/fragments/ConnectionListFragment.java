package com.artemlikhomanov.minewatcher.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.activities.ConnectionWithShearerActivity;
import com.artemlikhomanov.minewatcher.model.realm.CauseOfStop;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;
import com.artemlikhomanov.minewatcher.fragments.listeners.OnItemDeleteListener;
import com.artemlikhomanov.minewatcher.fragments.listeners.OnListItemClickListener;
import com.artemlikhomanov.minewatcher.fragments.listeners.OnListItemSwipeListener;
import com.artemlikhomanov.minewatcher.fragments.listeners.RecyclerItemTouchListener;
import com.artemlikhomanov.minewatcher.model.Cls450StateDataItem;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagram;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramDot;
import com.artemlikhomanov.minewatcher.model.realm.LocationDiagramSegment;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ConnectionListFragment extends ListAbstractFragment implements RealmChangeListener<RealmResults<ConnectionAddress>>,
                                                                            OnListItemSwipeListener,
                                                                            OnListItemClickListener{

    private static final String TAG = "ConnectionListFragment";

    private Realm mDataBase;
    private RealmResults<ConnectionAddress> mConnectionAddresses;

    private OnItemDeleteListener mOnItemDeleteListener;

    private int mDeletedIndex;

    private String[] mShearerTypes;

    private String  mDeletedIp;
    private String  mDeletedPort;
    private String  mDeletedName;
    private Integer mDeletedShearerType;
    private Long mDeletedPrimaryKey = (long)0.0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShearerTypes = getResources().getStringArray(R.array.shearer_types);
        mDataBase = Realm.getDefaultInstance();
        mConnectionAddresses = mDataBase.where(ConnectionAddress.class).sort("absoluteTime").findAll();
        mConnectionAddresses.addChangeListener(this);
    }

    @Override
    public void onDestroy() {
        mConnectionAddresses.removeAllChangeListeners();
        mDataBase.close();
        super.onDestroy();
    }

    @Override
    void setupRecyclerView() {
        // Calculate ActionBar height
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        //Set top margin equal to ActionBar height
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
        marginLayoutParams.setMargins(0, actionBarHeight, 0, 0);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());                                   //TODO
        mRecyclerView.setAdapter(new ConnectionAdapter(mConnectionAddresses));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getActivity(), mRecyclerView, this));
    }

    @Override //nothing
    void updateUI(Cls450StateDataItem dataItem) {
    }

    @Override //nothing
    void resetUI() {
    }

    @Override //nothing
    void valuesInitialisation() {
    }

    @Override
    public void onChange(@NonNull RealmResults<ConnectionAddress> results) {
        updateAdapter();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ConnectionHolder) {
            deleteItemTemporarily(position);
            if (mOnItemDeleteListener != null) {
                mOnItemDeleteListener.OnItemDeleted();
            }
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = ConnectionWithShearerActivity.newIntent(getActivity(),
                                    mConnectionAddresses.get(position).getName(),
                                    mConnectionAddresses.get(position).getIpAddress(),
                                    mConnectionAddresses.get(position).getPortNumber(),
                                    mConnectionAddresses.get(position).getShearerType(),
                                    mConnectionAddresses.get(position).getPrimaryKey());
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {
        //TODO
    }

    private void deleteItemTemporarily(int position) {

        /*проверить, есть ли кандидат на окончательное удаление*/
        if (mDeletedPrimaryKey != 0.0) {
            deleteItemCompletely();
        }

        /*временно удалить объект адреса и сохранить его метаданные*/
        mDeletedIndex = position;
        mDeletedIp = mConnectionAddresses.get(position).getIpAddress();
        mDeletedPort = mConnectionAddresses.get(position).getPortNumber();
        mDeletedName = mConnectionAddresses.get(position).getName();
        mDeletedShearerType = mConnectionAddresses.get(position).getShearerType();
        mDeletedPrimaryKey = mConnectionAddresses.get(position).getPrimaryKey();

        mDataBase.beginTransaction();
        mConnectionAddresses.deleteFromRealm(position);
        mDataBase.commitTransaction();
        mRecyclerView.getAdapter().notifyItemRemoved(position);
    }

    public void deleteItemCompletely() {

        RealmResults<CauseOfStop> stops = mDataBase.where(CauseOfStop.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();
        RealmResults<LocationDiagram> diagrams = mDataBase.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();
        RealmResults<LocationDiagramSegment> segments = mDataBase.where(LocationDiagramSegment.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();
        RealmResults<LocationDiagramDot> dots = mDataBase.where(LocationDiagramDot.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();

        if (!stops.isEmpty() || !diagrams.isEmpty() || !segments.isEmpty() || !dots.isEmpty()) {
            mDataBase.beginTransaction();
            dots.deleteAllFromRealm();
            segments.deleteAllFromRealm();
            diagrams.deleteAllFromRealm();
            stops.deleteAllFromRealm();
            mDataBase.commitTransaction();
        }

        /*после окончательного удаления данных привязанных к адресу, очистить сохраненный PrimaryKey*/
        mDeletedPrimaryKey = (long)0.0;
    }

    public void restoreItem() {

        RealmResults<LocationDiagram> diagrams = mDataBase.where(LocationDiagram.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();
        RealmResults<CauseOfStop> stops = mDataBase.where(CauseOfStop.class)
                .equalTo("parentPrimaryKey", mDeletedPrimaryKey)
                .findAll();

        mDataBase.beginTransaction();
        ConnectionAddress address = mDataBase.createObject(ConnectionAddress.class, mDeletedPrimaryKey);
        address.setIpAddress(mDeletedIp);
        address.setPortNumber(mDeletedPort);
        address.setName(mDeletedName);
        address.setShearerType(mDeletedShearerType);
        for (LocationDiagram diagram : diagrams) {
            address.addDiagram(diagram);
        }
        for (CauseOfStop stop : stops) {
            address.addStop(stop);
        }
        mDataBase.commitTransaction();

        mRecyclerView.getAdapter().notifyItemInserted(mDeletedIndex);
        /*после восстановления объекта адреса, очистить сохраненный PrimaryKey*/
        mDeletedPrimaryKey = (long)0.0;
    }

    private void updateAdapter() {
        if(mRecyclerView != null) {
            (mRecyclerView.getAdapter()).notifyDataSetChanged();
        }
    }

    public void addNewAddress(String name, String ip, String port, int shearerType) {
        mDataBase.beginTransaction();
        ConnectionAddress address = mDataBase.createObject(ConnectionAddress.class, getAbsoluteTime());
        address.setIpAddress(ip);
        address.setPortNumber(port);
        address.setName(name);
        address.setShearerType(shearerType);
        mDataBase.commitTransaction();
        updateAdapter();
    }

    public void setOnItemDeleteListener(OnItemDeleteListener itemDeleteListener) {
        mOnItemDeleteListener = itemDeleteListener;
    }

    private long getAbsoluteTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    @NonNull
    public static Fragment newInstance() {
        return new ConnectionListFragment();
    }

    private class ConnectionHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mIpAddress;
        private TextView mPortNumber;
        private TextView mShearerType;
        private RelativeLayout mViewForeground;
        private ImageView mThumbnail;

        ConnectionHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.name);
            mIpAddress = itemView.findViewById(R.id.ip_address);
            mPortNumber = itemView.findViewById(R.id.port_number);
            mShearerType = itemView.findViewById(R.id.shearer_type);
            mViewForeground = itemView.findViewById(R.id.view_foreground);
            mThumbnail = itemView.findViewById(R.id.thumbnail);
        }

        void bindData(String name, String ip, String port, String shearerType) {
            setText(mName, name);
            setText(mIpAddress, ip);
            setText(mPortNumber, port);
            setText(mShearerType, shearerType);
            drawThumbnail(name);
        }

        private void drawThumbnail(String name) {
            RectF textArea = new RectF();
            Rect textBounds = new Rect();
            Paint thmbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            textArea.set(0,
                    0,
                    getResources().getDimensionPixelSize(R.dimen.thumbnail_size),
                    getResources().getDimensionPixelSize(R.dimen.thumbnail_size));

            Bitmap bitmap = Bitmap.createBitmap((int)textArea.width(), (int)textArea.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            thmbPaint.setColor(getResources().getColor(R.color.colorCorumLightGray));
            canvas.drawOval(textArea, thmbPaint);

            textPaint.setColor(getResources().getColor(R.color.colorWhite));
            textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.connection_thumbnail_text_size));
            textPaint.setTextAlign(Paint.Align.LEFT);

            textPaint.getTextBounds(name, 0, 1, textBounds);
            float textX = getTextX(textArea, textBounds);
            float textY = getTextY(textArea, textBounds);
            canvas.drawText(name, 0, 1, textX, textY, textPaint);

            mThumbnail.setImageBitmap(bitmap);
        }

        private float getTextX(RectF textArea, Rect textBounds) {
            return  textArea.width() / 2.0F -
                    textBounds.width() / 2.0F -
                    textBounds.left +
                    textArea.left;
        }

        private float getTextY(RectF textArea, Rect textBounds) {
            return  textArea.height() / 2.0F +
                    textBounds.height() / 2.0F -
                    textBounds.bottom +
                    textArea.top;
        }
    }

    private class ConnectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private RealmResults<ConnectionAddress> mResults;

        ConnectionAdapter(RealmResults<ConnectionAddress> results) {
            mResults = results;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_connection, parent, false);
            return new ConnectionHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = mResults.get(position).getName();
            String ipAddress = mResults.get(position).getIpAddress();
            String portNumber = mResults.get(position).getPortNumber();
            String shearerType = getShearerType(position);

            ((ConnectionHolder)holder).bindData(name, ipAddress, portNumber, shearerType);
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        private String getShearerType(int position) {
            return mShearerTypes[mResults.get(position).getShearerType() - 10];
        }
    }

    private static class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

        private OnListItemSwipeListener mListener;

        RecyclerItemTouchHelper(int dragDirs, int swipeDirs, OnListItemSwipeListener listener) {
            super(dragDirs, swipeDirs);
            mListener = listener;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                final View foregroundView = ((ConnectionHolder)viewHolder).mViewForeground;
                getDefaultUIUtil().onSelected(foregroundView);
            }
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((ConnectionHolder)viewHolder).mViewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final View foregroundView = ((ConnectionHolder)viewHolder).mViewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((ConnectionHolder)viewHolder).mViewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        }

        @Override
        public int convertToAbsoluteDirection(int flags, int layoutDirection) {
            return super.convertToAbsoluteDirection(flags, layoutDirection);
        }
    }
}
