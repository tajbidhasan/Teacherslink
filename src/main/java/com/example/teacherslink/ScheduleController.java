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
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleController implements Serializable {

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
    private TextField instructorSearchField; // to find instructor by name or id

    @FXML
    private Button searchButton; // search courses

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

    @FXML
    private Label showFrequancy;
    private InstructorDatabase instructorDatabase;
    private CourseDataSet courseDataSet;

    private Instructor selectedInstructor; // Store the selected instructor
    private String selectedCourse;
    @FXML
    private void handleSetInstructorClick() {
        Course selectedCourse = UnassignedCourseList.getSelectionModel().getSelectedItem();

        if (selectedCourse != null && selectedInstructor != null) {
            // Assign the selected instructor to the selected course
            selectedCourse.setInstructor(selectedInstructor);
            selectedInstructor.assignCourse(selectedCourse);

            // Update the instructor's availability based on the assigned course
            selectedInstructor.updateAvailability(selectedCourse);

            populateUnassignedCourses(); // Refresh the unassigned course list

            // Optionally, clear the instructor selection and disable the setInstructor button
            instructorList.getSelectionModel().clearSelection();
            setInstructor.setDisable(true);
        }
    }

    @FXML
    public void initialize() {
        instructorDatabase = InstructorDatabase.getInstance();
        courseDataSet = CourseDataSet.getInstance();
       // System.out.println("Total courses: " + courseDataSet.getCourses().size());
      //  System.out.println("Initializing...");
        populateUnassignedCourses();

        // Add a listener to detect when a course is selected in the ListView
        UnassignedCourseList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayCourseDetails(newValue);
                populateInstructorsForCourse(newValue);
                selectedCourse = newValue.getCourse();

            }
        });


        instructorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayInstructorDetails(newValue);
                setInstructor.setDisable(false); // Enable the setInstructor button
                selectedInstructor = newValue; // Store the selected instructor for later use
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

        // Convert endTime from 24-hour to 12-hour format
        String endTime24Hour = selectedCourse.getEndTime().toString();
        SimpleDateFormat sdf24Hour = new SimpleDateFormat("HH:mm", Locale.US);
        SimpleDateFormat sdf12Hour = new SimpleDateFormat("hh:mm a", Locale.US);
        try {
            Date date = sdf24Hour.parse(endTime24Hour);
            String endTime12Hour = sdf12Hour.format(date);
            setEndTime.setText(endTime12Hour);
        } catch (ParseException e) {
            // Handle the parsing exception here if necessary
        }

        // Convert startTime from 24-hour to 12-hour format
        String startTime24Hour = selectedCourse.getBeginTime().toString();
        try {
            Date date = sdf24Hour.parse(startTime24Hour);
            String startTime12Hour = sdf12Hour.format(date);
            setStartTime.setText(startTime12Hour);
        } catch (ParseException e) {
            // Handle the parsing exception here if necessary
        }

        SetCourse.setText(selectedCourse.getCourse()); // Assuming the method name is getCourseName
    }

    private void populateInstructorsForCourse(Course selectedCourse) {
        ObservableList<Instructor> instructorsForCourse = FXCollections.observableArrayList();

        for (Instructor instructor : instructorDatabase.getAllInstructors()) {

            if (instructor.courseExists(selectedCourse.getCourse()) &&
                    instructor.isAvailableToTeach(selectedCourse) &&
                    instructor.canTeachAnotherCourse()) {

                instructorsForCourse.add(instructor);
            }
        }

        ObservableList<Instructor> sortedInstructors = instructorsForCourse.stream()
                .sorted(Comparator.comparingInt((Instructor instructor) ->
                                instructor.getCourseFrequency(selectedCourse.getCourse())).reversed()
                        .thenComparing(instructor -> instructor.getRank().ordinal()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        instructorList.setItems(sortedInstructors);
    }





    private void displayInstructorDetails(Instructor selectedInstructor) {

        SetInstructorName.setText(selectedInstructor.getName()); // Assuming the instructor class has a getName() method
        setInstructorID.setText(selectedInstructor.getID_no()); // Assuming the instructor class has an getId() method that returns int
        setRank.setText(selectedInstructor.getRank().toString());
        showFrequancy.setText("Frequancey: "+String.valueOf(selectedInstructor.getCourseFrequency(selectedCourse)));
    }



    @FXML
    private void handleHomeClick() {
        try {

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
