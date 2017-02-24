package app.optionparser;

import org.apache.commons.codec.binary.Base64;

/**
 * Data Object для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserDo {

    private String pathToOption;
    private String id;
    private String name;
    private String description;
    private String slug;
    private String type;
    private String region;
    private String csvData;

    public OptionParserDo () {
    }

    public OptionParserDo (String pathToOption, String id) {
        this.pathToOption = pathToOption;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name.equals(""))
            throw new Exception();
        this.name = name.replace("\"", "\"\"");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws Exception  {
        if(type.contains("B2B")) {
            this.type = "b2b";
        } else if (type.contains("B2C")) {
            this.type = "b2c";
        } else this.type = null;
    }

    public String getPathToOption() {
        return pathToOption;
    }

    public void setPathToOption(String pathToOption) {
        this.pathToOption = pathToOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug)  throws Exception {
        if(slug.equals(""))
            throw new Exception();
        slug = slug.replace("\"", "\"\"");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if(description.equals(""))
            throw new Exception();
        this.description = new String(Base64.decodeBase64(description)).replace("\"", "\"\"");
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String value) {
        if(value.equals("Москва") || value.equals("Москва и Московская область"))
            this.region = "MSK";
        else this.region = null;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    public String getCsvData() {
        return csvData;
    }
}
