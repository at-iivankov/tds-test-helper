package app.photo.articles.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Парсинг отчета XML
 *
 * @author Ivan Ivankov {@literal <iivankov@rtech.ru>}
 */
public class PhotoArticlesParserBo {

    public PhotoArticlesParserDo parse(PhotoArticlesParserDo photoArticlesParserDo) throws Exception {
        // TODO: заменить путь на переданный с формы после тестирования
        File inputFile = new File("D://test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        List<PhotoArticle> photoArticleList = new ArrayList<>();

        NodeList photoArticles = doc.getElementsByTagName("photoArticle");
        for (int i = 0; i < photoArticles.getLength(); i++) {
            PhotoArticle photoArticle = new PhotoArticle();
            Node photoArticleNode = photoArticles.item(i);
            Element photoArticleElement = (Element) photoArticleNode;
            photoArticle.setName(photoArticleElement.getElementsByTagName("name").item(0).getTextContent());
            photoArticle.setDate(photoArticleElement.getElementsByTagName("date").item(0).getTextContent());
            photoArticle.setSite(photoArticleElement.getElementsByTagName("region").item(0).getTextContent());
            photoArticle.setSlug(photoArticleElement.getElementsByTagName("url").item(0).getTextContent());
            NodeList images = ((Element) photoArticleNode).getElementsByTagName("image");
            for (int j = 0; j < images.getLength(); j++) {
                photoArticle.setImagesId(images.item(j).getTextContent());
            }
            photoArticleList.add(photoArticle);
        }
        photoArticlesParserDo.setCsvData(getCsvData(photoArticleList));
        return photoArticlesParserDo;
    }

    // TODO: уточнить названия полей и формат даты
    private String getCsvData(List<PhotoArticle> photoArticleList){
        String idPrefix = "photoArticle";
        int startId = 1000;
        String result = "/atg/commerce/catalog/SecureProductCatalog:tariff, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,slug,name,date,images\n";
        for(PhotoArticle photoArticle : photoArticleList) {
            result += idPrefix + startId + ",";
            result += photoArticle.getSlug() + ",";
            result += photoArticle.getName() + ",";
            result += photoArticle.getDate() + ",";
            result += photoArticle.getImagesId() + "\n";
            startId++;
        }
        return result;
    }

    private static class PhotoArticle {
        private String name;
        private String date;
        private String site;
        private String slug;
        private List<String> imagesId;
        private static Map<String, String> ID_MAP;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String dateStr) throws Exception {
            SimpleDateFormat formatter = null;
            if(dateStr.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$"))
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            else throw new Exception();
            // TODO: продумать исключения

            Date date = formatter.parse(dateStr);
            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            this.date = formatter.format(date);
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            // TODO: должно ли значение site заполняться всеми siteID, если site = siteFEDERAL
            // TODO: уточнить количество регионов и их siteID для заполнения site
            if(site.equals("siteFEDERAL"))
                this.site = "Все siteID";
            else this.site = site;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            // TODO: уточнить такие ли должны быть skug-и
            char[] slugArray = slug.toCharArray();
            if (slug.substring(slug.length() - 1, slug.length()).equals("/")) {
                String result = "";
                for (int i = slug.length() - 2; i >= 0; i--) {
                    if (slugArray[i] == '/')
                        break;
                    result += slugArray[i];
                }
                this.slug = new StringBuilder(result).reverse().toString();
            } else this.slug = "";
        }

        public String getImagesId() {
            String imagesIdStr = "";
            for(String imageId : imagesId) {
                imageId += imagesId + ",";
            }
            return (imagesIdStr.length() > 2) ? imagesIdStr.substring(0, imagesIdStr.length() - 1) : "";
        }

        public void setImagesId(String imageUrl) throws IOException {
            // TODO: необходима выгрузка csv из bcc и преобразование
            String imageName;
            char[] imageUrlArray = imageUrl.toCharArray();
            if (imageUrl.contains("/")) {
                String result = "";
                for (int i = imageUrl.length() - 1; i >= 0; i--) {
                    if (imageUrlArray[i] == '/')
                        break;
                    result += imageUrlArray[i];
                }
                imageName = new StringBuilder(result).reverse().toString();
            } else imageName = "";
            setMapId();
            /*
            Предполагается следующее:
            1) столбцы выгруженного из bcc файла должны быть в следующем порядке: url,id
            2) в файле нет строк, кроме последовательнсоти url,id
            3) последовательность url,id разделяется символом "," без пробелов
             */
            this.imagesId.add(ID_MAP.get(imageName));
        }

        private void setMapId() throws IOException {
            String line;
            // TODO: заменить путь на переданный с формы после тестирования
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D://test1.csv"));
            while ((line = bufferedReader.readLine()) != null) {
                ID_MAP.put(line.split(",")[0], line.split(",")[1]);
            }
        }
    }
}
