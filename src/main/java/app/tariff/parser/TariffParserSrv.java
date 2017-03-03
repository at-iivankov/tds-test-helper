package app.tariff.parser;

/**
 * Сервис для парсинга тарифов
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TariffParserSrv {
    public static TariffParserDo parseTariff(TariffParserDo tariffParserDo) throws Exception {
        TariffParserBo tariffParserBo = new TariffParserBo();
        return tariffParserBo.parse(tariffParserDo);
    }
}
