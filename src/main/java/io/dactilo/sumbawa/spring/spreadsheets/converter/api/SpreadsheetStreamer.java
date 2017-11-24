package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Serialize and send a {@link Spreadsheet} into a stream.
 */
public interface SpreadsheetStreamer {
    /**
     * Serialize and send a {@link Spreadsheet} into a stream.
     *
     * @param outputStream The stream to send the results to
     * @param spreadsheet The spreadsheet
     * @throws IOException If any IO error occurs
     */
    void streamSpreadsheet(OutputStream outputStream, Spreadsheet spreadsheet) throws IOException;

    default byte[] toByteArray(Spreadsheet spreadsheet) throws IOException {
        try(final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            streamSpreadsheet(byteArrayOutputStream, spreadsheet);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
