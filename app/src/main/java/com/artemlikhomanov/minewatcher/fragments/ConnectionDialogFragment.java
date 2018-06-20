package com.artemlikhomanov.minewatcher.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.artemlikhomanov.minewatcher.R;
import com.artemlikhomanov.minewatcher.model.Const;
import com.artemlikhomanov.minewatcher.model.realm.ConnectionAddress;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/*Класс диалогового окна для ввода IP адреса и номера порта*/
public class ConnectionDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public static final String TAG = "ConnectionDialog";

    @BindView(R.id.enter_ip_edit_text)
    EditText m_IPAddressEditText;
    @BindView(R.id.enter_port_edit_text)
    EditText m_PortNumberEditText;
    @BindView(R.id.enter_name_edit_text)
    EditText m_NameEditText;
    @BindView(R.id.shearer_type_spinner)
    Spinner m_ShearerTypeSpinner;

    private ConnectionDialogListener mDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_connection, null);

        ButterKnife.bind(this, view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                                                            R.array.shearer_types,
                                                            R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.shearer_type_spinner_dropdown_item);
        m_ShearerTypeSpinner.setAdapter(adapter);

        return new AlertDialog.Builder(getActivity(), R.style.DialogConnection)
                            .setView(view)
                            .setTitle(R.string.connection_dialog_title)
                            .setCancelable(true)
                            .setPositiveButton(android.R.string.ok, this)
                            .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (isIPAddressCorrect(m_IPAddressEditText.getText().toString()) &&
                isPortNumberCorrect(m_PortNumberEditText.getText().toString()) &&
                isShearerTypeSpecified()) {

            String ip = m_IPAddressEditText.getText().toString();
            String port = m_PortNumberEditText.getText().toString();
            String name;
            if (!m_NameEditText.getText().toString().isEmpty()) {
                name = m_NameEditText.getText().toString();
            } else {
                name = ip;
            }
            int shearerType = getShearerType();
            mDialogListener.onNewAddressAdded(name, ip, port, shearerType);
        } else {
            Handler h = new Handler(getActivity().getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(getActivity(),getResources().getString(R.string.connection_dialog_incompatible_format), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0, 250);
                    toast.show();
                }
            });
        }
    }

    /*метод проверяет что формат IP адреса верен*/
    private boolean isIPAddressCorrect(String string) {

        /*разбить строку по точкам*/
        Iterable<String> iterable =  Splitter.on(".").split(string);
        ArrayList<String> strings = Lists.newArrayList(iterable);
        /*если получилось больше или меньше 4 частей - формат не верен*/
        if (strings.size() != 4) {
            return false;
        }

        for (String str : strings) {
            /*если в какой-то части больше 3 знаков - формат не верен*/
            if (str.length() > 3) {
                return false;
            }
            /*если в какой-то части не только цифры - формат не верен*/
            if (!CharMatcher.digit().matchesAllOf(str)) {
                return false;
            }
        }
        return true;
    }

    /*метод проверяет что формат номера порта верен*/
    private boolean isPortNumberCorrect(String string) {

        /*если больше 5 знаков - формат не верен*/
        if (string.length() > 5 || string.length() == 0) {
            return false;
        }
        /*если не только цифры - формат не верен*/
        return CharMatcher.digit().matchesAllOf(string);
    }

    private boolean isShearerTypeSpecified() {
        String selectedItem = m_ShearerTypeSpinner.getSelectedItem().toString();
        return !selectedItem.equals(getString(R.string.default_shearer_type));
    }

    private int getShearerType() {
        String[] shearerTypes = getResources().getStringArray(R.array.shearer_types);
        String selectedShearerType = m_ShearerTypeSpinner.getSelectedItem().toString();

        if (selectedShearerType.equals(shearerTypes[1])) {
            return Const.SHEARER_TYPE_CLS450_first;
        } else if (selectedShearerType.equals(shearerTypes[2])) {
            return Const.SHEARER_TYPE_CLS450;
        } else if (selectedShearerType.equals(shearerTypes[3])) {
            return Const.SHEARER_TYPE_KDK500;
        } else {
            return Const.SHEARER_TYPE_CLS450_first;
        }
    }

    @NonNull
    public static ConnectionDialogFragment newInstance() {
        return new ConnectionDialogFragment();
    }

    public void setListener (ConnectionDialogListener listener) {
        mDialogListener = listener;
    }

    public interface ConnectionDialogListener {
        void onNewAddressAdded (String name, String ip, String port, int shearerType);
    }
}
