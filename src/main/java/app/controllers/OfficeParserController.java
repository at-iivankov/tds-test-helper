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
    @FXML public TextField txtfStartScheduleId;
    @FXML public TextField txtfStartOfficeId;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;;

    public void initialize(URL location, ResourceBundle resources) {
        txtfPathToSchedule.setDisable(true);
        txtaResult.setText("Инструкция по формированию csv для импорта новых офисов продаж в bcc по заполненному шаблону xlsx:\n\n1) Экспортировать все объекты расписаний из bcc в xls и преобразовать xls в xlsx, проставить в первом столбце первой пустой строки \"END;\"\n2) В заполненном шаблоне офисов продаж проставить в первом столбце первой пустой строки \"END;\", если необходимо, поменять местами столбцы сервисов в заполненном шаблоне офисов продаж таким образом, чтобы были следующие соответствия (на данный момент предполагается, что сервисы для всех регионов неизменны):\n\nSercie (1) = Предоставление детализации\nSercie (2) = Подключение/отключение городских номеров\nSercie (3) = Подключение красивых федеральных номеров\nSercie (4) = Обслуживание корпоративных клиентов\nSercie (5) = Обслуживание в вечернее время\nSercie (6) = Работает в выходные\nSercie (7) = Пополнить счет без комиссии\n\n3) Визуально проверить корректность заполненного шаблона офисов продаж\n4) Заполнить текстовые поля формы (id заполнить таким образом, чтобы они не пересекались с имеющимися в bcc для соответствующих типов объектов)\n5) Нажать \"Распарсить\"\n6) Cкопировать в новую csv первую часть полученного результата (расписание)\n7) Испортировать в bcc csv (расписание)\n8) Скопировать в новую csv вторую часть полученного результата (офисы)\n9) Испортировать в bcc csv (офисы)\n10) Задеплоить изменения");
    }

    public void parseOffice(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                txtaResult.setText(OfficeParserSrv.parseOffices(new OfficeParserDo(txtfPathToSchedule.getText(), chbxIsSetPath.isSelected(), txtfPathToOffices.getText(), Integer.parseInt(txtfStartScheduleId.getText()), Integer.parseInt(txtfStartOfficeId.getText()))).getCsvData());

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
        txtfStartScheduleId.setDisable(isDisable);
        txtfStartOfficeId.setDisable(isDisable);
        btnParse.setDisable(isDisable);
        txtaResult.setDisable(isDisable);
    }
}

