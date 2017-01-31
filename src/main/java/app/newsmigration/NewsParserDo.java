package app.newsmigration;

import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data Object для парсинга новости
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserDo {

    private String pathToNews;
    private String id;
    private boolean isFederal;
    private String slug;
    private String postDate;
    private String name;
    private String headline;
    private String description;
    private String siteIds;
    private String body;
    private String csvData;

    public NewsParserDo () {
    }

    public NewsParserDo (String pathToNews, boolean isFederal, String id) {
        this.pathToNews = pathToNews;
        this.isFederal = isFederal;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getFederal() {
        return isFederal;
    }

    public void setFederal(boolean federal) {
        this.isFederal = federal;
    }

    public String getSlug() throws Exception {
        return slug;
    }

    public void setSlug(String slug)  throws Exception{
        if(slug.equals(""))
            throw new Exception();
        slug = slug.replace("\"", "'");
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

    public String getPostDate() throws Exception {
        return postDate;
    }

    public void setPostDate(String value)  throws Exception {
        if(value.equals(""))
            throw new Exception();
        Pattern p = Pattern.compile("^(.+)\\/news\\/(.+?)\\/(.+?)\\/.*$");
        Matcher m = p.matcher(value);
        String year = "";
        String month = "";
        if (m.find()) {
            year = m.group(2).toString();
            month = m.group(3).toString();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = formatter.parse("01." + month + "." + year + " 00:00");
        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        this.postDate = formatter.format(date);



//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//            Date date = formatter.parse(postDate);
//            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//            this.postDate = formatter.format(date);
//        } catch (Exception e) {
//            try {
//                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//                Date date = formatter.parse(postDate);
//                formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//                this.postDate = formatter.format(date);
//            } catch (Exception ex) {
//                this.postDate = "";
//            }
//        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name.equals(""))
            throw new Exception();
        this.name = name.replace("\"", "\"\"");
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) throws Exception {
        if(headline.equals(""))
            throw new Exception();
        this.headline = headline.replace("\"", "\"\"");
    }

    public String getDescription() throws Exception {
        return description;
    }

    public void setDescription(String description) throws Exception {
        this.description = new String(Base64.decodeBase64(description)).replace("\"", "\"\"");
    }

    public String getSiteIds() throws Exception {
        return siteIds;
    }

    public void setSiteIds(String value) throws Exception {
        if(value.equals(""))
            throw new Exception();
        if(this.isFederal)
            this.siteIds = "siteMURMANSK";
        else {
            if (value.equals("Мурманск") || value.equals("Мурманская область"))
                this.siteIds = "siteMURMANSK";
            else this.siteIds = null;
        }
    }

    public String getBody() throws Exception {
        return body;
    }

    public void setBody(String body) throws Exception {
        if(body.equals(""))
            throw new Exception();
        this.body = new String(Base64.decodeBase64(body)).replace("\"", "\"\"");
    }

    public void setCsvData(String csvData) throws Exception {
        this.csvData = csvData;
    }

    public String getCsvData() throws Exception {
        return csvData;
    }
}
