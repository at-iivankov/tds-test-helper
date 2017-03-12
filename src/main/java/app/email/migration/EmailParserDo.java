package app.email.migration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Data Object для парсинга email
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class EmailParserDo {

    private String pathToFile;
    private String email;
    private String subscriptionDate;
    private String siteId;
    private String xmlData;
    private static final Map<String,String> REGION_MAP = new HashMap<String , String>() {{
        put("arh", "siteARH");
        put("barnaul", "siteBARNAUL");
        put("belgorod", "siteBELGOROD");
        put("bryansk", "siteBRYANSK");
        put("buryatia", "siteBURYATIA");
        put("chelyabinsk", "siteCHELYABINSK");
        put("chuvashia", "siteCHUVASHIA");
        put("eao", "siteEAO");
        put("ekt", "siteEKT");
        put("en", "NULL");
        put("hmao", "siteHMAO");
        put("irkutsk", "siteIRKUTSK");
        put("izhevsk", "siteIZHEVSK");
        put("kaliningrad", "siteKALININGRAD");
        put("kaluga", "siteKALUGA");
        put("kamchatka", "siteKAMCHATKA");
        put("karelia", "siteKARELIA");
        put("kazan", "siteKAZAN");
        put("khakasia", "siteKHAKASIA");
        put("kirov", "siteKIROV");
        put("komi", "siteKOMI");
        put("kostroma", "siteKOSTROMA");
        put("krasnodar", "siteKRASNODAR");
        put("krasnoyarsk", "siteKRASNOYARSK");
        put("kurgan", "siteKURGAN");
        put("kursk", "siteKURSK");
        put("kuzbass", "NULL");
        put("lipetsk", "siteLIPETSK");
        put("magadan", "siteMAGADAN");
        put("mariel", "siteMARIEL");
        put("mordovia", "siteMORDOVIA");
        put("msk", "siteMSK");
        put("murmansk", "siteMURMANSK");
        put("newtestregion", "NULL");
        put("nnov", "siteNNOV");
        put("norilsk", "siteNORILSK");
        put("novgorod", "siteNOVGOROD");
        put("novosibirsk", "siteNOVOSIBIRSK");
        put("old-msk", "NULL");
        put("old-spb", "NULL");
        put("omsk", "siteOMSK");
        put("orel", "siteOREL");
        put("orenburg", "siteORENBURG");
        put("penza", "sitePENZA");
        put("perm", "sitePERM");
        put("pskov", "sitePSKOV");
        put("rostov", "siteROSTOV");
        put("ryazan", "siteRYAZAN");
        put("sakhalin", "siteSAKHALIN");
        put("samara", "siteSAMARA");
        put("saratov", "siteSARATOV");
        put("smolensk", "siteSMOLENSK");
        put("spb", "siteSPB");
        put("tambov", "siteTAMBOV");
        put("testregion", "NULL");
        put("tomsk", "siteTOMSK");
        put("tula", "siteTULA");
        put("tver", "siteTVER");
        put("tyumen", "siteTYUMEN");
        put("uln", "siteULN");
        put("vladimir", "siteVLADIMIR");
        put("vladivostok", "siteVLADIVOSTOK");
        put("volgograd", "siteVOLGOGRAD");
        put("vologda", "siteVOLOGDA");
        put("voronezh", "siteVORONEZH");
        put("yanao", "siteYANAO");
    }};

    public EmailParserDo() {
    }

    public EmailParserDo(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) throws ParseException {
        SimpleDateFormat formatter = null;

        if(subscriptionDate.matches("^[0-9]{2}\\.[0-9]{2}\\.[0-9]{4} [0-9]{1,2}:[0-9]{2}$"))
            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if(subscriptionDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}$"))
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date date = formatter.parse(subscriptionDate);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.subscriptionDate = formatter.format(date);
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        try {
            this.siteId = REGION_MAP.get(siteId);
        } catch (Exception e) {
            this.siteId = "NULL";
        }
    }

    public void setSiteIdDefault(String siteId) {
        this.siteId = siteId;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }
}
