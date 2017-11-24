package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.util.List;

/**
 * Converts a list Java Beans into a {@link Spreadsheet} description
 */
public interface SpreadsheetConverter {
    /**
     * Converts a given input into a {@link Spreadsheet} document
     * @param input The input to be converted
     * @return The {@link Spreadsheet} document
     */
    Spreadsheet convert(List<?> input);
}
