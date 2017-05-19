package app.option.parser;

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
        if (value.equals("Москва") || value.equals("Москва и Московская область"))
            this.region = "Msk";
        else if (value.equals("Санкт-Петербург") || value.equals("Санкт-Петербург и Ленинградская область"))
            this.region = "Spb";
        else if (value.equals("Мурманса") || value.equals("Мурманская область"))
            this.region = "Murmansk";
        else if (value.equals("Новгород") || value.equals("Новгородская область"))
            this.region = "Novgorod";
        else if (value.equals("Архангельск") || value.equals("Архангельская область"))
            this.region = "Arh";
        else if (value.equals("Калининград") || value.equals("Калининградская область"))
            this.region = "Kaliningrad";
        else if (value.equals("Псков") || value.equals("Псковская область"))
            this.region = "Pskov";
        else if (value.equals("Карелия") || value.equals("Республика Карелия"))
            this.region = "Karelia";
        else if (value.equals("Вологда") || value.equals("Вологодская область"))
            this.region = "Vologda";
        else if (value.equals("Коми") || value.equals("Республика Коми"))
            this.region = "Komi";
        else if (value.equals("Тверь") || value.equals("Тверская область"))
            this.region = "Tver";
        else if (value.equals("Владимир") || value.equals("Владимирская область"))
            this.region = "Vladimir";
        else if (value.equals("Кострома") || value.equals("Костромская область"))
            this.region = "Kostroma";
        else if (value.equals("Рязань") || value.equals("Рязанская область"))
            this.region = "Ryazan";
        else if (value.equals("Смоленск") || value.equals("Смоленская область"))
            this.region = "Smolensk";
        else if (value.equals("Калуга") || value.equals("Калужская область"))
            this.region = "Kaluga";
        else if (value.equals("Тула") || value.equals("Тульская область"))
            this.region = "Tula";
        else if (value.equals("Белгород") || value.equals("Белгородская область"))
            this.region = "Belgorod";
        else if (value.equals("Брянск") || value.equals("Брянская область"))
            this.region = "Bryansk";
        else if (value.equals("Челябинск") || value.equals("Челябинская область"))
            this.region = "Chelyabinsk";
        else if (value.equals("ЕАО") || value.equals("Еврейская АО"))
            this.region = "Eao";
        else if (value.equals("Удмуртия") || value.equals("Удмуртская Республика"))
            this.region = "Izhevsk";
        else if (value.equals("Камчатка") || value.equals("Камчатский край"))
            this.region = "Kamchatka";
        else if (value.equals("Кемерово") || value.equals("Кемеровская область"))
            this.region = "Kemerovo";
        else if (value.equals("Кировская область"))
            this.region = "Kirov";
        else if (value.equals("Краснодарский край") || value.equals("Краснодарский край и Республика Адыгея"))
            this.region = "Krasnodar";
        else if (value.equals("Курск") || value.equals("Курская область"))
            this.region = "Kursk";
        else if (value.equals("Липецк") || value.equals("Липецкая область"))
            this.region = "Lipetsk";
        else if (value.equals("Магадан") || value.equals("Магаданская область"))
            this.region = "Magadan";
        else if (value.equals("Ненецк") || value.equals("Ненецкий АО"))
            this.region = "Neneck";
        else if (value.equals("Нижний Новгород") || value.equals("Нижегородская область"))
            this.region = "Nnov";
        else if (value.equals("Новосибирск") || value.equals("Новосибирская область"))
            this.region = "Novosibirsk";
        else if (value.equals("Омск") || value.equals("Омская область"))
            this.region = "Omsk";
        else if (value.equals("Орел") || value.equals("Орловская область"))
            this.region = "Orel";
        else if (value.equals("Ростов") || value.equals("Ростовская область"))
            this.region = "Rostov";
        else if (value.equals("Сахалин") || value.equals("Сахалинская область"))
            this.region = "Sakhalin";
        else if (value.equals("Тамбов") || value.equals("Тамбовская область"))
            this.region = "Tambov";
        else if (value.equals("Томск") || value.equals("Томская область"))
            this.region = "Tomsk";
        else if (value.equals("Воронеж") || value.equals("Воронежская область"))
            this.region = "Voronezh";
        else if (value.equals("Алтай") || value.equals("Алтайский край"))
            this.region = "Barnaul";
        else if (value.equals("Бурятия") || value.equals("Республика Бурятия"))
            this.region = "Buryatia";
        else if (value.equals("Волгоград") || value.equals("Волгоградская область"))
            this.region = "Volgograd";
        else if (value.equals("Иркутск") || value.equals("Иркутская область"))
            this.region = "Irkutsk";
        else if (value.equals("Красноярский край") || value.equals("Красноярский край (кроме Норильска)"))
            this.region = "Krasnoyarsk";
        else if (value.equals("Курган") || value.equals("Курганская область"))
            this.region = "Kurgan";
        else if (value.equals("Марий Эл") || value.equals("Республика Марий Эл"))
            this.region = "Mariel";
        else if (value.equals("Мордовия") || value.equals("Республика Мордовия"))
            this.region = "Mordovia";
        else if (value.equals("Норильск") || value.equals("Красноярский край (Норильск)"))
            this.region = "Norilsk";
        else if (value.equals("Оренбургск") || value.equals("Оренбургская область"))
            this.region = "Orenburg";
        else if (value.equals("Пенза") || value.equals("Пензенская область"))
            this.region = "Penza";
        else if (value.equals("Пермь") || value.equals("Пермский край"))
            this.region = "Perm";
        else if (value.equals("Владивосток") || value.equals("Приморский край"))
            this.region = "Vladivostok";
        else if (value.equals("Саратов") || value.equals("Саратовская область"))
            this.region = "Saratov";
        else if (value.equals("Самара") || value.equals("Самарская область"))
            this.region = "Samara";
        else if (value.equals("Екатеринбург") || value.equals("Свердловская область"))
            this.region = "Ekt";
        else if (value.equals("Татарстан") || value.equals("Республика Татарстан"))
            this.region = "Kazan";
        else if (value.equals("Тюмень") || value.equals("Тюменская область"))
            this.region = "Tyumen";
        else if (value.equals("Ульяновск") || value.equals("Ульяновская область"))
            this.region = "Uln";
        else if (value.equals("Хакасия") || value.equals("Республика Хакасия"))
            this.region = "Khakasia";
        else if (value.equals("ХМАО") || value.equals("Ханты-Мансийский АО"))
            this.region = "Hmao";
        else if (value.equals("Чувашия") || value.equals("Чувашская Республика"))
            this.region = "Chuvashia";
        else if (value.equals("ЯНАО") || value.equals("Ямало-Ненецкий АО"))
            this.region = "Yanao";
        else this.region = null;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    public String getCsvData() {
        return csvData;
    }
}
