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

    /**
     * Исключения, на данный момент невозможно пролить email, привязанные к нескольким сайтам
     */
    private List<String> exceptionList = new ArrayList<String>(
            Arrays.asList(
                    "vova.latypov.2016@",
                    "sergey-mal@",
                    "fr.d@",
                    "jane030398@",
                    "julia.simonova@",
                    "smajjl31@",
                    "laz.sasha.9227@",
                    "nimavmk@",
                    "vvsorokin@",
                    "oleg-solodchuk@",
                    "igor-bs@",
                    "juk.ko4uroff@",
                    "veronichka3393@",
                    "romankil@",
                    "vladimirsimb@",
                    "alexvdo24@",
                    "an6555@",
                    "mazavmk@",
                    "af.den@",
                    "siprohorov@",
                    "otman965@",
                    "e.nosov1978@",
                    "ralf8822@",
                    "andrewtishkin@",
                    "xovseym@",
                    "xovsevs@",
                    "ppi1@",
                    "tele.mir@",
                    "sergej.rodiono@",
                    "hsuleo@",
                    "evgenia.kvak@",
                    "vibora111@",
                    "Polad.esgerov@",
                    "mzawse@",
                    "mavezys@",
                    "mavmoza@",
                    "mozavse@",
                    "mavmop@",
                    "mazamve@",
                    "tatnew@",
                    "barada-ch@",
                    "zamemss@",
                    "maximvahnin@",
                    "vsexove@",
                    "xobimo@",
                    "iphonesales2014@",
                    "ebner2@",
                    "oxana.mikhaylova@",
                    "evgenijplus@",
                    "actioncar@",
                    "roman@",
                    "Sergey.Chibirev@",
                    "dementiew.con@",
                    "mavinw@",
                    "sergey@",
                    "n954027@",
                    "Valder1984@",
                    "cordura@",
                    "Olga.Gorodnicheva@",
                    "kizrin.misha@",
                    "aleksejpopov946@",
                    "sample@",
                    "mazavems@",
                    "wsexoym@",
                    "mavezxo@",
                    "zavsems@",
                    "vsezavm@",
                    "farfar@",
                    "nikitagnimry@",
                    "Sonik1525@",
                    "winter@",
                    "vseotum@",
                    "xobyvm@",
                    "biofuse@",
                    "mlunkin@",
                    "turunen@",
                    "belyakov-68@",
                    "dmitrii_ovchinnikov_2013@",
                    "tele.mail@",
                    "sermel@",
                    "vsebuz@",
                    "ta16237@",
                    "slavik_25@",
                    "an4opon4o@",
                    "mavnims@",
                    "mazavesm@",
                    "mamsxo@",
                    "vsezaym@",
                    "xobuvmo@",
                    "mavexom@",
                    "mavezae@",
                    "mazavex@",
                    "office@",
                    "ulianadurganova665@",
                    "mavexo21@",
                    "yss-11@",
                    "kariukovsky2010@",
                    "kolyn41@",
                    "mobyxo@",
                    "movsxo@",
                    "tiagr@",
                    "alaev_av@",
                    "mavsezaj@",
                    "zavseym@",
                    "xovseyw@",
                    "kazim9060@",
                    "reanalog@",
                    "tik17@",
                    "uksanochka@",
                    "wvcd@",
                    "mavemsk@",
                    "maxowms@",
                    "xovmoss@",
                    "lebedevpv@",
                    "dyachenko105.dyachenko@",
                    "sbnext@",
                    "ssm-boss@",
                    "sapalex3@",
                    "movsza@",
                    "crazy_desant@",
                    "vovaisus@",
                    "miha.pahomov.1999@",
                    "info@",
                    "polina.ugryumova@",
                    "vsezamum@",
                    "miron140165@",
                    "Djdvoynikov@",
                    "79083106544@",
                    "shevchenkoandrey63@",
                    "ekuchaev@",
                    "Kompers-km@",
                    "mavmoxo@",
                    "levin1970.levinson@",
                    "mabyxo@",
                    "chaykovskiy.aleksandr@",
                    "vsezamo@",
                    "zavsexo@",
                    "e.pitko@",
                    "filinsky1982@",
                    "konstantin.vaulin@",
                    "plot.vas@",
                    "tele2.34@",
                    "xomavm@",
                    "zhusova1966@",
                    "VVV1984VVV@",
                    "londaru@",
                    "eyukin037@",
                    "Ment82@",
                    "konstantin.108@",
                    "maniws@",
                    "m@",
                    "eyukin63@",
                    "maxowez@",
                    "atvkino@",
                    "ad0076@",
                    "ylanov213@",
                    "tarybaev.talgat@",
                    "mavemk21@",
                    "pavel@",
                    "admin@",
                    "movseza@",
                    "Arkady.Kiselev@",
                    "ivan_st@",
                    "ustinovmyu@",
                    "alena.vecherko.86@",
                    "kirillnov31@",
                    "mihail037@"
            )
    );


    /**
     * Временное решение, из-за исключений, которые на данный момент невозможно пролить email, привязанные к нескольким сайтам
     */
    private static final Map<String, Integer> REGION_PRIORITY_MAP = new HashMap<String, Integer>() {{
        put("siteMSK", 1);
        put("siteSPB", 2);
        put("siteTULA", 3);
        put("siteVORONEZH", 4);
        put("siteROSTOV", 5);
        put("siteNNOV", 6);
        put("siteCHELYABINSK", 7);
        put("siteNOVOSIBIRSK", 8);
        put("siteKRASNOYARSK", 9);
        // Остальные приоритеты
        put("siteARH", 10);
        put("siteBARNAUL", 11);
        put("siteBELGOROD", 12);
        put("siteBRYANSK", 13);
        put("siteBURYATIA", 14);
        put("siteCHUVASHIA", 15);
        put("siteEAO", 16);
        put("siteEKT", 17);
        put("siteHMAO", 18);
        put("siteIRKUTSK", 19);
        put("siteIZHEVSK", 20);
        put("siteKALININGRAD", 21);
        put("siteKALUGA", 22);
        put("siteKAMCHATKA", 23);
        put("siteKARELIA", 24);
        put("siteKAZAN", 25);
        put("siteKHAKASIA", 26);
        put("siteKIROV", 27);
        put("siteKOMI", 28);
        put("siteKOSTROMA", 29);
        put("siteKRASNODAR", 30);
        put("siteKURGAN", 31);
        put("siteKURSK", 32);
        put("siteLIPETSK", 33);
        put("siteMAGADAN", 34);
        put("siteMARIEL", 35);
        put("siteMORDOVIA", 36);
        put("siteMURMANSK", 37);
        put("siteNORILSK", 38);
        put("siteNOVGOROD", 39);
        put("siteOMSK", 40);
        put("siteOREL", 41);
        put("siteORENBURG", 42);
        put("sitePENZA", 43);
        put("sitePERM", 44);
        put("sitePSKOV", 45);
        put("siteRYAZAN", 46);
        put("siteSAKHALIN", 47);
        put("siteSAMARA", 48);
        put("siteSARATOV", 49);
        put("siteSMOLENSK", 50);
        put("siteTAMBOV", 51);
        put("siteTOMSK", 52);
        put("siteTVER", 53);
        put("siteTYUMEN", 54);
        put("siteULN", 55);
        put("siteVLADIMIR", 56);
        put("siteVLADIVOSTOK", 57);
        put("siteVOLGOGRAD", 58);
        put("siteVOLOGDA", 59);
        put("siteYANAO", 60);
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

        if(subscriptionDate.matches("^[0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{1,2}:[0-9]{2}$"))
            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if(subscriptionDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3}$"))
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

    public boolean isContainInExceptionList(String mail) {
        for(String exception: exceptionList) {
            if(mail.contains(exception))
                return true;
        }
        return false;
    }

    /**
     * Возвращает более приоритетный регион
     * @param firstRegion
     * @param secondregion
     * @return
     */
    public boolean isFirstRegionHigherPriority(String firstRegion, String secondregion) {
        return REGION_PRIORITY_MAP.get(firstRegion) < REGION_PRIORITY_MAP.get(secondregion);
    }

}
