package app.photo.articles.parser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class PhotoArticlesParserDo {

    private String pathToPhotoArticlesXml;
    private String pathToIdMapCsv;

    private String csvData;

    public PhotoArticlesParserDo(String pathToPhotoArticlesXml, String pathToIdMapCsv){
        this.pathToPhotoArticlesXml = pathToPhotoArticlesXml;
        this.pathToIdMapCsv = pathToIdMapCsv;
    }

    public String getPathToPhotoArticlesXml() {
        return pathToPhotoArticlesXml;
    }

    public String getPathToIdMapCsv() {
        return pathToIdMapCsv;
    }

    public String getCsvData() {
        return csvData;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }
}
