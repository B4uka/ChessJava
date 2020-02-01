package com.wb.demo.chess.communication.selection;

public class SelectionRq {
    private String fieldSelectionAttempt;

    public SelectionRq (String fieldSelectionAttempt) {
        this.fieldSelectionAttempt = fieldSelectionAttempt;
    }

    public String getFieldSelectionAttempt () {
        return fieldSelectionAttempt;
    }
}
