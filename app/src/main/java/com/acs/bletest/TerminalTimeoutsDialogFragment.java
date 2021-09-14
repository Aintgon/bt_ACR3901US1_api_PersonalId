/*
 * Copyright (C) 2019 Advanced Card Systems Ltd. All rights reserved.
 *
 * This software is the confidential and proprietary information of Advanced
 * Card Systems Ltd. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with ACS.
 */

package com.acs.bletest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.acs.smartcardio.TerminalTimeouts;

/**
 * The {@code TerminalTimeoutsDialogFragment} class shows the timeout settings of card terminal.
 *
 * @author Godfrey Chung
 * @version 1.0, 12 Sep 2019
 * @since 0.5
 */
public class TerminalTimeoutsDialogFragment extends AppCompatDialogFragment {

    /**
     * Interface definition for a callback to be invoked when positive or negative button is
     * clicked.
     */
    public interface TerminalTimeoutsDialogListener {

        /**
         * Called when the positive button is clicked.
         *
         * @param dialog the dialog fragment
         */
        void onTerminalTimeoutsDialogPositiveClick(DialogFragment dialog);

        /**
         * Called when the negative button is clicked.
         *
         * @param dialog the dialog fragment
         */
        void onTerminalTimeoutsDialogNegativeClick(DialogFragment dialog);
    }

    private static final String STATE_TERMINAL_NAME = "terminal_name";
    private TerminalTimeoutsDialogListener mListener;
    private TextView mTerminalNameTextView;
    private CheckBox mUseDefaultTimeoutCheckBox;
    private EditText mConnectionTimeoutEditText;
    private EditText mPowerTimeoutEditText;
    private EditText mProtocolTimeoutEditText;
    private EditText mApduTimeoutEditText;
    private EditText mControlTimeoutEditText;
    private String mTerminalName;
    private long mConnectionTimeout;
    private long mPowerTimeout;
    private long mProtocolTimeout;
    private long mApduTimeout;
    private long mControlTimeout;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            mListener = (TerminalTimeoutsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TerminalTimeoutsDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_terminal_timeouts, null);

        mTerminalNameTextView = view.findViewById(
                R.id.dialog_terminal_timeouts_text_view_terminal_name);

