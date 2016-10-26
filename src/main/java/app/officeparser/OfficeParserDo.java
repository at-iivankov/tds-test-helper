package app.officeparser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeParserDo {

    private String csvData;
    private String pathToSchedule;
    private String pathToOffices;
    private int startScheduleId;
    private int startOfficeId;

    public OfficeParserDo(String pathToSchedule, String pathToOffices, int startScheduleId, int startOfficeId) {
        this.pathToSchedule = pathToSchedule;
        this.pathToOffices = pathToOffices;
        this.startScheduleId = startScheduleId;
        this.startOfficeId = startOfficeId;
    }

    public String getCsvData() {
        return csvData;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    public String getPathToSchedule() {
        return pathToSchedule;
    }

    public String getPathToOffices() {
        return pathToOffices;
    }

    public int getStartScheduleId() {
        return startScheduleId;
    }

    public int getStartOfficeId() {
        return startOfficeId;
    }
}
