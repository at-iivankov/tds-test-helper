package app.tariffparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Business Object для парсинга архивных тарифов
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TariffParserBo {

    public TariffParserDo parse(TariffParserDo tariffDo) throws Exception {
        String csvFile = tariffDo.getPathToTariff();
        String line = "";
        String cvsSplitBy = ";";
        String idPrefix = "m_tariff";
        int idCounter = Integer.parseInt(tariffDo.getId());
        int columnCount = 5;
        int rowCounter = 0;
        String errorCounter = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        List<TariffParserDo> tariffParserDoList = new ArrayList<TariffParserDo>();
        while ((line = bufferedReader.readLine()) != null) {
            if(rowCounter == 0) {
                rowCounter++;
                continue;
            }
            rowCounter++;
            TariffParserDo tariffParserDo = new TariffParserDo();
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
                tariffParserDo.setId(idPrefix + idCounter);
                tariffParserDo.setName(row[0]);
                tariffParserDo.setDescription(row[1]);
                tariffParserDo.setSlug(row[2]);
                tariffParserDo.setType(row[3]);
                tariffParserDo.setRegion(row[4]);
                if(tariffParserDo.getRegion() != null && tariffParserDo.getType() != null) {
                    tariffParserDoList.add(tariffParserDo);
                    idCounter++;
                } else continue;
            }catch (Exception e) {
                errorCounter += rowCounter + ", ";
                continue;
            }
        }
        String result = "/atg/commerce/catalog/SecureProductCatalog:tariff, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,displayName,frontName,isArchive,isMigrated,migratedDescription,slug,clientType,childSKUs\n";
        for(TariffParserDo tariffParserDo : tariffParserDoList) {
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
        tariffDo.setCsvData(result);
        return tariffDo;
    }
}
