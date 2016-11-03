package app.officeparser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeParserDo {

    private String csvData;
    private String pathToSchedule;
    private boolean isSetPath;
    private String pathToOffices;
    private String regionCode;
    private int startScheduleId;
    private int startOfficeId;

    public OfficeParserDo(String pathToSchedule, boolean isSetPath, String pathToOffices, String regionCode, int startScheduleId, int startOfficeId) {
        this.pathToSchedule = pathToSchedule;
        this.isSetPath = isSetPath;
        this.pathToOffices = pathToOffices;
        this.regionCode = regionCode;
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

    public boolean isSetPath() {
        return isSetPath;
    }

    public String getPathToOffices() {
        return pathToOffices;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public int getStartScheduleId() {
        return startScheduleId;
    }

    public int getStartOfficeId() {
        return startOfficeId;
    }
}
