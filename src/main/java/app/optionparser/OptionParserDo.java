package app.optionparser;

import org.apache.commons.codec.binary.Base64;

/**
 * Data Object для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserDo {

    private String pathToOption;
    private int strNumber;
    private int id;
    private String name;
    private String description;
    private String slug;
    private String type;
    private String parentFolder;
    private String csvData;

    public OptionParserDo () {
    }

    public OptionParserDo (String pathToOption, int id) {
        this.pathToOption = pathToOption;
        this.id = id;
    }

    public int getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(int strNumber) {
        this.strNumber = strNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type.contains("B2B")) {
            this.type = "b2b";
        } else if (type.contains("B2C")) {
            this.type = "b2c";
        } else this.type = "";
    }

    public String getPathToOption() {
        return pathToOption;
    }

    public void setPathToOption(String pathToOption) {
        this.pathToOption = pathToOption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = new String(Base64.decodeBase64(description));;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(String value) {
        if(value.equals("Мурманск") || value.equals("Мурманская область"))
            this.parentFolder = "MURMANSK";
        if(value.equals("Москва") || value.equals("Москва и Московская область"))
            this.parentFolder = "MSK";
        else this.parentFolder = null;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    public String getCsvData() {
        return csvData;
    }
}