        mUseDefaultTimeoutCheckBox = view.findViewById(
                R.id.dialog_terminal_timeouts_check_box_use_default_timeout);
        mUseDefaultTimeoutCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mConnectionTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
                mPowerTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
                mProtocolTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
                mApduTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
                mControlTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());

                if (mUseDefaultTimeoutCheckBox.isChecked()) {

                    mConnectionTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
                    mPowerTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
                    mProtocolTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
                    mApduTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
                    mControlTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;

                    mConnectionTimeoutEditText.setText(String.valueOf(mConnectionTimeout));
                    mPowerTimeoutEditText.setText(String.valueOf(mPowerTimeout));
                    mProtocolTimeoutEditText.setText(String.valueOf(mProtocolTimeout));
                    mApduTimeoutEditText.setText(String.valueOf(mApduTimeout));
                    mControlTimeoutEditText.setText(String.valueOf(mControlTimeout));
                }
            }
        });

        mConnectionTimeoutEditText = view.findViewById(
                R.id.dialog_terminal_timeouts_edit_text_connection_timeout);
        mPowerTimeoutEditText = view.findViewById(
                R.id.dialog_terminal_timeouts_edit_text_power_timeout);
        mProtocolTimeoutEditText = view.findViewById(
                R.id.dialog_terminal_timeouts_edit_text_protocol_timeout);
        mApduTimeoutEditText = view.findViewById(
                R.id.dialog_terminal_timeouts_edit_text_apdu_timeout);
        mControlTimeoutEditText = view.findViewById(
                R.id.dialog_terminal_timeouts_edit_text_control_timeout);

        /* Restore the contents. */
        if (savedInstanceState != null) {

            mTerminalNameTextView.setText(savedInstanceState.getCharSequence(STATE_TERMINAL_NAME));

        } else {

            mTerminalNameTextView.setText(mTerminalName);
            mConnectionTimeoutEditText.setText(String.valueOf(mConnectionTimeout));
            mPowerTimeoutEditText.setText(String.valueOf(mPowerTimeout));
            mProtocolTimeoutEditText.setText(String.valueOf(mProtocolTimeout));
            mApduTimeoutEditText.setText(String.valueOf(mApduTimeout));
            mControlTimeoutEditText.setText(String.valueOf(mControlTimeout));
        }

        builder.setTitle(R.string.set_terminal_timeouts)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        updateData();
                        mListener.onTerminalTimeoutsDialogPositiveClick(
                                TerminalTimeoutsDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onTerminalTimeoutsDialogNegativeClick(
                                TerminalTimeoutsDialogFragment.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        /* Save the contents. */
        outState.putCharSequence(STATE_TERMINAL_NAME, mTerminalNameTextView.getText());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mUseDefaultTimeoutCheckBox.setChecked(
                mConnectionTimeoutEditText.getText().toString().equals(
                        String.valueOf(TerminalTimeouts.DEFAULT_TIMEOUT))

                        && mPowerTimeoutEditText.getText().toString().equals(
                        String.valueOf(TerminalTimeouts.DEFAULT_TIMEOUT))

                        && mProtocolTimeoutEditText.getText().toString().equals(
                        String.valueOf(TerminalTimeouts.DEFAULT_TIMEOUT))

                        && mApduTimeoutEditText.getText().toString().equals(
                        String.valueOf(TerminalTimeouts.DEFAULT_TIMEOUT))

                        && mControlTimeoutEditText.getText().toString().equals(
                        String.valueOf(TerminalTimeouts.DEFAULT_TIMEOUT)));

        mConnectionTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
        mPowerTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
        mProtocolTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
        mApduTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
        mControlTimeoutEditText.setEnabled(!mUseDefaultTimeoutCheckBox.isChecked());
    }

    /**
     * Gets the terminal name.
     *
     * @return the terminal name
     */
    public String getTerminalName() {
        return mTerminalName;
    }

    /**
     * Sets the terminal name.
     *
     * @param terminalName the terminal name
     */
    public void setTerminalName(String terminalName) {
        mTerminalName = terminalName;
    }

    /**
     * Gets the connection timeout.
     *
     * @return the connection timeout
     */
    public long getConnectionTimeout() {
        return mConnectionTimeout;
    }

    /**
     * Sets the connection timeout.
     *
     * @param connectionTimeout the connection timeout
     */
    public void setConnectionTimeout(long connectionTimeout) {
        mConnectionTimeout = connectionTimeout;
    }

    /**
     * Gets the power timeout.
     *
     * @return the power timeout
     */
    public long getPowerTimeout() {
        return mPowerTimeout;
    }

    /**
     * Sets the power timeout
     *
     * @param powerTimeout the power timeout
     */
    public void setPowerTimeout(long powerTimeout) {
        mPowerTimeout = powerTimeout;
    }

    /**
     * Gets the protocol timeout.
     *
     * @return the protocol timeout
     */
    public long getProtocolTimeout() {
        return mProtocolTimeout;
    }

    /**
     * Sets the protocol timeout.
     *
     * @param protocolTimeout the protocol timeout
     */
    public void setProtocolTimeout(long protocolTimeout) {
        mProtocolTimeout = protocolTimeout;
    }

    /**
     * Gets the APDU timeout.
     *
     * @return the APDU timeout
     */
    public long getApduTimeout() {
        return mApduTimeout;
    }

    /**
     * Sets the APDU timeout.
     *
     * @param apduTimeout the APDU timeout
     */
    public void setApduTimeout(long apduTimeout) {
        mApduTimeout = apduTimeout;
    }

    /**
     * Gets the control timeout
     *
     * @return the control timeout
     */
    public long getControlTimeout() {
        return mControlTimeout;
    }

    /**
     * Sets the control timeout.
     *
     * @param controlTimeout the control timeout
     */
    public void setControlTimeout(long controlTimeout) {
        mControlTimeout = controlTimeout;
    }

    /**
     * Updates the data.
     */
    private void updateData() {

        try {
            mConnectionTimeout = Long.parseLong(mConnectionTimeoutEditText.getText().toString());
        } catch (NumberFormatException e) {
            mConnectionTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
        }

        try {
            mPowerTimeout = Long.parseLong(mPowerTimeoutEditText.getText().toString());
        } catch (NumberFormatException e) {
            mPowerTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
        }

        try {
            mProtocolTimeout = Long.parseLong(mProtocolTimeoutEditText.getText().toString());
        } catch (NumberFormatException e) {
            mProtocolTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
        }

        try {
            mApduTimeout = Long.parseLong(mApduTimeoutEditText.getText().toString());
        } catch (NumberFormatException e) {
            mApduTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
        }

        try {
            mControlTimeout = Long.parseLong(mControlTimeoutEditText.getText().toString());
        } catch (NumberFormatException e) {
            mControlTimeout = TerminalTimeouts.DEFAULT_TIMEOUT;
        }
    }
}
