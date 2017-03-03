package app.office.parser;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class ScheduleDo {

    private String id;
    private String day;
    private String openTime;
    private String closeTime;
    private String timeInterval;
    private String objectType;
    String startSchedulePrefix = "impSched";

    public ScheduleDo() {
    }

    public ScheduleDo(String id, String day, String timeInterval, String objectType) {
        this.id = startSchedulePrefix + id;
        this.day = day;
        this.timeInterval = timeInterval;
        this.objectType = objectType;
        calculateOpenAndCloseTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void calculateTimeInterval() {
        timeInterval = openTime + "-" + closeTime;
    }

    public void calculateOpenAndCloseTime() {
        String [] timeIntervalArray = timeInterval.split("-");
        openTime = timeIntervalArray[0];
        closeTime = timeIntervalArray[1];
    }
}
