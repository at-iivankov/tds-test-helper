package app.optionparser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Business Object для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserBo {

    public OptionParserDo parse(OptionParserDo optionData) throws IOException, ParseException {
        Iterator<Row> rowIterator = getXlsxIterator(optionData.getPathToOption());
        String value = "";
        int rowCounter = 0;
        List<OptionParserDo> optionParserDoList = new ArrayList<OptionParserDo>();
        while (rowIterator.hasNext()) {
            rowCounter++;
            Row row = rowIterator.next();
            int columnCounter = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            OptionParserDo optionParserDo = new OptionParserDo();
            try {
                while (cellIterator.hasNext()) {
                    columnCounter++;
                    Cell cell = cellIterator.next();
                    value = getCellValue(cell);
                    if ((value.equals("ERROR")) && (columnCounter == 1))
                        break;
                    if (columnCounter == 6)
                        break;

                    if (columnCounter == 1)
                        optionParserDo.setName(value);
                    if (columnCounter == 2)
                        optionParserDo.setDescription(value);
                    if (columnCounter == 3)
                        optionParserDo.setSlug(value);
                    if (columnCounter == 4)
                        optionParserDo.setType(value);
                    if (columnCounter == 5) {
                        optionParserDo.setParentFolder(value);
                        optionParserDoList.add(optionParserDo);
                    }
                }
            } catch (Exception e) {
                optionParserDo.setStrNumber(rowCounter);
            }
            if ((value.equals("ERROR")) && (columnCounter == 1))
                break;
        }

        String result = "";
        result += "//atg/commerce/catalog/SecureProductCatalog:option, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,displayName,frontName,isArchive,isMigrated,migratedDescription,slug,clientType\n";
        int startId = optionData.getId();
        for(OptionParserDo optionParserDo : optionParserDoList) {
            if(optionParserDo.getStrNumber() == 0 && optionParserDo.getParentFolder() != null)
                result += "\"" + "option"  + (startId++) + "\"" + "\"" + optionParserDo.getName() + "\"" + "\"" +
                        optionParserDo.getName() + "\"," + "true, true," + "\"" + optionParserDo.getDescription() +
                        "\"," + "\"" + optionParserDo.getType() + "\"\n";
        }
        result += "\n\n\n\nERROR STRING NUMBERS:\n\n";
        for(OptionParserDo optionParserDo : optionParserDoList) {
            if(optionParserDo.getStrNumber() != 0 && optionParserDo.getParentFolder() != null)
                result += optionParserDo.getStrNumber() + ", ";
        }
        optionData.setCsvData(result);
        return optionData;
    }

    private Iterator<Row> getXlsxIterator(String path) throws IOException {
        File myFile = new File(path);
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        return mySheet.iterator();
    }

    private String getCellValue(Cell cell) {
        String value;
        int cellType = cell.getCellType();
        //перебираем возможные типы ячеек
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = "" + cell.getNumericCellValue();
                break;
            default:
                value = "ERROR";
                break;
        }
        return value;
    }
}
