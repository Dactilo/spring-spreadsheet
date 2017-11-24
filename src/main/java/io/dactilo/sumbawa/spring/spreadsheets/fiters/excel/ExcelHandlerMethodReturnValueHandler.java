package io.dactilo.sumbawa.spring.spreadsheets.fiters.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.ObjectToSpreadsheetConverter;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.ExcelStreamer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;

public class ExcelHandlerMethodReturnValueHandler extends AbstractHttpMessageConverter<Object> {
    private final ExcelStreamer excelStreamer;
    private final ObjectToSpreadsheetConverter objectToSpreadsheetConverter;

    ExcelHandlerMethodReturnValueHandler(ExcelStreamer excelStreamer, ObjectToSpreadsheetConverter objectToSpreadsheetConverter) {
        super(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        this.excelStreamer = excelStreamer;
        this.objectToSpreadsheetConverter = objectToSpreadsheetConverter;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Excel Parsing is not (yet) implemented");
    }

    @Override
    protected void writeInternal(Object input, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        excelStreamer.streamSpreadsheet(outputMessage.getBody(), objectToSpreadsheetConverter.convert((List<Object>) input));
    }
}
