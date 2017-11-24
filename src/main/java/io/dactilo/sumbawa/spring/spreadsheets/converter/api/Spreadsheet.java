package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.util.List;

public class Spreadsheet {
    private final List<String> spreadsheetHeader;
    private final List<List<Object>> spreadsheetRows;

    public Spreadsheet(List<String> spreadsheetHeader,
                       List<List<Object>> spreadsheetRows) {
        this.spreadsheetHeader = spreadsheetHeader;
        this.spreadsheetRows = spreadsheetRows;
    }

    public List<String> getSpreadsheetHeader() {
        return spreadsheetHeader;
    }

    public List<List<Object>> getSpreadsheetRows() {
        return spreadsheetRows;
    }
}
