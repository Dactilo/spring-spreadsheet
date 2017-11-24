package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description of a spreadsheet document
 */
public class Spreadsheet {
    private final List<String> spreadsheetHeader;
    private final List<List<Object>> spreadsheetRows;

    public Spreadsheet(List<String> spreadsheetHeader,
                       List<List<Object>> spreadsheetRows) {
        this.spreadsheetHeader = spreadsheetHeader;
        this.spreadsheetRows = spreadsheetRows;
    }

    /**
     * The first row (i.e. The header)
     * @return The header
     */
    public List<String> getSpreadsheetHeader() {
        return Collections.unmodifiableList(spreadsheetHeader);
    }

    /**
     * All the rows
     * @return The rows
     */
    public List<List<Object>> getSpreadsheetRows() {
        return Collections.unmodifiableList(
                spreadsheetRows.stream().map(Collections::unmodifiableList).collect(Collectors.toList())
        );
    }
}
