package io.dactilo.sumbawa.spring.spreadsheets.fiters.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.JacksonToSpreadsheetConverter;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVPrinterFactory;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVStreamer;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.ExcelStreamer;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.XSFFWorkbokFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ExcelFormatterConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public ExcelHandlerMethodReturnValueHandler csvHandlerMethodReturnValueHandler() {
        return new ExcelHandlerMethodReturnValueHandler(excelStreamer(), jacksonToSpreadsheetConverter());
    }

    @Bean
    public JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter() {
        return new JacksonToSpreadsheetConverter();
    }

    @Bean
    public ExcelStreamer excelStreamer() {
        return new ExcelStreamer(xsffWorkbokFactory());
    }

    @Bean
    public XSFFWorkbokFactory xsffWorkbokFactory() {
        return new XSFFWorkbokFactory();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(csvHandlerMethodReturnValueHandler());
    }
}
