package com.example.teacherslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ScheduleController {
    @FXML
    private Label SetCourse;

    @FXML
    private Label SetInstructorName;

    @FXML
    private ListView<Course> UnassignedCourseList;

    @FXML
    private Button homeButton;

    @FXML
    private ListView<Instructor> instructorList;

    @FXML
    private TextField instructorSearchField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label setCRN;

    @FXML
    private Label setCourseTitle;

    @FXML
    private Label setDays;

    @FXML
    private Button setInstructor;

    @FXML
    private Label setInstructorID;

    @FXML
    private Label setEndTime;

    @FXML
    private Label setStartTime;
    @FXML
    private Label setRank;

    @FXML
    void SeachInstructor(ActionEvent event) {

    }
    private InstructorDatabase instructorDatabase;
    private CourseDataSet courseDataSet;


    @FXML
    public void initialize() {
        instructorDatabase = InstructorDatabase.getInstance();
        courseDataSet = CourseDataSet.getInstance();
        System.out.println("Total courses: " + courseDataSet.getCourses().size());
        System.out.println("Initializing...");
        populateUnassignedCourses();

        // Add a listener to detect when a course is selected in the ListView
        UnassignedCourseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayCourseDetails(newValue);
                populateInstructorsForCourse(newValue);
            }
        });

        instructorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayInstructorDetails(newValue);
            }
        });
    }

    private void populateUnassignedCourses() {
        ObservableList<Course> unassignedCourses = FXCollections.observableArrayList();
        for (Course course : courseDataSet.getCourses().values()) {
            if (course.getInstructor() == null) {
                unassignedCourses.add(course);
            }
        }
        UnassignedCourseList.setItems(unassignedCourses);
    }

    private void displayCourseDetails(Course selectedCourse) {
        setCRN.setText(Integer.toString(selectedCourse.getCrn()));
        setCourseTitle.setText(selectedCourse.getCourseTitle());
        setDays.setText(selectedCourse.getDays());
        setEndTime.setText(selectedCourse.getEndTime().toString());
        setStartTime.setText(selectedCourse.getBeginTime().toString());
        SetCourse.setText(selectedCourse.getCourse()); // Assuming the method name is getCourseName
    }
    private void populateInstructorsForCourse(Course selectedCourse) {
        ObservableList<Instructor> instructorsForCourse = FXCollections.observableArrayList();

        for (Instructor instructor : instructorDatabase.getAllInstructors()) { // Assuming there's a getInstructors method
            if (instructor.courseExists(selectedCourse.getCourse())) {
                instructorsForCourse.add(instructor);
            }
        }

        ObservableList<Instructor> sortedInstructors = instructorsForCourse.stream()
                .sorted(Comparator.comparingInt(instructor -> instructor.getRank().ordinal()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        instructorList.setItems(sortedInstructors); // Set the sorted list to instructorList
    }
    private void displayInstructorDetails(Instructor selectedInstructor) {
        SetInstructorName.setText(selectedInstructor.getName()); // Assuming the instructor class has a getName() method
        setInstructorID.setText(selectedInstructor.getID_no()); // Assuming the instructor class has an getId() method that returns int
        setRank.setText(selectedInstructor.getRank().toString());
    }


    @FXML
    private void handleHomeClick() {
        try {
            CourseReader.readCoursesFromCSV();
            // Load the new scene from courseView.fxml
            Parent courseViewRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
            Scene courseViewScene = new Scene(courseViewRoot);

            // Get the current stage
            Stage currentStage = (Stage) homeButton.getScene().getWindow();

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
