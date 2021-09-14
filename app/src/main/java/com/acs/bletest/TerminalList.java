/*
 * Copyright (C) 2017 Advanced Card Systems Ltd. All rights reserved.
 *
 * This software is the confidential and proprietary information of Advanced
 * Card Systems Ltd. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with ACS.
 */

package com.acs.bletest;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardTerminal;

/**
 * The {@code TerminalList} class is a singleton that provides the list of card terminals.
 *
 * @author Godfrey Chung
 * @version 1.0, 23 Jun 2017
 */
public final class TerminalList {

    private static final TerminalList INSTANCE = new TerminalList();
    private List<CardTerminal> mTerminals = new ArrayList<>();

    /**
     * Creates an instance of {@code TerminalList}.
     */
    private TerminalList() {
    }

    /**
     * Returns the instance of {@code TerminalList}.
     *
     * @return the instance
     */
    public static TerminalList getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the list of card terminals.
     *
     * @return the list of card terminals
     */
    public List<CardTerminal> getTerminals() {
        return mTerminals;
    }
}
