package app.reportparser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class ReportParserDo {

    private String parsedReport;

    public ReportParserDo(String parsedReport){
        this.parsedReport = parsedReport;
    }

    public String getParsedReport() {
        return parsedReport;
    }
}
