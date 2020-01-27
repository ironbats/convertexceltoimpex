package br.com.impex.converter;

import br.com.impex.domain.Address;
import br.com.impex.domain.AddressBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelCreateImpexFromExcel {

    private static String[] columns = {"Name", "Email", "Date Of Birth", "Salary"};
    private static List<AddressBuilder> addresses =  new ArrayList<>();

static {

    addresses.add(AddressBuilder.builder().code(1).addressName("teste").owner(5).province("teste").build());
    addresses.add(AddressBuilder.builder().code(2).addressName("teste1").owner(4).province("teste").build());
    addresses.add(AddressBuilder.builder().code(3).addressName("teste2").owner(3).province("teste").build());
    addresses.add(AddressBuilder.builder().code(4).addressName("teste3").owner(2).province("teste").build());
    addresses.add(AddressBuilder.builder().code(5).addressName("teste4").owner(1).province("teste").build());
}

    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Address");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());


        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);


        Row headerRow = sheet.createRow(0);


        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(AddressBuilder address: addresses) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(address.getCode());

            row.createCell(1)
                    .setCellValue(address.getAddressName());

            Cell dateOfBirthCell = row.createCell(2);
            dateOfBirthCell.setCellValue(address.getProvince());
            dateOfBirthCell.setCellStyle(dateCellStyle);

            row.createCell(3)
                    .setCellValue(address.getOwner());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("/home/themarkiron/Desktop/poi-generated-file.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();

    }
}
