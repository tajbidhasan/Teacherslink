package com.example.teacherslink;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        writeCoursesWithInstructorsToFile("courses_with_instructors.txt");
        writeInstructorsWithCoursesToFile("instructors_with_courses.txt");

        StateManager.saveState(InstructorDatabase.getInstance(), "instructor_state.ser");
        StateManager.saveState(CourseDataSet.getInstance(), "course_state.ser");
    }

    public static void main(String[] args) throws IOException {
        // Load InstructorDatabase
        File instructorFile = new File("instructor_state.ser");
        if (instructorFile.exists() && !instructorFile.isDirectory()) {
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
        if (courseFile.exists() && !courseFile.isDirectory()) {
            CourseDataSet cs = (CourseDataSet) StateManager.loadState("course_state.ser");
            if (cs != null) {
                CourseDataSet.setInstance(cs);
            }
        } else {
            CourseReader.readCoursesFromCSV();
        }

        launch(args);
    }
    public static void writeCoursesWithInstructorsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Course course : CourseDataSet.getInstance().getCourses().values()) {
                Instructor instructor = course.getInstructor(); // Assuming you have a method like this
                String line = course.getCourse() + ": " + (instructor != null ? instructor.getName() : "No instructor assigned");
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeInstructorsWithCoursesToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Instructor instructor : InstructorDatabase.getInstance().getAllInstructors()) {
                List<Course> assignedCourses = instructor.getAssignedCourses(); // Assuming you have this method in your Instructor class
                String courses = assignedCourses.stream()
                        .map(Course::getCourse) // Assuming you have getCourseName() in Course class
                        .collect(Collectors.joining(", "));
                String line = instructor.getName() + ": " + (courses.isEmpty() ? "No courses" : courses);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
