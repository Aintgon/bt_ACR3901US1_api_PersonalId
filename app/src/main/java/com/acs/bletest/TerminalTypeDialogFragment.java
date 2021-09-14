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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

/**
 * The {@code TerminalTypeDialogFragment} class shows a list of terminal types for selection.
 *
 * @author Godfrey Chung
 * @version 1.0, 20 Apr 2018
 * @since 0.3
 */
public class TerminalTypeDialogFragment extends AppCompatDialogFragment {

    /**
     * Interface definition for a callback to be invoked when the item is clicked.
     */
    public interface TerminalTypeDialogListener {

        /**
         * Called when the item is clicked.
         *
         * @param dialog the dialog fragment
         * @param which  the item
         */
        void onDialogItemClick(DialogFragment dialog, int which);
    }

    private TerminalTypeDialogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            mListener = (TerminalTypeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TerminalTypeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.select_terminal_type)
                .setItems(R.array.terminal_types_array, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogItemClick(TerminalTypeDialogFragment.this, which);
                    }
                });

        return builder.create();
    }
}
