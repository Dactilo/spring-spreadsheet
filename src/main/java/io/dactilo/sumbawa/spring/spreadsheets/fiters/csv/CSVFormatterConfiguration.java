package io.dactilo.sumbawa.spring.spreadsheets.fiters.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.JacksonToSpreadsheetConverter;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVPrinterFactory;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVStreamer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class CSVFormatterConfiguration extends WebMvcConfigurerAdapter {
    @Bean
    public CSVHandlerMethodReturnValueHandler csvHandlerMethodReturnValueHandler() {
        return new CSVHandlerMethodReturnValueHandler(csvStreamer(), jacksonToSpreadsheetConverter());
    }

    @Bean
    public JacksonToSpreadsheetConverter jacksonToSpreadsheetConverter() {
        return new JacksonToSpreadsheetConverter();
    }

    @Bean
    public CSVStreamer csvStreamer() {
        return new CSVStreamer(csvPrinterFactory());
    }

    @Bean
    public CSVPrinterFactory csvPrinterFactory() {
        return new CSVPrinterFactory();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(csvHandlerMethodReturnValueHandler());
    }
}
