package app.controllers;

import app.tariffparser.TariffParserDo;
import app.tariffparser.TariffParserSrv;
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
public class TariffParserController extends BaseController {

    @FXML public TextField txtfPathToXlsx;
    @FXML public TextField txtfStartId;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void parseTariff(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                txtaResult.setText(TariffParserSrv.parseTariff(new TariffParserDo(txtfPathToXlsx.getText(), txtfStartId.getText())).getCsvData());

                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    private void disableElements(boolean isDisable) {
        txtfPathToXlsx.setDisable(isDisable);
        txtfStartId.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}
