package app.officeparser;

import java.util.HashMap;

/**
 * <Description>
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeDo {

    private String name;
    private String city;
    private String address;
    private String geocode;
    private String latitude;
    private String longitude;
    private String officeType;
    private HashMap<String, String[]> scheduleHashMap;
    private String scheduleString;
    private String mondaySchedule;
    private String tuesdaySchedule;
    private String wednesdaySchedule;
    private String thursdaySchedule;
    private String fridaySchedule;
    private String saturdaySchedule;
    private String sundaySchedule;
    private String serviceString;
    private boolean isService1;
    private boolean isService2;
    private boolean isService3;
    private boolean isService4;
    private boolean isService5;
    private boolean isService6;
    private boolean isService7;

    public OfficeDo() {
        scheduleHashMap = new HashMap<String, String[]>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getOfficeType() {
        return officeType;
    }

    public void setOfficeType(String officeType) {
        this.officeType = officeType;
    }

    public HashMap<String, String[]> getScheduleHashMap() {
        return scheduleHashMap;
    }

    public String getScheduleString() {
        return scheduleString;
    }

    public void setScheduleString(String scheduleString) {
        this.scheduleString = scheduleString;
    }

    public void setMondaySchedule(String mondaySchedule) {
        this.mondaySchedule = mondaySchedule;
    }


    public void setTuesdaySchedule(String tuesdaySchedule) {
        this.tuesdaySchedule = tuesdaySchedule;
    }


    public void setWednesdaySchedule(String wednesdaySchedule) {
        this.wednesdaySchedule = wednesdaySchedule;
    }


    public void setThursdaySchedule(String thursdaySchedule) {
        this.thursdaySchedule = thursdaySchedule;
    }


    public void setFridaySchedule(String fridaySchedule) {
        this.fridaySchedule = fridaySchedule;
    }


    public void setSaturdaySchedule(String saturdaySchedule) {
        this.saturdaySchedule = saturdaySchedule;
    }


    public void setSundaySchedule(String sundaySchedule) {
        this.sundaySchedule = sundaySchedule;
    }

    public String getServiceString() {
        return serviceString;
    }

    public void setServiceString(String serviceString) {
        this.serviceString = serviceString;
    }

    public boolean isService1() {
        return isService1;
    }

    public void setService1(String service1Value) {
        isService1 = service1Value.equals("да");
    }

    public boolean isService2() {
        return isService2;
    }

    public void setService2(String service2Value) {
        isService2 = service2Value.equals("да");
    }

    public boolean isService3() {
        return isService3;
    }

    public void setService3(String service3Value) {
        isService3 = service3Value.equals("да");
    }

    public boolean isService4() {
        return isService4;
    }

    public void setService4(String service4Value) {
        isService4 = service4Value.equals("да");
    }

    public boolean isService5() {
        return isService5;
    }

    public void setService5(String service5Value) {
        isService5 = service5Value.equals("да");
    }

    public boolean isService6() {
        return isService6;
    }

    public void setService6(String service6Value) {
        isService6 = service6Value.equals("да");
    }

    public boolean isService7() {
        return isService7;
    }

    public void setService7(String service7Value) {
        isService7 = service7Value.equals("да");
    }

    public void calculateLatitudeAndLongitude() {
        String [] geocodeArray = geocode.split(",");
        latitude = geocodeArray[0];
        longitude = geocodeArray[1];
    }

    public void fillScheduleHashMap() {
        if (!mondaySchedule.equals("нет"))
            scheduleHashMap.put("monday", mondaySchedule.split(";"));
        if (!tuesdaySchedule.equals("нет"))
            scheduleHashMap.put("tuesday", tuesdaySchedule.split(";"));
        if (!wednesdaySchedule.equals("нет"))
            scheduleHashMap.put("wednesday", wednesdaySchedule.split(";"));
        if (!thursdaySchedule.equals("нет"))
            scheduleHashMap.put("thursday", thursdaySchedule.split(";"));
        if (!fridaySchedule.equals("нет"))
            scheduleHashMap.put("friday", fridaySchedule.split(";"));
        if (!saturdaySchedule.equals("нет"))
            scheduleHashMap.put("saturday", saturdaySchedule.split(";"));
        if (!sundaySchedule.equals("нет"))
            scheduleHashMap.put("sunday", sundaySchedule.split(";"));
    }
}
