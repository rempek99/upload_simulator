package pl.zimnyciechan.uploadsimulator.ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Upload Simulator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
