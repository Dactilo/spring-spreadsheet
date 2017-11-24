package io.dactilo.sumbawa.spring.spreadsheets.fiters.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.JacksonToSpreadsheetConverter;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVStreamer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

public class CSVHandlerMethodReturnValueHandler extends AbstractHttpMessageConverter<Object> {
    private final CSVStreamer csvStreamer;
    private final JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter;

    public CSVHandlerMethodReturnValueHandler(CSVStreamer csvStreamer, JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter) {
        super(new MediaType("text", "csv"));
        this.csvStreamer = csvStreamer;
        this.jacksonToSpreadsheetConverter = jacksonToSpreadsheetConverter;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("CSV Parsing is not (yet) implemented");
    }

    @Override
    protected void writeInternal(Object input, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        csvStreamer.streamSpreadsheet(outputMessage.getBody(), jacksonToSpreadsheetConverter.convert((List<Object>) input));
    }
}
