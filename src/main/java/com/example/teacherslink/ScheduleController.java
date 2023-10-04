package com.example.teacherslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ScheduleController {
    @FXML
    private Label SetCourse;

    @FXML
    private Label SetInstructorName;

    @FXML
    private ListView<Course> UnassignedCourseList;

    @FXML
    private Button goHome;

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


}
