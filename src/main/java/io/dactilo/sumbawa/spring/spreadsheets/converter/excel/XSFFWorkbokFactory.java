package io.dactilo.sumbawa.spring.spreadsheets.converter.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * XSFFWorkbook factory
 */
public class XSFFWorkbokFactory {
    XSSFWorkbook createInstance() {
        return new XSSFWorkbook();
    }
}
