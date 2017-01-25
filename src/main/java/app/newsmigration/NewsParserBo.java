package app.newsmigration;

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
 * Business Object для парсинга новостей
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserBo {

    public NewsParserDo parse(NewsParserDo newsData) throws IOException, ParseException {
        Iterator<Row> rowIterator = getXlsxIterator(newsData.getPathToNews());
        String value = "";
        int rowCounter = 0;
        List<NewsParserDo> newsParserDoList = new ArrayList<NewsParserDo>();
        while (rowIterator.hasNext()) {
            rowCounter++;
            Row row = rowIterator.next();
            int columnCounter = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            NewsParserDo newsParserDo = new NewsParserDo();
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
                        newsParserDo.setHeadline(value);
                    if (columnCounter == 2)
                        newsParserDo.setPostDate(value);
                    if (columnCounter == 3)
                        newsParserDo.setBody(value);
                    if (columnCounter == 4)
                        newsParserDo.setDescription(value);
                    if (columnCounter == 5) {
                        newsParserDo.setSlug(value);
                        newsParserDoList.add(newsParserDo);
                    }
                }
            } catch (Exception e) {
                newsParserDo.setStrNumber(rowCounter);
            }
            if ((value.equals("ERROR")) && (columnCounter == 1))
                break;
        }

        String result = "";
        result += "/atg/content/SecureContentManagementRepository:newsArticle, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,postDate,headline,abstract,siteIds,body,isFederal,slug\n";
        int startId = newsData.getId();
        for(NewsParserDo newsParserDo : newsParserDoList) {
            if(newsParserDo.getStrNumber() == 0 && newsParserDo.getSiteIds() != null)
                result += "\"" + "news" + (startId++) + "\"," + "\"" + newsParserDo.getPostDate() + "\"," + "\"" +
                        newsParserDo.getHeadline() + "\"," + "\"" + newsParserDo.getDescription() + "\"," +
                        "\"" + newsParserDo.getSiteIds() + "\"," + "\"" + newsParserDo.getBody() + "\"," +
                        newsParserDo.isFederal() + ",\"" + newsParserDo.getSlug() + "\"\n";
        }
        result += "\n\n\n\nERROR STRING NUMBERS:\n\n";
        for(NewsParserDo newsParserDo : newsParserDoList) {
            if(newsParserDo.getStrNumber() != 0 && newsParserDo.getSiteIds() != null)
                result += newsParserDo.getStrNumber() + ", ";
        }
        newsData.setCsvData(result);
        return newsData;
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
