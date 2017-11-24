package io.dactilo.sumbawa.spring.spreadsheets.fiters.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.JacksonToSpreadsheetConverter;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVStreamer;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.ExcelStreamer;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ExcelHandlerMethodReturnValueHandler extends AbstractHttpMessageConverter<Object> {
    private final ExcelStreamer excelStreamer;
    private final JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter;

    public ExcelHandlerMethodReturnValueHandler(ExcelStreamer excelStreamer, JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter) {
        super(new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        this.excelStreamer = excelStreamer;
        this.jacksonToSpreadsheetConverter = jacksonToSpreadsheetConverter;
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
        excelStreamer.streamSpreadsheet(outputMessage.getBody(), jacksonToSpreadsheetConverter.convert((List<Object>) input));
    }
}
