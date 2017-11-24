package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.util.List;

public interface SpreadsheetConverter {
    <I> Spreadsheet convert(List<I> input);
}
