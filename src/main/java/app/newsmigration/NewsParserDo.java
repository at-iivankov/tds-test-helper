package app.newsmigration;

import org.apache.commons.codec.binary.Base64;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data Object для парсинга новости
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserDo {

    private String pathToNews;
    private int strNumber;
    private int id;
    private boolean isFederal;
    private String slug;
    private String postDate;
    private String headline;
    private String description;
    private String siteIds;
    private String body;
    private String csvData;

    public NewsParserDo () {
    }

    public NewsParserDo (String pathToNews, boolean isFederal, int id) {
        this.pathToNews = pathToNews;
        this.isFederal = isFederal;
        this.id = id;
    }

    public int getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(int strNumber) {
        this.strNumber = strNumber;
    }

    public boolean isFederal() {
        return isFederal;
    }

    public String getPathToNews() {
        return pathToNews;
    }

    public void setPathToNews(String pathToNews) {
        this.pathToNews = pathToNews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getFederal() {
        return isFederal;
    }

    public void setFederal(boolean federal) {
        this.isFederal = federal;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        char [] slugArray = slug.toCharArray();
        if(slug.substring(slug.length() - 1, slug.length()).equals("/")) {
            String result = "";
            for(int i = slug.length() - 2; i >= 0; i--) {
                if (slugArray[i] == '/')
                    break;
                result += slugArray[i];
            }
            this.slug = new StringBuilder(result).reverse().toString();
        } else {
            this.slug = "";
        }
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) throws ParseException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date date = formatter.parse(postDate);
            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            this.postDate = formatter.format(date);
        } catch (Exception e) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date date = formatter.parse(postDate);
                formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                this.postDate = formatter.format(date);
            } catch (Exception ex) {
                this.postDate = "";
            }
        }

    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = new String(Base64.decodeBase64(description));;
    }

    public String getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(String siteIds) {
        if(isFederal)
            siteIds = "siteMSK, siteMURMANSK";
        if(siteIds.equals("Мурманск") || siteIds.equals("Мурманская область"))
            this.siteIds = "siteMURMANSK";
        else if(siteIds.equals("Москва") || siteIds.equals("Москва и Московская область"))
            this.siteIds = "siteMSK";
        else this.siteIds = null;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = new String(Base64.decodeBase64(body));
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    public String getCsvData() {
        return csvData;
    }
}
