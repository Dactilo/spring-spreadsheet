package io.dactilo.sumbawa.spring.spreadsheets.converter.excel;

import io.dactilo.sumbawa.spring.spreadsheets.converter.api.Spreadsheet;
import io.dactilo.sumbawa.spring.spreadsheets.converter.api.SpreadsheetStreamer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelStreamer implements SpreadsheetStreamer {
    private static final int DEFAULT_COLUMN_SIZE = 3_000;
    private final XSFFWorkbokFactory xsffWorkbokFactory;

    public ExcelStreamer(XSFFWorkbokFactory xsffWorkbokFactory) {
        this.xsffWorkbokFactory = xsffWorkbokFactory;
    }

    @Override
    public void streamSpreadsheet(OutputStream outputStream,
                                  Spreadsheet spreadsheet) throws IOException {
        final XSSFWorkbook workbook = xsffWorkbokFactory.createInstance();
        final XSSFSheet sheet = workbook.createSheet("Export");
        final Map<String, CellStyle> styles = new HashMap<>();

        createStyles(styles, workbook);
        setColumnSizes(sheet, spreadsheet.getSpreadsheetHeader());

        createHeader(styles, sheet, spreadsheet.getSpreadsheetHeader());

        int rowNumber = 1;
        for (List<Object> row : spreadsheet.getSpreadsheetRows()) {
            final Row excelRow = sheet.createRow(rowNumber);
            int columnNumber = 0;
            for (Object column : row) {
                final Cell cell = excelRow.createCell(columnNumber);

                if (column != null) {
                    if (column instanceof Integer) {
                        cell.setCellValue((int) column);
                    } else if (column instanceof Long) {
                        cell.setCellValue((long) column);
                    } else if (column instanceof Double || column instanceof Float) {
                        cell.setCellValue((double) column);
                    } else if (column instanceof Date) {
                        cell.setCellStyle(styles.get("date"));
                        cell.setCellValue((Date) column);
                    } else {
                        cell.setCellStyle(styles.get("wrap"));
                        cell.setCellValue(column.toString());
                    }

                }
                columnNumber++;
            }

            rowNumber++;
        }

        workbook.write(outputStream);
    }

    private void createStyles(Map<String, CellStyle> styles,
                              Workbook workbook) {
        if (!styles.containsKey("header")) {
            final CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            //style.setFillPattern(style.SOLID_FOREGROUND);

            final Font font = workbook.createFont();
            font.setColor((short) 9);
            style.setFont(font);
            styles.put("header", style);
        }

        if (!styles.containsKey("wrap")) {
            final CellStyle wrapStyle = workbook.createCellStyle();
            wrapStyle.setWrapText(true);
            styles.put("wrap", wrapStyle);
        }

        if (!styles.containsKey("date")) {
            final DataFormat dateFormat = workbook.createDataFormat();
            final CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(dateFormat.getFormat("d/mm/yy"));
            styles.put("date", dateStyle);
        }

    }

    private void setColumnSizes(XSSFSheet sheet, List<String> spreadsheetHeader) {
        int i = 0;
        for (String headerColumn : spreadsheetHeader) {
            sheet.setColumnWidth(i, DEFAULT_COLUMN_SIZE);
            i++;
        }
    }

    private void createHeader(Map<String, CellStyle> styles,
                              XSSFSheet sheet,
                              List<String> header) {
        final XSSFRow rowhead = sheet.createRow(0);

        int i = 0;
        for (String headerColumn : header) {
            rowhead.createCell(i).setCellValue(new XSSFRichTextString(headerColumn));
            rowhead.getCell(i).setCellStyle(styles.get("header"));
            i++;
        }
    }
}
