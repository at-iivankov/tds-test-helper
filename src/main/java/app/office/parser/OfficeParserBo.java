package app.office.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Парсинг офисов продаж
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeParserBo {

    int startScheduleId = 0;
    int startOfficeId = 0;
    String startOfficePrefix = "impLoc";

    List<ScheduleDo> scheduleList = new ArrayList<ScheduleDo>();
    List<ScheduleDo> newScheduleList = new ArrayList<ScheduleDo>();
    List<OfficeDo> officeList = new ArrayList<OfficeDo>();
    List<OfficeDo> newOfficeList = new ArrayList<OfficeDo>();
    List<OfficeDo> newOfficeListFinal = new ArrayList<OfficeDo>();
    String result = "";

    public OfficeParserDo parse (OfficeParserDo officeParserDo) throws IOException {

        startScheduleId = officeParserDo.getStartScheduleId();
        startOfficeId = officeParserDo.getStartOfficeId();

        // Формируем список из расписаний (таблица из bcc)
        if (officeParserDo.isSetPath())
            fillScheduleList(officeParserDo.getPathToSchedule());
        // Формируем список из офисов (таблица-шаблон о бизнеса)
        fillOfficeList(officeParserDo.getPathToOffices());
        // Обновляем раписание и формируем раписание по id у новых офисов
        appendToScheduleList();
        // Формируем список объектов расписания по id у новых офисов
        fillScheduleString();
        // Формируем список сервисов по id у новых офисов
        fillServiceString();
        // Формируем результат
        fillResult(officeParserDo.getRegionCode());

        officeParserDo.setCsvData(result);
        return officeParserDo;
    }

    private void fillScheduleList(String path) throws IOException {
        Iterator<Row> rowIterator = getXlsxIterator(path);
        String value = "";
        int rowCounter = 0;
        while (rowIterator.hasNext()) {
            rowCounter++;
            Row row = rowIterator.next();
            if (rowCounter <= 2)
                continue;
            int columnCounter = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            ScheduleDo scheduleDo = new ScheduleDo();
            while (cellIterator.hasNext()) {
                columnCounter++;
                Cell cell = cellIterator.next();
                value = getCellValue(cell);
                if ((value.equals("ERROR")) && (columnCounter == 1))
                    break;
                if(columnCounter==5)
                    break;

                // 1 столбец - ID, 2 столбец - closeTime, 3 стобец - openTime, 4 стобец - day
                if (columnCounter == 1)
                    scheduleDo.setId(value);
                if (columnCounter == 2)
                    scheduleDo.setCloseTime(value);
                if (columnCounter == 3)
                    scheduleDo.setOpenTime(value);
                if (columnCounter == 4) {
                    scheduleDo.setDay(value);
                    scheduleDo.setObjectType("old");
                    scheduleDo.calculateTimeInterval();
                    scheduleList.add(scheduleDo);
                }
            }
            if ((value.equals("ERROR")) && (columnCounter == 1))
                break;
        }
    }

    private void fillOfficeList(String path) throws IOException {
        Iterator<Row> rowIterator = getXlsxIterator(path);
        String value = "";
        int rowCounter = 0;
        while (rowIterator.hasNext()) {
            rowCounter++;
            Row row = rowIterator.next();
            if (rowCounter <= 1)
                continue;
            int columnCounter = 0;
            Iterator<Cell> cellIterator = row.cellIterator();
            OfficeDo officeDo = new OfficeDo();
            String overallSchedule = null;
            while (cellIterator.hasNext()) {
                columnCounter++;
                Cell cell = cellIterator.next();
                value = getCellValue(cell);
                if ((value.equals("ERROR")) && (columnCounter == 1))
                    break;
                if(columnCounter == 21)
                    break;

                // 1 столбец - Name, 2 столбец - City, 3 стобец - Address 1, 4 стобец - Geocode, 5 стобец - OfficeDo Type, 6-12 столбцы - ScheduleDo (Monday - Sunday), 13-19 - Service (1-7)
                if (columnCounter == 1)
                    officeDo.setName(value);
                if (columnCounter == 2)
                    officeDo.setCity(value);
                if (columnCounter == 3)
                    officeDo.setAddress(value);
                if (columnCounter == 4) {
                    officeDo.setGeocode(value);
                    officeDo.calculateLatitudeAndLongitude();
                }
                if (columnCounter == 5)
                    officeDo.setOfficeType(value);
                if (columnCounter == 6)
                    officeDo.setMondaySchedule(value);
                if (columnCounter == 7)
                    officeDo.setTuesdaySchedule(value);
                if (columnCounter == 8)
                    officeDo.setWednesdaySchedule(value);
                if (columnCounter == 9)
                    officeDo.setThursdaySchedule(value);
                if (columnCounter == 10)
                    officeDo.setFridaySchedule(value);
                if (columnCounter == 11)
                    officeDo.setSaturdaySchedule(value);
                if (columnCounter == 12)
                    officeDo.setSundaySchedule(value);
                if (columnCounter == 13)
                    overallSchedule = value;
                if (columnCounter == 14)
                    officeDo.setService1(value);
                if (columnCounter == 15)
                    officeDo.setService2(value);
                if (columnCounter == 16)
                    officeDo.setService3(value);
                if (columnCounter == 17)
                    officeDo.setService4(value);
                if (columnCounter == 18)
                    officeDo.setService5(value);
                if (columnCounter == 19)
                    officeDo.setService6(value);
                if (columnCounter == 20) {
                    officeDo.setService7(value);
                    officeList.add(officeDo);
                }
            }
            if (overallSchedule != null) {
                if (!overallSchedule.equals("ERROR")) {
                    officeDo.setMondaySchedule(overallSchedule);
                    officeDo.setTuesdaySchedule(overallSchedule);
                    officeDo.setWednesdaySchedule(overallSchedule);
                    officeDo.setThursdaySchedule(overallSchedule);
                    officeDo.setFridaySchedule(overallSchedule);
                    officeDo.setSaturdaySchedule(overallSchedule);
                    officeDo.setSundaySchedule(overallSchedule);
                }
                officeDo.fillScheduleHashMap();
            }

            if ((value.equals("ERROR")) && (columnCounter == 1))
                break;
        }
    }

    private void appendToScheduleList() {
        HashMap<String, List<String>> newScheduleHashMap = new HashMap<String, List<String>>();
        Set<String> set = null;

        // Формируем список раписания (только по новым объектам расписания)
        for (OfficeDo office : officeList) {
            // Идем по всему расписанию недели
            for (HashMap.Entry<String,String[]> entry : office.getScheduleHashMap().entrySet()) {
                List<String> tempList = newScheduleHashMap.get(entry.getKey());
                if (tempList == null)
                    tempList = new ArrayList<String>();
                tempList.addAll(Arrays.asList(entry.getValue()));
                newScheduleHashMap.put(entry.getKey(), tempList);
            }
        }

        // Удаляем дубли
        for (HashMap.Entry<String,List<String>> entry : newScheduleHashMap.entrySet()) {
            set = new HashSet<String>(entry.getValue());
            newScheduleHashMap.put(entry.getKey(), new ArrayList<String>(set));
        }

        // Дополняем старый список расписаний новым
        for (HashMap.Entry<String,List<String>> entry : newScheduleHashMap.entrySet()) {
            for (String item : entry.getValue()) {
                int coincidenceCounter = 0;
                for (ScheduleDo schedule : scheduleList) {
                    if (entry.getKey().equals(schedule.getDay()) && item.equals(schedule.getTimeInterval()))
                        coincidenceCounter++;
                }
                if (coincidenceCounter == 0) {
                    scheduleList.add(new ScheduleDo(startScheduleId + "", entry.getKey(), item, "new"));
                    newScheduleList.add(new ScheduleDo(startScheduleId + "", entry.getKey(), item, "new"));
                    startScheduleId ++;
                }
            }
        }
    }

    private void fillScheduleString() {
        // Заполняем данные по расписанию (список id) для новых офисов
        for (OfficeDo office : officeList) {
            // Идем по всему расписанию недели
            for (HashMap.Entry<String,String[]> entry : office.getScheduleHashMap().entrySet()) {
                // Идем по расписанию дня
                for (int i = 0; i < entry.getValue().length; i ++) {
                    for (ScheduleDo schedule : scheduleList) {
                        if (entry.getKey().equals(schedule.getDay()) && schedule.getTimeInterval().equals(entry.getValue()[i])) {
                            if (office.getScheduleString() != null)
                                office.setScheduleString(office.getScheduleString() + schedule.getId() + ",");
                            else office.setScheduleString(schedule.getId() + ",");
                        }
                    }
                }
            }
            if (office.getScheduleString() != null)
                office.setScheduleString(office.getScheduleString().substring(0, office.getScheduleString().length() - 1));
            newOfficeList.add(office);
        }
    }

    private void fillServiceString() {
        // Заполняем данные по сервисам (список id) для новых офисов
        // Порядок сервисов:
        // Предоставление детализации, Подключение/отключение городских номеров,
        // Подключение красивых федеральных номеров, Обслуживание корпоративных клиентов,
        // Обслуживание в вечернее время, Работает в выходные, Пополнить счет без комиссии
        for (OfficeDo office : newOfficeList) {
            String serviceString = "";
            if (office.isService1())
                serviceString += "srv100001,";
            if (office.isService2())
                serviceString += "srv100003,";
            if (office.isService3())
                serviceString += "srv100005,";
            if (office.isService4())
                serviceString += "srv100007,";
            if (office.isService5())
                serviceString += "srv100009,";
            if (office.isService6())
                serviceString += "srv100011,";
            if (office.isService7())
                serviceString += "srv100013";

            office.setServiceString(serviceString);
            newOfficeListFinal.add(office);
        }
    }

    private void fillResult(String regionCode) {
        result += "/atg/commerce/locations/SecureLocationRepository:daySchedule, , , ,LOCALE=ru_RU,\nID,closeTime,openTime,day\n";
        for (ScheduleDo schedule : newScheduleList)
            result += schedule.getId()
                    + "," + schedule.getCloseTime()
                    + "," + schedule.getOpenTime()
                    + "," + schedule.getDay() + "\n";

        result += "\n\n------------------------------------------------\n\n";

        result += "/atg/commerce/locations/SecureLocationRepository:store, , , ,LOCALE=en_US,\nID,sites,name,city,address1,latitude,longitude,locationType,daySchedules,services\n";
        for (OfficeDo office : newOfficeListFinal) {
            result += startOfficePrefix + startOfficeId
                    + ",\"" + regionCode.replace(" ", "")
                    + "\",\"" + office.getName()
                    + "\",\"" + office.getCity()
                    + "\",\"" + office.getAddress()
                    + "\"," + office.getLatitude()
                    + "," + office.getLongitude()
                    + "," + office.getOfficeType()
                    + ",\"" + office.getScheduleString()
                    + "\",\"" + office.getServiceString()
                    + "\"\n";
            startOfficeId ++;
        }
    }

    private Iterator<Row> getXlsxIterator(String path) throws IOException {
        File myFile = new File(path);
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        return mySheet.iterator();
    }

    private String getCellValue(Cell cell) {
        String value;
        int cellType = cell.getCellType();
        //перебираем возможные типы ячеек
        switch (cellType) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                value = "" + cell.getNumericCellValue();
                break;
            default:
                value = "ERROR";
                break;
        }
        return value;
    }
}
