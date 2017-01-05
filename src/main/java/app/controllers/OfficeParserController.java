package app.controllers;

import app.officeparser.OfficeParserDo;
import app.officeparser.OfficeParserSrv;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

/**
 * Контроллер парсинга офисов продаж
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class OfficeParserController extends BaseController {

    @FXML public TextField txtfPathToSchedule;
    @FXML public CheckBox chbxIsSetPath;
    @FXML public TextField txtfPathToOffices;
    @FXML public TextField txtfRegionCode;
    @FXML public TextField txtfStartScheduleId;
    @FXML public TextField txtfStartOfficeId;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;;

    public void initialize(URL location, ResourceBundle resources) {
        txtfPathToSchedule.setDisable(true);
        txtaResult.setText("Инструкция по формированию csv для импорта новых офисов продаж в bcc по заполненному шаблону xlsx:\n\n1) Экспортировать все объекты расписаний из bcc в xls и преобразовать xls в xlsx\n2) Визуально проверить корректность заполненного шаблона офисов продаж\n3) Заполнить текстовые поля формы (id заполнить таким образом, чтобы они не пересекались с имеющимися в bcc для соответствующих типов объектов)\n4) Нажать \"Распарсить\"\n5) Cкопировать в новую csv первую часть полученного результата (расписание)\n6) Импортировать в bcc csv (расписание)\n7) Скопировать в новую csv вторую часть полученного результата (офисы)\n8) Импортировать в bcc csv (офисы)\n9) Задеплоить изменения");
    }

    public void parseOffice(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                txtaResult.setText(OfficeParserSrv.parseOffices(new OfficeParserDo(txtfPathToSchedule.getText(), chbxIsSetPath.isSelected(), txtfPathToOffices.getText(), txtfRegionCode.getText(), Integer.parseInt(txtfStartScheduleId.getText()), Integer.parseInt(txtfStartOfficeId.getText()))).getCsvData());

                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    public void selectCheckBox(ActionEvent actionEvent)
    {
        if (chbxIsSetPath.isSelected())
            txtfPathToSchedule.setDisable(false);
        else txtfPathToSchedule.setDisable(true);
    }

    private void disableElements(boolean isDisable) {
        if (chbxIsSetPath.isSelected())
            txtfPathToSchedule.setDisable(isDisable);
        chbxIsSetPath.setDisable(isDisable);
        txtfPathToOffices.setDisable(isDisable);
        txtfRegionCode.setDisable(isDisable);
        txtfStartScheduleId.setDisable(isDisable);
        txtfStartOfficeId.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}

