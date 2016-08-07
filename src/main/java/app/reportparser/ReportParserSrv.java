package app.reportparser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class ReportParserSrv {

    public static ReportParserDo parseReport(String originalReport) throws Exception {
        ReportParserBo reportParserBo = new ReportParserBo(originalReport);
        return reportParserBo.parse();
    }
}
