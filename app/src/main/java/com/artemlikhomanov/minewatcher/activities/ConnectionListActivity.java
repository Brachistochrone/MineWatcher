package com.artemlikhomanov.minewatcher.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.fragments.ConnectionDialogFragment;
import com.artemlikhomanov.minewatcher.fragments.ConnectionListFragment;
import com.artemlikhomanov.minewatcher.fragments.listeners.OnItemDeleteListener;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ConnectionListActivity extends AppCompatActivity implements View.OnClickListener,
                                                                         OnItemDeleteListener,
                                                                         ConnectionDialogFragment.ConnectionDialogListener {

    private static final String DIALOG_CONNECTION = "DIALOG_CONNECTION";

    @BindView(R.id.coordinator)
    CoordinatorLayout mLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFloatingButton;

    private ConnectionListFragment mListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        //test comment
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        mListFragment = (ConnectionListFragment) fm.findFragmentById(R.id.fragment_container);

        if (mListFragment == null) {
            mListFragment = (ConnectionListFragment) createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, mListFragment)
                    .commit();
        }

        setSupportActionBar(mToolbar);

        mFloatingButton.setOnClickListener(this);

        mListFragment.setOnItemDeleteListener(this);
    }

    @Override
    public void onClick(View view) {
        showConnectionDialog();
    }

    @Override
    public void OnItemDeleted() {
        Snackbar snackbar = Snackbar.make(mLayout, getString(R.string.one_item_deleted), Snackbar.LENGTH_SHORT);
        snackbar.setAction(getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListFragment.restoreItem();
            }
        });
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event == DISMISS_EVENT_TIMEOUT) {
                    mListFragment.deleteItemCompletely();
                }
            }
        });
        snackbar.show();
    }

    @Override
    public void onNewAddressAdded(String name, String ip, String port, int shearerType) {
        mListFragment.addNewAddress(name, ip, port, shearerType);
    }

    private void showConnectionDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ConnectionDialogFragment dialogFragment = ConnectionDialogFragment.newInstance();
        dialogFragment.setListener(this);
        dialogFragment.show(fragmentManager, DIALOG_CONNECTION);
    }

    @NonNull
    private Fragment createFragment() {
        return ConnectionListFragment.newInstance();
    }

    private int getLayoutResId() {
        return R.layout.activity_connection_list;
    }
}
