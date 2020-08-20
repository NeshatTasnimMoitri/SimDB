package sample;

import com.sun.prism.Texture;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;


public class Main extends Application {
    private Stage stage;
    public Connection connection;
    public Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        ConnectionConfiguration c = new ConnectionConfiguration();
        connection = c.getConnection();
        showLoginPage();

    }

    public void showLoginPage() throws Exception {
        // XML Loading using FXMLLoader
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        root = loader.load();

        // Loading the controller
        Controller controller = loader.getController();
        controller.setMain(this);
        controller.setConnection(connection);
        // Set the primary stage
        stage.setTitle("mysim ");
        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void showFrontpage(int userid) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("frontPage.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loading the controller
        FrontPage controller = loader.getController();
        controller.initialise(userid, connection, this);

        // Set the primary stage
        stage.setTitle("mysim ");
        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }

    public void showOfferpage(int userid) {
        //Loads the page
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("offers.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loading the controller
        OfferPageController controller = loader.getController();
        controller.initialise(userid, connection, this);

        // Set the primary stage
        stage.setTitle("mysim ");
        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }

    public void showUsageHistory(int userid) {
        //Loads the page
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UsageHistory.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loading the controller
        UsageHistoryController controller = loader.getController();
        controller.initialise(userid, connection, this);

        // Set the primary stage
        stage.setTitle("mysim ");
        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }

    public void showFrontPage(int userid) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("offers.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Loading the controller
        FrontPage controller = loader.getController();
        controller.initialise(userid, connection, this);

        // Set the primary stage
        stage.setTitle("mysim ");
        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }
}
