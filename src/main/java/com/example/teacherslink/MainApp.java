package com.example.teacherslink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

        // Set the scene and show the stage
        Scene scene = new Scene(root,650,600);
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());

        primaryStage.setTitle("TeachersLink");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
