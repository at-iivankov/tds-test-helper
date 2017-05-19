package app.controllers;

import app.option.parser.OptionParserDo;
import app.option.parser.OptionParserSrv;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для парсинга опций
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OptionParserController extends BaseController {

    @FXML public TextField txtfPathToCsv;
    @FXML public TextField txtfStartId;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void parseOption(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                txtaResult.setText(OptionParserSrv.parseOption(new OptionParserDo(txtfPathToCsv.getText(), txtfStartId.getText())).getCsvData());

                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    private void disableElements(boolean isDisable) {
        txtfPathToCsv.setDisable(isDisable);
        txtfStartId.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}
