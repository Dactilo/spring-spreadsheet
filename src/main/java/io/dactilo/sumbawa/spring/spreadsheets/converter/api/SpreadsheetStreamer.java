package io.dactilo.sumbawa.spring.spreadsheets.converter.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface SpreadsheetStreamer {
    void streamSpreadsheet(OutputStream outputStream, Spreadsheet spreadsheet) throws IOException;

    default byte[] toByteArray(Spreadsheet spreadsheet) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        streamSpreadsheet(byteArrayOutputStream, spreadsheet);
        return byteArrayOutputStream.toByteArray();
    }
}
