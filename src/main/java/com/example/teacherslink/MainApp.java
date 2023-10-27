package com.example.teacherslink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


/**
 * The main application class for TeachersLink.
 * It initializes the JavaFX application, loads the initial UI, and handles application state management.
 *
 * @author Tajbid Hasan
 * @version 1.0
 * @since 2023-10-27
 */
public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/teacherslink/HomeView.fxml"));

        // Set the scene and show the stage
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add(getClass().getResource("/theme.css").toExternalForm());

        primaryStage.setTitle("TeachersLink");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        String coursesFilename = "courses_with_instructors.xlsx";
        String instructorsFilename = "instructors_with_courses.xlsx";

        // Call the methods to generate the Excel files
        ExcelWriter.writeCoursesWithInstructorsToFile(coursesFilename);
        ExcelWriter.writeInstructorsWithCoursesToFile(instructorsFilename);

        StateManager.saveState(InstructorDatabase.getInstance(), "instructor_state.ser");
        StateManager.saveState(CourseDataSet.getInstance(), "course_state.ser");

        System.out.println("Excel files have been generated successfully.");

        System.out.println("Data Saved successfully.");
    }

    public static void main(String[] args) throws IOException {
        // Load InstructorDatabase
        File instructorFile = new File("instructor_state.ser");

        if (instructorFile.exists() && !instructorFile.isDirectory() && instructorFile.length() > 0) {
            InstructorDatabase db = (InstructorDatabase) StateManager.loadState("instructor_state.ser");
            if (db != null) {
                InstructorDatabase.setInstance(db);
            }
        } else {
            ExcelProcessor.processExcelFile("/Users/tajbidhasan/Desktop/CS248/Teacherslink/src/main/resources/Instructors.xlsx");
            ExcelProcessor.processFrequencyFile("/Users/tajbidhasan/Desktop/intillij JAVA/CS248/src/main/resources/Frequency.xlsx");
        }


        // Load CourseDataSet
        File courseFile = new File("course_state.ser");
        if (courseFile.exists() && !courseFile.isDirectory() && courseFile.length()>0) {
            CourseDataSet cs = (CourseDataSet) StateManager.loadState("course_state.ser");
            if (cs != null) {
                CourseDataSet.setInstance(cs);
            }
        } else {
            CourseReader.readCoursesFromCSV();
        }

        launch(args);
    }

}
