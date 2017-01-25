package app.tariffparser;

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
 * Business Object для парсинга архивных тарифов
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TariffParserBo {

    public TariffParserDo parse(TariffParserDo tariffData) throws IOException, ParseException {
        Iterator<Row> rowIterator = getXlsxIterator(tariffData.getPathToTariff());
        String value = "";
        int rowCounter = 0;
        List<TariffParserDo> tariffParserDoList = new ArrayList<TariffParserDo>();
        while (rowIterator.hasNext()) {
            rowCounter++;
            Row row = rowIterator.next();
            int columnCounter = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            TariffParserDo tariffParserDo = new TariffParserDo();
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
                        tariffParserDo.setName(value);
                    if (columnCounter == 2)
                        tariffParserDo.setDescription(value);
                    if (columnCounter == 3)
                        tariffParserDo.setSlug(value);
                    if (columnCounter == 4)
                        tariffParserDo.setType(value);
                    if (columnCounter == 5) {
                        tariffParserDo.setParentFolder(value);
                        tariffParserDoList.add(tariffParserDo);
                    }
                }
            } catch (Exception e) {
                tariffParserDo.setStrNumber(rowCounter);
            }
            if ((value.equals("ERROR")) && (columnCounter == 1))
                break;
        }

        String result = "";
        result += "//atg/commerce/catalog/SecureProductCatalog:tariff, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,displayName,frontName,isArchive,isMigrated,migratedDescription,slug,clientType\n";
        int startId = tariffData.getId();
        for(TariffParserDo tariffParserDo : tariffParserDoList) {
            if(tariffParserDo.getStrNumber() == 0 && tariffParserDo.getParentFolder() != null)
                result += "\"" + "option"  + (startId++) + "\"" + "\"" + tariffParserDo.getName() + "\"" + "\"" +
                        tariffParserDo.getName() + "\"," + "true, true," + "\"" + tariffParserDo.getDescription() +
                        "\"," + "\"" + tariffParserDo.getType() + "\"\n";
        }
        result += "\n\n\n\nERROR STRING NUMBERS:\n\n";
        for(TariffParserDo tariffParserDo : tariffParserDoList) {
            if(tariffParserDo.getStrNumber() != 0 && tariffParserDo.getParentFolder() != null)
                result += tariffParserDo.getStrNumber() + ", ";
        }
        tariffData.setCsvData(result);
        return tariffData;
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
