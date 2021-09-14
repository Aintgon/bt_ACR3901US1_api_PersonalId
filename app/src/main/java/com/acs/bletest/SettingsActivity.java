/*
 * Copyright (C) 2018 Advanced Card Systems Ltd. All rights reserved.
 *
 * This software is the confidential and proprietary information of Advanced
 * Card Systems Ltd. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with ACS.
 */

package com.acs.bletest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The {@code SettingsActivity} class shows preferences from {@code SettingsFragment} object.
 *
 * @author Godfrey Chung
 * @version 1.0, 21 Mar 2018
 * @since 0.2
 */
public class SettingsActivity extends AppCompatActivity {

    /** Preference key: GET RESPONSE for T=0 */
    public static final String KEY_PREF_T0_GET_RESPONSE = "pref_t0_get_response";

    /** Preference key: GET RESPONSE for T=1 */
    public static final String KEY_PREF_T1_GET_RESPONSE = "pref_t1_get_response";

    /** Preference key: Strip Le for T=1 */
    public static final String KEY_PREF_T1_STRIP_LE = "pref_t1_strip_le";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
