package app.news.migration;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data Object для парсинга новостей
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

    public void setPostDate(String postDateStr, String url)  throws Exception {
        if(url.equals("") || postDateStr.equals(""))
            throw new Exception();

        SimpleDateFormat formatter = null;

        if(postDateStr.matches("^[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{1,2}:[0-9]{2}:[0-9]{2}$"))
            formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        else if(postDateStr.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}$"))
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        else throw new Exception();

        Date date = formatter.parse(postDateStr);

        Pattern p = Pattern.compile("^(.+)\\/news\\/(.+?)\\/(.+?)\\/.*$");
        Matcher m = p.matcher(url);
        String year = "";
        String month = "";
        if (m.find()) {
            year = m.group(2).toString();
            month = m.group(3).toString();
        }

        formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        if(((date.getYear() + 1900) == Integer.parseInt(year)) && ((date.getMonth() + 1) == Integer.parseInt(month)))
            this.postDate = formatter.format(date);
        else {
            this.postDate = formatter.format(formatter.parse("01." + month + "." + year + " 00:00"));
        }
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
        // Удаляем все html - тэги из описания
        this.description = Jsoup.clean(new String(Base64.decodeBase64(description)).replace("\"", "\"\""), Whitelist.none());
    }

    public String getSiteIds() throws Exception {
        return siteIds;
    }

    public void setSiteIds(String value) throws Exception {
        if(value.equals(""))
            throw new Exception();
        if(this.isFederal)
            this.siteIds = "siteMSK, siteSPB, siteMURMANSK, siteNOVGOROD, siteARH, siteKALININGRAD, sitePSKOV, siteKARELIA, siteVOLOGDA, siteKOMI, siteTVER, siteVLADIMIR, siteKOSTROMA, siteRYAZAN, siteSMOLENSK, siteKALUGA, siteTULA";
        else {
            if (value.equals("Москва") || value.equals("Москва и Московская область"))
                this.siteIds = "siteMSK";
            else if (value.equals("Санкт-Петербург") || value.equals("Санкт-Петербург и Ленинградская область"))
                this.siteIds = "siteSPB";
            else if (value.equals("Мурманса") || value.equals("Мурманская область"))
                this.siteIds = "siteMURMANSK";
            else if (value.equals("Новгород") || value.equals("Новгородская область"))
                this.siteIds = "siteNOVGOROD";
            else if (value.equals("Архангельск") || value.equals("Архангельская область"))
                this.siteIds = "siteARH";
            else if (value.equals("Калининград") || value.equals("Калининградская область"))
                this.siteIds = "siteKALININGRAD";
            else if (value.equals("Псков") || value.equals("Псковская область"))
                this.siteIds = "sitePSKOV";
            else if (value.equals("Карелия") || value.equals("Республика Карелия"))
                this.siteIds = "siteKARELIA";
            else if (value.equals("Вологда") || value.equals("Вологодская область"))
                this.siteIds = "siteVOLOGDA";
            else if (value.equals("Коми") || value.equals("Республика Коми"))
                this.siteIds = "siteKOMI";
            else if (value.equals("Тверь") || value.equals("Тверская область"))
                this.siteIds = "siteTVER";
            else if (value.equals("Владимир") || value.equals("Владимирская область"))
                this.siteIds = "siteVLADIMIR";
            else if (value.equals("Кострома") || value.equals("Костромская область"))
                this.siteIds = "siteKOSTROMA";
            else if (value.equals("Рязань") || value.equals("Рязанская область"))
                this.siteIds = "siteRYAZAN";
            else if (value.equals("Смоленск") || value.equals("Смоленская область"))
                this.siteIds = "siteSMOLENSK";
            else if (value.equals("Калуга") || value.equals("Калужская область"))
                this.siteIds = "siteKALUGA";
            else if (value.equals("Тула") || value.equals("Тульская область"))
                this.siteIds = "siteTULA";
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
