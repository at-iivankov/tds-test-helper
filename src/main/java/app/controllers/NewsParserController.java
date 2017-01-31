package app.controllers;

import app.newsmigration.NewsParserDo;
import app.newsmigration.NewsParserSrv;
import app.officeparser.OfficeParserDo;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для парсинга новостей
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class NewsParserController extends BaseController {

    @FXML public TextField txtfPathToXlsx;
    @FXML public CheckBox chbxIsFederal;
    @FXML public TextField txtfStartId;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;;

    public void initialize(URL location, ResourceBundle resources) {
    }

    public void parseNews(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                txtaResult.setText(NewsParserSrv.parseNews(new NewsParserDo(txtfPathToXlsx.getText(), chbxIsFederal.isSelected(), txtfStartId.getText())).getCsvData());

                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    private void disableElements(boolean isDisable) {
        chbxIsFederal.setDisable(isDisable);
        txtfPathToXlsx.setDisable(isDisable);
        txtfStartId.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}
