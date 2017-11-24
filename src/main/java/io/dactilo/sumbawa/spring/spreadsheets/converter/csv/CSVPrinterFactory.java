package io.dactilo.sumbawa.spring.spreadsheets.converter.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CSVPrinterFactory {
    public CSVPrinter createInstance(char delimiter, OutputStream outputStream) throws IOException {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        return new CSVPrinter(outputStreamWriter,
                CSVFormat
                        .DEFAULT
                        .withDelimiter(delimiter)
                        .withTrim()
        );
    }
}
