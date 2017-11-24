package io.dactilo.sumbawa.spring.spreadsheets.fiters.csv;

import io.dactilo.sumbawa.spring.spreadsheets.converter.SpringSpreadsheetConfiguration;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVPrinterFactory;
import io.dactilo.sumbawa.spring.spreadsheets.converter.csv.CSVStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@Import(SpringSpreadsheetConfiguration.class)
public class CSVFormatterConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private SpringSpreadsheetConfiguration springSpreadsheetConfiguration;

    @Bean
    public CSVHandlerMethodReturnValueHandler csvHandlerMethodReturnValueHandler() {
        return new CSVHandlerMethodReturnValueHandler(csvStreamer(), springSpreadsheetConfiguration.jacksonToSpreadsheetConverter());
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
