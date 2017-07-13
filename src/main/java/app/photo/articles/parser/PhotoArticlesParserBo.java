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
        File inputFile = new File(photoArticlesParserDo.getPathToPhotoArticlesXml());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        List<PhotoArticle> photoArticleList = new ArrayList<>();
        String photoArticlePrefix = "articleMgrPhoto";
        String imgPrefix = "imgMgrPhoto";
        int photoArticleCounter = 1;
        int imgCounter = 1;
        NodeList photoArticles = doc.getElementsByTagName("photoArticle");
        for (int i = 0; i < photoArticles.getLength(); i++) {
            PhotoArticle photoArticle = new PhotoArticle();
            Node photoArticleNode = photoArticles.item(i);
            Element photoArticleElement = (Element) photoArticleNode;
            photoArticle.setArticleId(photoArticlePrefix + photoArticleCounter);
            photoArticle.setName(photoArticleElement.getElementsByTagName("name").item(0).getTextContent());
            photoArticle.setDate(photoArticleElement.getElementsByTagName("date").item(0).getTextContent());
            photoArticle.setSite(photoArticleElement.getElementsByTagName("region").item(0).getTextContent());
            photoArticle.setSlug(photoArticleElement.getElementsByTagName("url").item(0).getTextContent());
            NodeList images = ((Element) photoArticleNode).getElementsByTagName("image");
            if(images.getLength() == 0) {
                //imgCounter++;
                continue;
            }
            for (int j = 0; j < images.getLength(); j++) {
                if(images.item(j).getTextContent().equals("")) {
                    imgCounter++;
                    continue;
                }
                photoArticle.setImagesId(imgPrefix + imgCounter);
                imgCounter ++;
            }
            photoArticleList.add(photoArticle);
            photoArticleCounter ++;
        }
        photoArticlesParserDo.setCsvData(getCsvData(photoArticleList));
        return photoArticlesParserDo;
    }

    private String getCsvData(List<PhotoArticle> photoArticleList){
        String result = "/atg/content/SecureContentManagementRepository:photoArticle, ,TIMEFORMAT=dd.MM.yyyy H:mm, ,LOCALE=ru_RU,\n" +
                "ID,displayName,name,postDate,headline,parentFolder,siteIds,slug,relatedMedia\n";
        for(PhotoArticle photoArticle : photoArticleList) {
            result += "\"" + photoArticle.getArticleId() + "\",";
            result += "\"" + photoArticle.getName().replace("\"", "\"\"") + "\",";
            result += "\"" + photoArticle.getName().replace("\"", "\"\"") + "\",";
            result += photoArticle.getDate() + ",";
            result += "\"" + photoArticle.getName().replace("\"", "\"\"") + "\",";
            result += "fldr1803"  + ",";
            result += photoArticle.getSite() + ",";
            result += photoArticle.getSlug() + ",";
            result += "\"" + photoArticle.getImagesId() + "\"\n";
        }
        return result;
    }

    private class PhotoArticle {
        private String articleId;
        private String name;
        private String date;
        private String site;
        private String slug;
        private List<String> imagesId;

        public PhotoArticle() {
            imagesId = new ArrayList<>();
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

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

            Date date = formatter.parse(dateStr);
            formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            this.date = formatter.format(date);
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            if(site.equals("teFEDERAL"))
                this.site = "";
            else this.site = site;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
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
                imagesIdStr += imageId + ",";
            }
            return (imagesIdStr.length() > 2) ? imagesIdStr.substring(0, imagesIdStr.length() - 1) : "";
        }

        public void setImagesId(String imgId) throws IOException {
            imagesId.add(imgId);
        }
    }
}
