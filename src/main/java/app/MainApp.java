package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * @author Ivan Ivankov {@literal <iivankov@at-consulting.ru>}
 */
public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setTitle("TDSTestHelper");
        TabPane tabPane = new TabPane();
        Tab tabTestCaseFormatter = new Tab ("FORMAT TEST CASE");
        Parent rootTestCaseFormatter = FXMLLoader.load(getClass().getResource("/fxml/testcaseformatter.fxml"));
        tabTestCaseFormatter.setContent(rootTestCaseFormatter);
        Tab tabReportParser = new Tab ("PARSE REPORT");
        Parent rootReportParser = FXMLLoader.load(getClass().getResource("/fxml/reportparser.fxml"));
        tabReportParser.setContent(rootReportParser);
        Tab tabOfficeParser = new Tab ("PARSE OFFICES");
        Parent rootOfficeParser = FXMLLoader.load(getClass().getResource("/fxml/officeparser.fxml"));
        tabOfficeParser.setContent(rootOfficeParser);
        tabPane.getTabs().addAll(tabTestCaseFormatter, tabReportParser, tabOfficeParser);
        Scene scene = new Scene(tabPane, 630, 630);
        stage.setScene(scene);
        stage.show();
    }
}
