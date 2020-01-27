package br.com.impex.converter;

import br.com.impex.domain.Address;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ExcelToImpexConveter {
    private static final String SAMPLE_EXCEL_FILE = "/home/themarkiron/Desktop/impexTestConverter.xlsx";
    private static final String INSERT_UPDATE = " INSERT_UPDATE ";

    public static void main(String[] args) throws IOException, InvalidFormatException {


        Workbook workbook = WorkbookFactory.create( new File(SAMPLE_EXCEL_FILE));

        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        List<String> impexConverter = new ArrayList<>();
        int  fields = Address.class.getDeclaredFields().length;

        StringBuilder impexInsert = new StringBuilder();
        impexInsert.append(insertUpdate(Address.class));

        List<Address> addresses = new ArrayList<>();

        Sheet createSheet = workbook.createSheet("Address");

        Row rows = createSheet.createRow(0);
        rows.createCell(0).setCellValue(insertUpdate(Address.class).toString());

        for(int i =0; i < fields; i++){
             impexInsert.append(Address.class.getDeclaredFields()[i].getName()).append(";");
             rows.createCell(i+1).setCellValue(Address.class.getDeclaredFields()[i].getName() +  ";");
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("/home/themarkiron/Desktop/poi-generated-file2.xlsx");
        workbook.write(fileOut);
        fileOut.close();



        StringBuilder linesImpex = new StringBuilder();

        sheet.forEach(row -> {
            row.forEach(cell -> {
                String cellValue = dataFormatter.formatCellValue(cell);
                linesImpex.append(cellValue).append(";");

            });
            impexConverter.add(linesImpex.toString());
            linesImpex.delete(0,linesImpex.length());
        });

        for(String string : impexConverter){
            impexInsert.append("\n").append(string);
        }
        System.out.println(impexInsert);

    }

    public static StringBuilder insertUpdate(Class cls){
        return new StringBuilder().append( INSERT_UPDATE + cls.getSimpleName() ).append(";");
    }

}
