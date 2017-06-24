package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import org.apache.commons.codec.binary.Base64;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        Tab tabNewsParser = new Tab ("PARSE NEWS");
        Parent rootNewsParser = FXMLLoader.load(getClass().getResource("/fxml/newsparser.fxml"));
        tabNewsParser.setContent(rootNewsParser);
        Tab tabOptionParser = new Tab ("PARSE OPTIONS");
        Parent rootOptionsParser = FXMLLoader.load(getClass().getResource("/fxml/optionparser.fxml"));
        tabOptionParser.setContent(rootOptionsParser);
        Tab tabTariffParser = new Tab ("PARSE TARIFF");
        Parent rootTariffParser = FXMLLoader.load(getClass().getResource("/fxml/tariffparser.fxml"));
        tabTariffParser.setContent(rootTariffParser);
        Tab tabEmailParser = new Tab ("PARSE EMAIL");
        Parent rootEmailParser = FXMLLoader.load(getClass().getResource("/fxml/emailparser.fxml"));
        tabEmailParser.setContent(rootEmailParser);
        Tab tabPhotoArticlesParser = new Tab ("PARSE PHOTOS");
        Parent rootPhotoArticlesParser = FXMLLoader.load(getClass().getResource("/fxml/photoarticlesparser.fxml"));
        tabPhotoArticlesParser.setContent(rootPhotoArticlesParser);

        tabPane.getTabs().addAll(tabTestCaseFormatter, tabReportParser, tabOfficeParser, tabNewsParser, tabTariffParser,
                tabOptionParser, tabEmailParser, tabPhotoArticlesParser);
        Scene scene = new Scene(tabPane, 630, 630);
        stage.setScene(scene);
        stage.show();
    }
}
