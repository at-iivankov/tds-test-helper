package app.controllers;

import app.photo.articles.parser.PhotoArticlesParserDo;
import app.photo.articles.parser.PhotoArticlesParserSrv;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер парсинга статей фотоальбома
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class PhotoArticlesParserController extends BaseController {

    @FXML public TextField txtfPathToPhotoArticlesXml;
    @FXML public TextField txtfPathToIdMapCsv;
    @FXML public Button btnParse;
    @FXML public TextArea txtaResult;

    public void parsePhotoArticles(ActionEvent actionEvent) throws Exception {
        runWorker(actionEvent);
    }

    public Task runFunctionalWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                disableElements(true);

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream("D:/photos.csv"), "utf-8"))) {
                    writer.write(PhotoArticlesParserSrv.parse(new PhotoArticlesParserDo(txtfPathToPhotoArticlesXml.getText(), txtfPathToIdMapCsv.getText())).getCsvData());
                    txtaResult.setText("SUCCESS: D:/photos.csv");
                } catch (Exception e) {
                    txtaResult.setText("FAIL");
                }

                disableElements(false);
                isDone=true;
                return true;
            }
        };

    }

    private void disableElements(boolean isDisable) {
        txtfPathToPhotoArticlesXml.setDisable(isDisable);
        txtfPathToIdMapCsv.setDisable(isDisable);
        btnParse.setDisable(isDisable);
    }
}

