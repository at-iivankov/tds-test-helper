package app.testcaseformatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Форматирование тест-кейса
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TestCaseFormatterBo {

    private String formattedText;
    private String originalText;

    public TestCaseFormatterBo(String originalText){
        formattedText = "";
        this.originalText = originalText;
    }

    public TestCaseFormatterDo format (){

        try {

            String[] textArray = originalText.split("\n"); //разбиение тест-кейса на массив строк

            boolean isExample = false;

            for (int i = 0; i < textArray.length; i++) {
            /*
            * Первичное форматирование: удаление пробелов в начале строк, удаление меток, форматирование комментариев
            */
                textArray[i] = textArray[i].replaceAll("^\\s+", "");
                textArray[i] = textArray[i].replaceAll("^@.*", "");
                if (!textArray[i].equals("") && textArray[i].substring(0, 1).equals("#")) {
                    textArray[i] = "_" + textArray[i] + "_";
                }

                if (textArray[i].contains("Сценарий:")) {
                    formatScenario(textArray[i]);
                } else if (textArray[i].contains("Структура сценария:")) {
                    formatScenarioStructure(textArray[i]);
                } else if (textArray[i].contains("Предыстория:")) {
                    formatPrehistory();
                } else {
                    if (textArray[i].contains("Примеры:")) {
                        isExample = true;
                        formatExamples();
                    } else if (isExample) {
                        if (!textArray[i].contains("|") || i == textArray.length - 1) {
                            isExample = false;
                            formattedText += textArray[i] + "{panel}\n\n";
                        } else formattedText += textArray[i] + "\n";
                    } else {
                        textArray[i] = getBoldText(textArray[i]);
                        if (!textArray[i].equals("")) {
                            formattedText += "{panel}" + textArray[i] + "{panel}\n\n";
                        }
                    }
                }
            }
        } catch (Exception e){
            return null;
        }
        return new TestCaseFormatterDo(formattedText);
    }

    private void formatScenario(String text){
        formattedText += "\n\n{panel:title=*Сценарий*}" + getTextByRegularExpression("Сценарий:(.*)", text) + "{panel}\n\n";
    }

    private void formatScenarioStructure(String text){
        formattedText += "\n\n{panel:title=*Структура сценария*}" + getTextByRegularExpression("Структура сценария:(.*)", text) + "{panel}\n\n";
    }

    private void formatPrehistory(){
        formattedText += "\n\n{panel:title=*Предыстория*}{panel}\n\n";
    }

    private void formatExamples(){
        formattedText += "\n\n{panel}*Примеры*:\n\n";
    }

    private String getBoldText(String text){
        text = text.replace("Пусть ", "*Пусть* ");
        text = text.replace("Когда ", "*Когда* ");
        text = text.replace("Тогда ", "*Тогда*" );
        text = text.replace("Если ", "*Если* ");
        text = text.replace("Тогда ", "*Тогда* ");
        text = text.replace("К тому же ", "*К тому же* ");
        text = text.replace("И ", "*И* ");
        return text;
    }

    private String getTextByRegularExpression(String patternString, String text){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group(1);
    }
}
