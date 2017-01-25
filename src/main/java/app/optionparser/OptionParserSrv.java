package app.optionparser;

/**
 * Сервис для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserSrv {

    public static OptionParserDo parseOption(OptionParserDo optionParserDo) throws Exception {
        OptionParserBo optionParserBo = new OptionParserBo();
        return optionParserBo.parse(optionParserDo);
    }
}
