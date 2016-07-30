package app.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Базовый контроллер
 *
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public abstract class BaseController implements Initializable {

    @FXML
    public ProgressIndicator piIndicator;

    boolean isDone = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void runWorker(ActionEvent actionEvent) throws Exception {

        Task functionalWorker = runFunctionalWorker();
        new Thread(functionalWorker).start();

        Task progressIndicatorWorker = runProgressIndicatorWorker();
        piIndicator.progressProperty().unbind();
        piIndicator.progressProperty().bind(progressIndicatorWorker.progressProperty());
        new Thread(progressIndicatorWorker).start();
    }

    protected abstract Task runFunctionalWorker();


    private Task runProgressIndicatorWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    if (!isDone)
                        Thread.sleep(100);
                    else break;
                }
                updateProgress(1,1);
                isDone=false;
                return true;
            }
        };
    }
}
