package app.photo.articles.parser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class PhotoArticlesParserSrv {

    public static PhotoArticlesParserDo parse(PhotoArticlesParserDo photoArticlesParserDo) throws Exception {
        PhotoArticlesParserBo photoArticlesParserBo = new PhotoArticlesParserBo();
        return photoArticlesParserBo.parse(photoArticlesParserDo);
    }
}
