package io.dactilo.sumbawa.spring.spreadsheets.converter;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.ObjectToSpreadsheetConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSpreadsheetConfiguration {
    @Bean
    public ObjectToSpreadsheetConverter jacksonToSpreadsheetConverter() {
        return new ObjectToSpreadsheetConverter();
    }
}
