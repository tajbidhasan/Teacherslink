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
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class CourseController implements Serializable {

    @FXML
    private Button homeButton;

    @FXML
    private ListView<Course> listView;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchbar;

    @FXML
    private Label setBeginTime;

    @FXML
    private Label setCampus;

    @FXML
    private Label setCourseName;

    @FXML
    private Label setCourseTitle;

    @FXML
    private Label setCrn;

    @FXML
    private Label setDays;

    @FXML
    private Label setEndTime;

    @FXML
    private Label setPOT;
    @FXML
    private Label setInstructor;
    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
        searchCourses();
    }

    @FXML
    public void initialize() {
        populateListView(); // this line to initially populate the listView

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayCourseDetails(newSelection);
            }
        });
    }

    private void displayCourseDetails(Course course) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h:mma");

        setCourseName.setText(course.getCourse());
        setInstructor.setText(course.getInstructor().getName());
        setCourseTitle.setText(course.getCourseTitle());
        setCrn.setText(String.valueOf(course.getCrn()));
        setPOT.setText(course.getPartOfTerm());
        setCampus.setText(course.getCampus());
        setDays.setText(course.getDays());
        setBeginTime.setText(course.getBeginTime().format(outputFormatter));  // Format to 12-hour time with AM/PM
        setEndTime.setText(course.getEndTime().format(outputFormatter));      // Format to 12-hour time with AM/PM

    }


    private void populateListView() {
        ObservableList<Course> courseList = FXCollections.observableArrayList(
                CourseDataSet.getInstance().getCourses().values()
                        .stream()
                        .filter(course -> course.getInstructor() != null && !course.getInstructor().getName().isEmpty())
                        .collect(Collectors.toList())
        );

        listView.setItems(courseList);

        // Select the first course in the list by default
        if (!courseList.isEmpty()) {
            listView.getSelectionModel().select(0);
            displayCourseDetails(courseList.get(0));
        }
    }



    private void searchCourses() {
        String query = searchbar.getText().trim();
        if (query.isEmpty()) {
            populateListView(); // if search bar is empty, show all courses
            return;
        }

        ObservableList<Course> filteredList = FXCollections.observableArrayList();
        for (Course course : CourseDataSet.getInstance().getCourses().values()) {
            if (course.getCourse().contains(query) || String.valueOf(course.getCrn()).contains(query)) {
                filteredList.add(course);
            }
        }
        listView.setItems(filteredList);
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

