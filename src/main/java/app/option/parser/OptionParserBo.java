package app.option.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business Object для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserBo {

    public OptionParserDo parse(OptionParserDo optionDo) throws Exception {
        String csvFile = optionDo.getPathToOption();
        String line = "";
        String cvsSplitBy = ";";
        String idPrefix = "m_option";
        int idCounter = Integer.parseInt(optionDo.getId());
        int columnCount = 5;
        int rowCounter = 0;
        String errorCounter = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        List<OptionParserDo> optionParserDoList = new ArrayList<OptionParserDo>();
        while ((line = bufferedReader.readLine()) != null) {
            if(rowCounter == 0) {
                rowCounter++;
                continue;
            }
            rowCounter++;
            OptionParserDo optionParserDo = new OptionParserDo();
            String[] row = null;
            try {
                row = line.split(cvsSplitBy);
            } catch (Exception e) {
                continue;
            }
            if(row == null || row.length != columnCount) {
                errorCounter += rowCounter + ", ";
                continue;
            }
            try {
                optionParserDo.setId(idPrefix + idCounter);
                optionParserDo.setName(row[0]);
                optionParserDo.setDescription(row[1]);
                optionParserDo.setSlug(row[2]);
                optionParserDo.setType(row[3]);
                optionParserDo.setRegion(row[4]);
                if(optionParserDo.getRegion() != null && optionParserDo.getType() != null) {
                    optionParserDoList.add(optionParserDo);
                    idCounter++;
                } else continue;
            }catch (Exception e) {
                errorCounter += rowCounter + ", ";
                continue;
            }
        }
        String result = "/atg/commerce/catalog/SecureProductCatalog:option, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,displayName,frontName,isArchive,isMigrated,migratedDescription,slug,clientType,childSKUs\n";
        for(OptionParserDo tariffParserDo : optionParserDoList) {
            result += tariffParserDo.getId() +
                    ",\"" + tariffParserDo.getName() + "\"," +
                    "\"" + tariffParserDo.getName() + "\"," +
                    "true, true," +
                    "\"" + tariffParserDo.getDescription() + "\"," +
                    "\"" + tariffParserDo.getSlug() + "\"," +
                    "\"" + tariffParserDo.getType() + "\"," +
                    "sku70095\n";
        }
        result += "ERRORS ROW NUMBER: " + errorCounter;
        optionDo.setCsvData(result);
        return optionDo;
    }
}
