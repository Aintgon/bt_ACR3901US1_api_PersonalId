/*
 * Copyright (C) 2018 Advanced Card Systems Ltd. All rights reserved.
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

/**
 * The {@code MasterKeyDialogFragment} class shows the master key settings of card terminal.
 *
 * @author Godfrey Chung
 * @version 1.0, 15 May 2018
 * @since 0.3
 */
public class MasterKeyDialogFragment extends AppCompatDialogFragment {

    /**
     * Interface definition for a callback to be invoked when positive or negative button is
     * clicked.
     */
    public interface MasterKeyDialogListener {

        /**
         * Called when the positive button is clicked.
         *
         * @param dialog the dialog fragment
         */
        void onDialogPositiveClick(DialogFragment dialog);

        /**
         * Called when the negative button is clicked.
         *
         * @param dialog the dialog fragment
         */
        void onDialogNegativeClick(DialogFragment dialog);
    }

    private static final String STATE_TERMINAL_NAME = "terminal_name";
    private MasterKeyDialogListener mListener;
    private TextView mTerminalNameTextView;
    private CheckBox mUseDefaultKeyCheckBox;
    private EditText mNewKeyEditText;
    private String mTerminalName;
    private boolean mDefaultKeyUsed;
    private String mNewKey;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            mListener = (MasterKeyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MasterKeyDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_master_key, null);

        mTerminalNameTextView = view.findViewById(R.id.dialog_master_key_text_view_terminal_name);

        mUseDefaultKeyCheckBox = view.findViewById(
                R.id.dialog_master_key_check_box_use_default_key);
        mUseDefaultKeyCheckBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mNewKeyEditText.setEnabled(!mUseDefaultKeyCheckBox.isChecked());
            }
        });

        mNewKeyEditText = view.findViewById(R.id.dialog_master_key_edit_text_new_key);

        /* Restore the contents. */
        if (savedInstanceState != null) {

            mTerminalNameTextView.setText(savedInstanceState.getCharSequence(STATE_TERMINAL_NAME));

        } else {

            mTerminalNameTextView.setText(mTerminalName);
            mUseDefaultKeyCheckBox.setChecked(mDefaultKeyUsed);
            mNewKeyEditText.setText(mNewKey);
        }

        builder.setTitle(R.string.set_master_key)
                .setView(view)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        updateData();
                        mListener.onDialogPositiveClick(MasterKeyDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(MasterKeyDialogFragment.this);
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

        mNewKeyEditText.setEnabled(!mUseDefaultKeyCheckBox.isChecked());
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
     * Returns {@code true} if the default key is used.
     *
     * @return {@code true} if the default key is used, otherwise {@code false}.
     */
    public boolean isDefaultKeyUsed() {
        return mDefaultKeyUsed;
    }

    /**
     * Sets {@code true} to use the default key.
     *
     * @param defaultKeyUsed {@code true} if the default key is used, otherwise {@code false}.
     */
    public void setDefaultKeyUsed(boolean defaultKeyUsed) {
        mDefaultKeyUsed = defaultKeyUsed;
    }

    /**
     * Gets the new key.
     *
     * @return the new key
     */
    public String getNewKey() {
        return mNewKey;
    }

    /**
     * Sets the new key.
     *
     * @param newKey the new key
     */
    public void setNewKey(String newKey) {
        mNewKey = newKey;
    }

    /**
     * Updates the data.
     */
    private void updateData() {

        mDefaultKeyUsed = mUseDefaultKeyCheckBox.isChecked();
        mNewKey = Hex.toHexString(Hex.toByteArray(mNewKeyEditText.getText().toString()));
    }
}
