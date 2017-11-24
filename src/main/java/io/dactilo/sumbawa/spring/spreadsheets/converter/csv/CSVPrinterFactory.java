package io.dactilo.sumbawa.spring.spreadsheets.converter.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * {@link CSVPrinter} factory
 */
public class CSVPrinterFactory {
    /**
     * Creates a {@link CSVPrinter} instance
     *
     * @param delimiter    The delimiter
     * @param outputStream The outputStream where the csv will be printed to
     * @return The CSVPrinter
     * @throws IOException if any IO error occurs
     */
    CSVPrinter createInstance(char delimiter, OutputStream outputStream) throws IOException {
        final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        return new CSVPrinter(outputStreamWriter,
                CSVFormat
                        .DEFAULT
                        .withDelimiter(delimiter)
                        .withTrim()
        );
    }
}
