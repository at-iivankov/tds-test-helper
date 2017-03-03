package app.news.migration;

/**
 * Сервис для парсинга новостей
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserSrv {

    public static NewsParserDo parseNews(NewsParserDo newsParserDo) throws Exception {
        NewsParserBo newsParserBo = new NewsParserBo();
        return newsParserBo.parse(newsParserDo);
    }
}
