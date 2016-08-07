package app.controllers;

import app.testcaseformatter.TestCaseFormatterDo;
import app.testcaseformatter.TestCaseFormatterSrv;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Контроллер форматирования тест-кейса для Zephyr for JIRA
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class TestCaseFormatterController extends BaseController {

    @FXML public TextArea txtaOriginalText;
    @FXML public Button btnFormat;
    @FXML public TextArea txtaResult;

    public void formatTestCase(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker()
    {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);
                TestCaseFormatterDo testCaseFormatterDo = TestCaseFormatterSrv.formatTestCase(txtaOriginalText.getText());
                if(testCaseFormatterDo != null)
                    txtaResult.setText(testCaseFormatterDo.getFormattedText());
                else txtaResult.setText("Во время обработки исходных данных произошла ошибка");
                disableElements(false);
                isDone=true;
                return true;
            }
        };
    }

    private void disableElements(boolean isDisable)
    {
        txtaOriginalText.setDisable(isDisable);
        btnFormat.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}
