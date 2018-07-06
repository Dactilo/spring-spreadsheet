package io.dactilo.sumbawa.spring.spreadsheets.fiters.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.SpringSpreadsheetConfiguration;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.ExcelStreamer;
import io.dactilo.sumbawa.spring.spreadsheets.converter.excel.XSFFWorkbokFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@Import(SpringSpreadsheetConfiguration.class)
public class ExcelFormatterConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private SpringSpreadsheetConfiguration springSpreadsheetConfiguration;

    @Bean
    public ExcelHandlerMethodReturnValueHandler excelHandlerMethodReturnValueHandler() {
        return new ExcelHandlerMethodReturnValueHandler(excelStreamer(), springSpreadsheetConfiguration.objectToSpreadsheetConvert());
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
        converters.add(excelHandlerMethodReturnValueHandler());
    }
}
