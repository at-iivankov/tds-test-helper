package app.reportparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Парсинг отчета XML
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class ReportParserBo {

    /**
     * Класс для хранения параметров тестовых сценариев
     */
    private class TestScenario{

        private String testSummary;
        private int stepCount;
        private int atestCount;
        private int passStepCount;
        private int failStepCount;
        private int blockedStepCount;
        private int wipStepCount;
        private int unexecutedStepCount;

        public String getTestSummary() {
            return testSummary;
        }

        public int getStepCount() {
            return stepCount;
        }

        public void setStepCount(int stepCount) {
            this.stepCount = stepCount;
        }

        public int getAtestCount() {
            return atestCount;
        }

        public void setAtestCount(int atestCount) {
            this.atestCount = atestCount;
        }

        public int getPassStepCount() {
            return passStepCount;
        }

        public void setPassStepCount(int passStepCount) {
            this.passStepCount = passStepCount;
        }

        public int getFailStepCount() {
            return failStepCount;
        }

        public void setFailStepCount(int failStepCount) {
            this.failStepCount = failStepCount;
        }

        public int getBlockedStepCount() {
            return blockedStepCount;
        }

        public void setBlockedStepCount(int blockedStepCount) {
            this.blockedStepCount = blockedStepCount;
        }

        public int getWipStepCount() {
            return wipStepCount;
        }

        public void setWipStepCount(int wipStepCount) {
            this.wipStepCount = wipStepCount;
        }

        public int getUnexecutedStepCount() {
            return unexecutedStepCount;
        }

        public void setUnexecutedStepCount(int unexecutedStepCount) {
            this.unexecutedStepCount = unexecutedStepCount;
        }

        public TestScenario(String testSummary){
            this.testSummary = testSummary;
        }

        public TestScenario(String testSummary, int stepCount, int atestCount, int passStepCount, int failStepCount, int blockedStepCount, int wipStepCount, int unexequtedStepCount){
            this.testSummary = testSummary;
            this.stepCount = stepCount;
            this.atestCount = atestCount;
            this.passStepCount = passStepCount;
            this.failStepCount = failStepCount;
            this.blockedStepCount = blockedStepCount;
            this.wipStepCount = wipStepCount;
            this.unexecutedStepCount = unexequtedStepCount;
        }
    }

    private String parsedReport;
    private String originalReport;

    public ReportParserBo(String originalReport){
        parsedReport = "";
        this.originalReport = originalReport;
    }

    public ReportParserDo parse () throws IOException, ParserConfigurationException, SAXException {

        List<TestScenario> testScenarioList = new ArrayList<TestScenario>();
        try {

            String xmlString = getReconstructedXmlReport(originalReport);

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            StringBuilder xmlStringBuilder = new StringBuilder();
            xmlStringBuilder.append(xmlString);
            ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));

            Document document = documentBuilder.parse(input);
            document.getDocumentElement().normalize();
            NodeList rootNodeList = document.getElementsByTagName("execution"); // Находим объекты выполнения сценариев

            // Проходим по всем тестовым сценариям
            for (int i = 0; i < rootNodeList.getLength(); i++) {
                int atestCounter = 0;
                boolean isPrehistory = false;
                Node node = rootNodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    //Берем название сценария
                    String testSummary = element
                            .getElementsByTagName("testSummary")
                            .item(0)
                            .getTextContent();
                    TestScenario testScenario = new TestScenario(getTestSummary(testSummary));

                    // Получаем все тест-кейсы внутри тестового сценария
                    NodeList testStepNodeList = ((Element) node).getElementsByTagName("teststep");
                    testScenario.setStepCount(testStepNodeList.getLength());

                    //Проходим по всем тест-кейсам внутри тестового сценария
                    for (int j = 0; j < testStepNodeList.getLength(); j++) {
                        // Получаем текст тет-кейса
                        String stepText = element
                                .getElementsByTagName("шаг")
                                .item(j)
                                .getTextContent();
                        if (stepText.contains("Предыстория"))
                            isPrehistory = true;
                        if (stepText.contains("@atest"))
                            atestCounter++;
                        // Получаем результат выполнения отдельного тест-кейса внутри тестового сценария
                        String resultStep = element
                                .getElementsByTagName("Результатшага")
                                .item(j)
                                .getTextContent();
                        if (resultStep.equals("PASS")) {
                            testScenario.setPassStepCount(testScenario.getPassStepCount() + 1);
                        }
                        if (resultStep.equals("FAIL")) {
                            testScenario.setFailStepCount(testScenario.getFailStepCount() + 1);
                        }
                        if (resultStep.equals("BLOCKED")) {
                            testScenario.setBlockedStepCount(testScenario.getBlockedStepCount() + 1);
                        }
                        if (resultStep.equals("WIP")) {
                            testScenario.setWipStepCount(testScenario.getWipStepCount() + 1);
                        }
                        if (resultStep.equals("UNEXECUTED")) {
                            testScenario.setUnexecutedStepCount(testScenario.getUnexecutedStepCount() + 1);
                        }
                    }
                    if (isPrehistory && atestCounter != 0)
                        atestCounter++;
                    testScenario.setAtestCount(atestCounter);
                    testScenarioList.add(testScenario);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return new ReportParserDo(getReport(getStatistic(testScenarioList)));
    }

    /**
     * Метод для восстановления xml (Zephyr for JIRA отдает 'битый' xml)
     */
    private String getReconstructedXmlReport(String xmlString){
        xmlString = xmlString.replace(" ", "");
        xmlString = xmlString.replace("(","_");
        xmlString = xmlString.replace(")","_");
        xmlString = xmlString.replace("\"<","");
        xmlString = xmlString.replace("\">","");
        xmlString = xmlString.replace("<?xmlversion=\"1.0\"encoding=\"UTF-8\"?>", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        return xmlString;
    }

    /**
     * Метод для получения базового названия тестовых сценариев для того чтобы потом сгруппировать сценарии по базовому
     * названию (базовое название - название до '(')
     */
    private String getTestSummary(String str) {
        String[] strArray = str.split("_");
        return strArray[0];
    }

    /**
     * Метод для получени статистики (в случае наличия нескольких тестовых сценариев для одного функционала группирует и
     * суммирует результат по базовом названию (базовое название - название до '('))
     */
    private Map<String, TestScenario> getStatistic(List<TestScenario> testScenarioList){
        Map<String, TestScenario> testScenarioMap = new HashMap<String, TestScenario>();

        for (TestScenario testScenario : testScenarioList)
        {
            if(!testScenarioMap.containsKey(testScenario.getTestSummary())){
                testScenarioMap.put(
                        testScenario.getTestSummary(),
                        new TestScenario(
                                testScenario.getTestSummary(),
                                testScenario.getStepCount(),
                                testScenario.getAtestCount(),
                                testScenario.getPassStepCount(),
                                testScenario.getFailStepCount(),
                                testScenario.getBlockedStepCount(),
                                testScenario.getWipStepCount(),
                                testScenario.getUnexecutedStepCount()
                        )
                );
            }
            else{
                testScenarioMap.put(
                        testScenario.getTestSummary(),
                        new TestScenario(
                                testScenario.getTestSummary(),
                                testScenarioMap.get(testScenario.getTestSummary()).getStepCount() + testScenario.getStepCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getAtestCount() + testScenario.getAtestCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getPassStepCount() + testScenario.getPassStepCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getFailStepCount() + testScenario.getFailStepCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getBlockedStepCount() + testScenario.getBlockedStepCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getWipStepCount() + testScenario.getWipStepCount(),
                                testScenarioMap.get(testScenario.getTestSummary()).getUnexecutedStepCount() + testScenario.getUnexecutedStepCount()
                        )
                );
            }
        }
        return testScenarioMap;
    }

    private String getReport(Map<String, TestScenario> reportMap){
        String result = "Статистика по выполнению тест-кейсов: \n\n\n";
        int scenarioCounter = 0;
        int atestCounter = 0;
        int passScenarioCounter = 0;
        int failScenarioCounter = 0;
        int blockedScenarioCounter = 0;
        int wipScenarioCounter = 0;
        int unexecutedScenarioCounter = 0;

        for (Map.Entry<String,TestScenario> entry : reportMap.entrySet()) {
            result += entry.getValue().getTestSummary().toUpperCase()
                    + ": \n\nall - " + entry.getValue().getStepCount()
                    + " | atest - " + entry.getValue().getAtestCount()
                    + " | pass - " + entry.getValue().getPassStepCount()
                    + " | fail - " + entry.getValue().getFailStepCount()
                    + " | blocked - " + entry.getValue().getBlockedStepCount()
                    + " | wip - " + entry.getValue().getWipStepCount()
                    + " | unexequted - " + entry.getValue().getUnexecutedStepCount()
                    + "\n\n=========================================\n\n";
            scenarioCounter += entry.getValue().getStepCount();
            atestCounter += entry.getValue().getAtestCount();
            passScenarioCounter += entry.getValue().getPassStepCount();
            failScenarioCounter += entry.getValue().getFailStepCount();
            blockedScenarioCounter += entry.getValue().getBlockedStepCount();
            wipScenarioCounter += entry.getValue().getWipStepCount();
            unexecutedScenarioCounter += entry.getValue().getUnexecutedStepCount();
        }
        result +="\nВсего тест-кейсов: "
                + scenarioCounter
                + "\natest: "
                + atestCounter
                + "\npass: "
                + passScenarioCounter
                + "\nfail: "
                + failScenarioCounter
                + "\nblocked: "
                + blockedScenarioCounter
                + "\nwip: "
                + wipScenarioCounter
                + "\nunexecuted: "
                + unexecutedScenarioCounter;
        return result;
    }
}
