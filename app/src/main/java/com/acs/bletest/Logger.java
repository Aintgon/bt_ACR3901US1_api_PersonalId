/*
 * Copyright (C) 2017 Advanced Card Systems Ltd. All rights reserved.
 *
 * This software is the confidential and proprietary information of Advanced
 * Card Systems Ltd. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with ACS.
 */

package com.acs.bletest;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The {@code Logger} class logs the message to text view.
 *
 * @author Godfrey Chung
 * @version 1.0, 21 Jun 2017
 */
public class Logger {

    private static final int MAX_LINES = 1000;
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private Activity mActivity;
    private TextView mTextView;
    private PrintWriter mPrintWriter;

    /**
     * Creates an instance of {@code Logger}.
     *
     * @param textView the text view
     */
    public Logger(Activity activity, TextView textView) {

        /* Check the parameter. */
        if (activity == null) {
            throw new IllegalArgumentException("Activity must not be null");
        }

        if (textView == null) {
            throw new IllegalArgumentException("Text view must not be null");
        }

        mActivity = activity;
        mTextView = textView;
        mTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Opens the log file.
     *
     * @param logFile the log file
     * @throws IOException if there is an error in opening or creating the log file
     */
    public void openLogFile(File logFile) throws IOException {

        /* Close the log file if it is opened. */
        closeLogFile();

        /* Open the log file for append. */
        mPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
    }

    /**
     * Closes the log file.
     */
    public void closeLogFile() {

        /* Close the print writer. */
        if (mPrintWriter != null) {

            mPrintWriter.close();
            mPrintWriter = null;
        }
    }

    /**
     * Logs the message.
     *
     * @param format the format
     * @param args   the arguments
     */
    public void logMsg(String format, Object... args) {

        final String msg = String.format(Locale.US, format, args);

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                int lines = mTextView.getHeight() / mTextView.getLineHeight();

                /* Append the message to the text view. */
                mTextView.append(msg + "\n");

                /* Remove the first line from the text view. */
                if (mTextView.getLineCount() > MAX_LINES) {

                    String logString = mTextView.getText().toString();
                    int newLineIndex = logString.indexOf('\n', 1);

                    if (newLineIndex >= 0) {
                        mTextView.getEditableText().delete(0, newLineIndex);
                    }
                }

                /* Scroll the text view. */
                if (mTextView.getLineCount() > lines) {
                    mTextView.scrollTo(0, (mTextView.getLineCount() - lines)
                            * mTextView.getLineHeight());
                }
            }
        });

        if (mPrintWriter != null) {

            DateFormat dateFormat = new SimpleDateFormat("[dd-MM-yyyy HH:mm:ss]: ", Locale.US);
            Date date = new Date();

            mPrintWriter.println(dateFormat.format(date) + msg);
            mPrintWriter.flush();
        }
    }

    /**
     * Logs the contents of buffer.
     *
     * @param buffer the buffer
     */
    public void logBuffer(byte[] buffer) {
        if (buffer != null) {
            logBuffer(buffer, 0, buffer.length);
        }
    }

    /**
     * Logs the contents of buffer.
     *
     * @param buffer    the buffer
     * @param offset    the offset
     * @param byteCount the byte count
     */
    public void logBuffer(byte[] buffer, int offset, int byteCount) {

        /* Check the parameter. */
        if ((buffer == null) || (offset < 0) || (byteCount < 0)
                || (offset + byteCount > buffer.length)) {
            return;
        }

        StringBuilder builder = new StringBuilder(3 * byteCount);

        for (int i = 0; i < byteCount; i++) {

            int tmp = buffer[offset + i] & 0xFF;

            if (i % 16 == 0) {
                if (builder.length() > 0) {

                    logMsg(builder.toString());
                    builder.setLength(0);
                }
            } else {
                builder.append(" ");
            }

            builder.append(HEX_DIGITS[tmp >>> 4]);
            builder.append(HEX_DIGITS[tmp & 0x0F]);
        }

        if (builder.length() > 0) {
            logMsg(builder.toString());
        }
    }

    /**
     * Logs the HEX string.
     *
     * @param hexString the HEX string
     */
    public void logHexString(String hexString) {

        if (hexString != null) {

            StringBuilder builder = new StringBuilder();
            boolean first = true;
            int j = 0;

            for (int i = 0; i < hexString.length(); i++) {

                char c = hexString.charAt(i);
                if (((c >= '0') && (c <= '9'))
                        || ((c >= 'A') && (c <= 'F'))
                        || ((c >= 'a') && (c <= 'f'))
                        || (c == 'X')
                        || (c == 'x')) {

                    if (first) {
                        if (j != 0) {
                            builder.append(" ");
                        }
                    }

                    builder.append(c);
                    j++;

                    first = !first;

                    if (j >= 2 * 16) {

                        logMsg(builder.toString());
                        builder.setLength(0);
                        j = 0;
                    }
                }
            }

            if (j > 0) {
                logMsg(builder.toString());
            }
        }
    }

    /**
     * Clears the log messages.
     */
    public void clear() {

        mTextView.setText("");
        mTextView.scrollTo(0, 0);
    }
}
