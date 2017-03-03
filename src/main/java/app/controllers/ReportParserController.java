package app.controllers;

import app.report.parser.ReportParserDo;
import app.report.parser.ReportParserSrv;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * Контроллер парсинга xml-отчета из Zephyr for JIRA
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class ReportParserController extends BaseController {

    @FXML public TextArea txtaXml;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;

    public void parseReport(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker()
    {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);
                ReportParserDo reportParserDo = ReportParserSrv.parseReport(txtaXml.getText());
                if (reportParserDo != null)
                    txtaResult.setText(reportParserDo.getParsedReport());
                else txtaResult.setText("Во время обработки xml-отчета произошла ошибка");
                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    private void disableElements(boolean isDisable)
    {
        txtaXml.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}
