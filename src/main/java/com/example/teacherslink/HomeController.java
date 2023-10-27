package com.example.teacherslink;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class HomeController implements Serializable {
    @FXML
    private Button getCourses;

    @FXML
    private Button getInstructors;

    @FXML
    private TextField getPassCode;


    @FXML
    private Button scheduleBuilder;
    @FXML
    private void handleScheduleBuilderClick() {
        try {
            //ExcelProcessor.processExcelFile("/Users/tajbidhasan/Desktop/CS248/Teacherslink/src/main/resources/Instructors.xlsx");
            //CourseReader.readCoursesFromCSV();
            //ExcelProcessor.processFrequencyFile("/Users/tajbidhasan/Desktop/intillij JAVA/CS248/src/main/resources/Frequency.xlsx");
            // Load the new scene from ScheduleBuilderView.fxml
            Parent scheduleBuilderViewRoot = FXMLLoader.load(getClass().getResource("/com/example/teacherslink/ScheduleBuilderView.fxml"));

            Scene scheduleBuilderViewScene = new Scene(scheduleBuilderViewRoot);
            scheduleBuilderViewScene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());
            // Get the current stage
            Stage currentStage = (Stage) scheduleBuilder.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(scheduleBuilderViewScene);

            // Set the width and height for the stage
            currentStage.setWidth(800);
            currentStage.setHeight(700);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an error dialog)
        }
    }


    @FXML
    private void handleGetInstructorsClick() {
        try {
            // Load the new scene from instructorView.fxml
            Parent instructorViewRoot = FXMLLoader.load(getClass().getResource("/com/example/teacherslink/InstructorView.fxml"));


            Scene instructorViewScene = new Scene(instructorViewRoot);
            instructorViewScene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());

            // Get the current stage
            Stage currentStage = (Stage) getInstructors.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(instructorViewScene);

            // Set the width and height for the stage
            currentStage.setWidth(800);
            currentStage.setHeight(700);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an error dialog)
        }

    }
    @FXML
    private void handleGetCoursesClick() {
        try {
            //CourseReader.readCoursesFromCSV(); (test)
            // Load the new scene from courseView.fxml
            Parent courseViewRoot = FXMLLoader.load(getClass().getResource("/com/example/teacherslink/courseView.fxml"));
            Scene courseViewScene = new Scene(courseViewRoot);
            courseViewScene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());

            // Get the current stage
            Stage currentStage = (Stage) getCourses.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(courseViewScene);

            // Set the width and height for the stage
            currentStage.setWidth(800);
            currentStage.setHeight(700);

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., show an error dialog)
        }
    }


}
