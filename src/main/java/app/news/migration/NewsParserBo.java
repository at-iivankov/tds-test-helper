package app.news.migration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business Object для парсинга новостей
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserBo {

    public NewsParserDo parse(NewsParserDo newsData) throws Exception {
        String csvFile = newsData.getPathToNews();
        String line = "";
        String cvsSplitBy = ";";
        String idPrefix = "news";
        int idCounter = Integer.parseInt(newsData.getId());
        int columnCount = 8;
        int rowCounter = 0;
        String errorCounter = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));
        List<NewsParserDo> newsParserDoList = new ArrayList<NewsParserDo>();
        while ((line = bufferedReader.readLine()) != null) {
            if(rowCounter == 0) {
               rowCounter++;
               continue;
            }
            rowCounter++;
            NewsParserDo newsParserDo = new NewsParserDo();
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
                newsParserDo.setId(idPrefix + idCounter);
                newsParserDo.setName(row[0]);
                newsParserDo.setHeadline(row[0]);
                newsParserDo.setBody(row[3]);
                newsParserDo.setDescription(row[4]);
                newsParserDo.setSlug(row[6]);
                newsParserDo.setPostDate(row[1], row[6]);
                newsParserDo.setFederal(newsData.isFederal());
                newsParserDo.setSiteIds(row[7]);
                if(newsParserDo.getSiteIds() != null) {
                    newsParserDoList.add(newsParserDo);
                    idCounter++;
                } else continue;
            }catch (Exception e) {
                errorCounter += rowCounter + ", ";
                continue;
            }
        }
        String result = "/atg/content/SecureContentManagementRepository:newsArticle, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,name,headline,body,abstract,postDate,siteIds,slug,isFederal\n";
        for(NewsParserDo newsParserDo : newsParserDoList) {
            result += newsParserDo.getId() +
                    ",\"" + newsParserDo.getName() + "\"," +
                    "\"" + newsParserDo.getHeadline() + "\"," +
                    "\"" + newsParserDo.getBody() + "\"," +
                    ((newsParserDo.getDescription().equals("")) ? "," : "\"" + newsParserDo.getDescription() + "\",") +
                    "\"" + newsParserDo.getPostDate() + "\"," +
                    "\"" + newsParserDo.getSiteIds() + "\"," +
                    "\"" + newsParserDo.getSlug() + "\"," +
                    newsParserDo.isFederal() +
                    "\n";
        }
        result += "ERRORS ROW NUMBER: " + errorCounter;
        newsData.setCsvData(result);
        return newsData;
    }
}
