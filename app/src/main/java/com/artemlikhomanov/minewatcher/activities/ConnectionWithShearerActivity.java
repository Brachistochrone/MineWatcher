package com.artemlikhomanov.minewatcher.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.controller.ConnectionStateChangedListener;
import com.artemlikhomanov.minewatcher.fragments.BlocksListFragment;
import com.artemlikhomanov.minewatcher.fragments.LocationDiagramFragment;
import com.artemlikhomanov.minewatcher.fragments.MalfunctionsListFragment;
import com.artemlikhomanov.minewatcher.fragments.NetworkFragment;
import com.artemlikhomanov.minewatcher.fragments.SensorsListFragment;
import com.artemlikhomanov.minewatcher.fragments.TechnicalWindowFragment;
import com.artemlikhomanov.minewatcher.fragments.ThermometersListFragment;
import com.artemlikhomanov.minewatcher.model.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConnectionWithShearerActivity extends AppCompatActivity implements ConnectionStateChangedListener {

    private static final String TAG = "ConnectionActivity";
    private static final String TAG_NETWORK_FRAGMENT = "TAG_NETWORK_FRAGMENT";

    private static final String EXTRA_CONNECTION_NAME = "com.artemlikhomanov.minewatcher.activities.EXTRA_CONNECTION_NAME";
    private static final String EXTRA_IP_ADDRESS = "com.artemlikhomanov.minewatcher.activities.EXTRA_IP_ADDRESS";
    private static final String EXTRA_PORT_NUMBER = "com.artemlikhomanov.minewatcher.activities.EXTRA_PORT_NUMBER";
    private static final String EXTRA_SHEARER_TYPE = "com.artemlikhomanov.minewatcher.activities.EXTRA_SHEARER_TYPE";
    private static final String EXTRA_PRIMARY_KEY = "com.artemlikhomanov.minewatcher.activities.EXTRA_PRIMARY_KEY";

    private static final String KEY_CONNECTION_NAME = "KEY_CONNECTION_NAME";
    private static final String KEY_IP_ADDRESS = "KEY_IP_ADDRESS";
    private static final String KEY_PORT_NUMBER = "KEY_PORT_NUMBER";
    private static final String KEY_SHEARER_TYPE = "KEY_SHEARER_TYPE";
    private static final String KEY_CONNECTION_STATE = "KEY_CONNECTION_STATE";

    @BindView(R.id.content_frame)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.connection_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.state_line_text_view)
    TextView mStateLine;
    @BindView(R.id.message_text_view)
    TextView mConnectionMessage;

    private NetworkFragment mNetworkFragment;

    private String mConnectionName;
    private String mIpAddress;
    private String mPortNumber;

    private long mPrimaryKey;

    private int mShearerType;

    private boolean mIsConnected = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONNECTION_NAME, mConnectionName);
        outState.putString(KEY_IP_ADDRESS, mIpAddress);
        outState.putString(KEY_PORT_NUMBER, mPortNumber);
        outState.putInt(KEY_SHEARER_TYPE, mShearerType);
        outState.putBoolean(KEY_CONNECTION_STATE, mIsConnected);
        outState.putLong(EXTRA_PRIMARY_KEY, mPrimaryKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mConnectionName = getIntent().getStringExtra(EXTRA_CONNECTION_NAME);
        mIpAddress = getIntent().getStringExtra(EXTRA_IP_ADDRESS);
        mPortNumber = getIntent().getStringExtra(EXTRA_PORT_NUMBER);
        mShearerType = getIntent().getIntExtra(EXTRA_SHEARER_TYPE, Const.SHEARER_TYPE_CLS450_first);
        mPrimaryKey = getIntent().getLongExtra(EXTRA_PRIMARY_KEY, (long) 0.0);

        ButterKnife.bind(this);

        extractSavedValues(savedInstanceState);
        startNetworkFragment(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorCorumLightGray), PorterDuff.Mode.SRC_ATOP);

        setTitle(mConnectionName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mIsConnected) {
            initUI();
        }
        throwConnectionMessage(mNetworkFragment.getConnectionStateMessage());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkFragment.removeListeners();
    }

    @Override
    public void notifyConnectionStateChanged(int message) {
        if (!mIsConnected) {
            initUI();
            mIsConnected = true;
        }
        throwConnectionMessage(message);
    }

    private void throwConnectionMessage(int message) {

        switch (message){
            case 0:
                break;
            case Const.SERVER_NOT_AVAILABLE:
                showConnectionMessage(getString(R.string.server_not_available));
                break;
            case Const.NETWORK_NOT_AVAILABLE:
                showConnectionMessage(getString(R.string.network_not_available));
                break;
            case Const.UNDERGROUND_MODEM_NOT_AVAILABLE:
                showConnectionMessage(getString(R.string.underground_modem_not_available));
                break;
            case Const.NO_CONNECTION_WITH_SHEARER:
                showConnectionMessage(getString(R.string.shearer_not_available));
                break;
            case Const.CONNECTING:
                break;
            case Const.CONNECTION_ESTABLISHED:
                mConnectionMessage.setVisibility(View.GONE);
                break;
            case Const.SERVER_DISCONNECTED:
                showConnectionMessage(getString(R.string.server_disconnected));
                break;
        }
    }

    private void extractSavedValues(Bundle savedState) {
        if (savedState != null) {
            mConnectionName = savedState.getString(KEY_CONNECTION_NAME);
            mIpAddress = savedState.getString(KEY_IP_ADDRESS);
            mPortNumber = savedState.getString(KEY_PORT_NUMBER);
            mShearerType = savedState.getInt(KEY_SHEARER_TYPE);
            mIsConnected = savedState.getBoolean(KEY_CONNECTION_STATE);
            mPrimaryKey = savedState.getLong(EXTRA_PRIMARY_KEY);
        }
    }

    private void showConnectionMessage(String message) {
        mConnectionMessage.setText(message);
        mConnectionMessage.setVisibility(View.VISIBLE);
    }

    private void startNetworkFragment(Bundle savedState) {
        if (savedState != null) {
            mNetworkFragment = (NetworkFragment) getSupportFragmentManager().findFragmentByTag(TAG_NETWORK_FRAGMENT);
            mNetworkFragment.setConnectionStateListener(this);
        } else {
            mNetworkFragment = (NetworkFragment) NetworkFragment.newInstance();
            mNetworkFragment.setIpAddress(mIpAddress);
            mNetworkFragment.setPortNumber(mPortNumber);
            mNetworkFragment.setPrimaryKey(mPrimaryKey);
            mNetworkFragment.setConnectionStateListener(this);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, mNetworkFragment, TAG_NETWORK_FRAGMENT)
                    .commit();
        }
    }

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(TechnicalWindowFragment.newInstance(mShearerType));
        adapter.addFragment(ThermometersListFragment.newInstance());
        adapter.addFragment(SensorsListFragment.newInstance());
        adapter.addFragment(BlocksListFragment.newInstance(mShearerType));
        adapter.addFragment(MalfunctionsListFragment.newInstance(mPrimaryKey));
        adapter.addFragment(LocationDiagramFragment.newInstance(mPrimaryKey));

        mViewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_tab_worker_selector));
        mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_tab_celsius_selector));
        mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_tab_gauge_selector));
        mTabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_tab_lan_selector));
        mTabLayout.getTabAt(4).setIcon(getResources().getDrawable(R.drawable.ic_tab_alert_octagon_selector));
        mTabLayout.getTabAt(5).setIcon(getResources().getDrawable(R.drawable.ic_tab_chart_line_selector));
    }

    public static Intent newIntent(Activity activity, String name, String ip, String port, int shearerType, long primaryKey) {
        Intent intent = new Intent(activity, ConnectionWithShearerActivity.class);
        intent.putExtra(EXTRA_CONNECTION_NAME, name);
        intent.putExtra(EXTRA_IP_ADDRESS, ip);
        intent.putExtra(EXTRA_PORT_NUMBER, port);
        intent.putExtra(EXTRA_SHEARER_TYPE, shearerType);
        intent.putExtra(EXTRA_PRIMARY_KEY, primaryKey);
        return intent;
    }

    private int getLayoutResId() {
        return R.layout.activity_connection_with_shearer;
    }

    private void initUI() {
        mProgressBar.setVisibility(View.GONE);
        mStateLine.setVisibility(View.GONE);
        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        void addFragment(Fragment fragment) {
            mFragments.add(fragment);
        }
    }
}
