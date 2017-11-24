package io.dactilo.sumbawa.spring.spreadsheets.converter.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.Spreadsheet;
import io.dactilo.sumbawa.spring.spreadsheets.converter.api.SpreadsheetStreamer;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.OutputStream;

public class CSVStreamer implements SpreadsheetStreamer {
    private final CSVPrinterFactory csvPrinterFactory;
    private final char delimiter;

    public CSVStreamer(CSVPrinterFactory csvPrinterFactory, char delimiter) {
        this.csvPrinterFactory = csvPrinterFactory;
        this.delimiter = delimiter;
    }

    public CSVStreamer(CSVPrinterFactory csvPrinterFactory) {
        this(csvPrinterFactory, ';');
    }

    @Override
    public void streamSpreadsheet(OutputStream outputStream, Spreadsheet spreadsheet) throws IOException {
        try(final CSVPrinter csvPrinter = csvPrinterFactory.createInstance(delimiter, outputStream)) {
            csvPrinter.printRecord(spreadsheet.getSpreadsheetHeader());
            csvPrinter.printRecords(spreadsheet.getSpreadsheetRows());
            csvPrinter.flush();
        }
    }
}
