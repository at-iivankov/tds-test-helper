package app.office.parser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeParserSrv {

    public static OfficeParserDo parseOffices(OfficeParserDo officeParserDo) throws Exception {
        OfficeParserBo officeParserBo = new OfficeParserBo();
        return officeParserBo.parse(officeParserDo);
    }
}
