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
            originalText = originalText.replace("\n\n", "\n\nline_break\n\n");
            String[] textArray = originalText.split("\n"); //разбиение тест-кейса на массив строк
            int testCaseCounter = 0;
            boolean isExample = false;

            for (int i = 0; i < textArray.length; i++) {
                /*
                * Первичное форматирование: удаление пробелов в начале строк, выделение меток, форматирование комментариев
                */
                textArray[i] = textArray[i].replaceAll("^\\s+", "");
                textArray[i] = textArray[i].contains("@atest") ? "*" + textArray[i] + "*" : textArray[i];
                if (!textArray[i].equals("") && textArray[i].substring(0, 1).equals("#")) {
                    textArray[i] = "_" + textArray[i] + "_";
                }

                if (textArray[i].contains("Сценарий:")) {
                    formatScenario(textArray[i], ++testCaseCounter);
                } else if (textArray[i].contains("Структура сценария:")) {
                    formatScenarioStructure(textArray[i], ++testCaseCounter);
                } else if (textArray[i].contains("Предыстория:")) {
                    formatPrehistory();
                } else {
                    textArray[i] = getBoldText(textArray[i]);
                    if (!textArray[i].equals("")) {
                        formattedText += textArray[i] + "\n";
                    }
                }
            }
        } catch (Exception e){
            return null;
        }
        return new TestCaseFormatterDo((formattedText.replace("line_break", "{panel}\n\n")) + "{panel}");
    }

    private void formatScenario(String text, int testCaseCounter){
        formattedText += "{panel:title=Test Case " + testCaseCounter + ": " + getTextByRegularExpression("Сценарий:(.*)", text) + "|borderStyle=dashed|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#FFFFCE}\n";
    }

    private void formatScenarioStructure(String text, int testCaseCounter){
        formattedText += "{panel:title=Test Case " + testCaseCounter + ": " + getTextByRegularExpression("Структура сценария:(.*)", text) + "|borderStyle=dashed|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#FFFFCE}\n";
    }

    private void formatPrehistory(){
        formattedText += "{panel:title=Предыстория|borderStyle=dashed|borderColor=#ccc|titleBGColor=#F7D6C1|bgColor=#FFFFCE}\n";
    }

    private String getBoldText(String text){
        text = text.replace("Пусть ", "*Пусть* ");
        text = text.replace("Когда ", "*Когда* ");
        text = text.replace("Тогда ", "*Тогда* " );
        text = text.replace("Если ", "*Если* ");
        text = text.replace("То ", "*То* ");
        text = text.replace("К тому же ", "*К тому же* ");
        text = text.replace("Также ", "*Также* ");
        text = text.replace("А ", "*А* ");
        text = text.replace("Допустим ", "*Допустим* ");
        text = text.replace("И ", "*И* ");
        text = text.replace("Примеры:", "*Примеры:*");
        return text;
    }

    private String getTextByRegularExpression(String patternString, String text){
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        matcher.find();
        return matcher.group(1);
    }
}
