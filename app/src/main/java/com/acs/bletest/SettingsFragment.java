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

import androidx.preference.PreferenceFragmentCompat;

/**
 * The {@code SettingsFragment} class loads preferences from preferences.xml.
 *
 * @author Godfrey Chung
 * @version 1.0, 21 Mar 2018
 * @since 0.2
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
