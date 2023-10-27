package com.example.teacherslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class controller implements Serializable {

    InstructorDatabase db = InstructorDatabase.getInstance();

    @FXML
    private SplitPane splitPane1;
    @FXML
    private SplitPane splitPane2;

    @FXML
    private Button chooseFileButton;
    @FXML
    private Button searchButton;

    @FXML
    private Label coursesLabel;

    @FXML
    private Button friday3pmButton;

    @FXML
    private Button friday7amButton;

    @FXML
    private Button fridayAmButton;

    @FXML
    private Button fridayEveningButton;

    @FXML
    private Button fridayLateAfternoonButton;

    @FXML
    private Button fridayPmButton;

    @FXML
    private Label homeCampusLabel;

    @FXML
    private Label idLabel;



    @FXML
    private Button monday3pmButton;

    @FXML
    private Button monday7amButton;

    @FXML
    private Button mondayAmButton;

    @FXML
    private Button mondayEveningButton;

    @FXML
    private Button mondayLateAfternoonButton;

    @FXML
    private Button mondayPmButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label onlineCertLabel;

    @FXML
    private Label preferredCampusLabel;

    @FXML
    private Label rankLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Label secondThirdCourseLabel;

    @FXML
    private Button thursday3pmButton;

    @FXML
    private Button thursday7amButton;

    @FXML
    private Button thursdayAmButton;

    @FXML
    private Button thursdayEveningButton;

    @FXML
    private Button thursdayLateAfternoonButton;

    @FXML
    private Button thursdayPmButton;

    @FXML
    private Button tuesday3pmButton;

    @FXML
    private Button tuesday7amButton;

    @FXML
    private Button tuesdayAmButton;

    @FXML
    private Button tuesdayEveningButton;

    @FXML
    private Button tuesdayLateAfternoonButton;

    @FXML
    private Button tuesdayPmButton;

    @FXML
    private Button wednesday3pmButton;

    @FXML
    private Button wednesday7amButton;

    @FXML
    private Button wednesdayAmButton;

    @FXML
    private Button wednesdayEveningButton;

    @FXML
    private Button wednesdayLateAfternoonButton;

    @FXML
    private Button wednesdayPmButton;
    @FXML
    private Button sundayButton;

    @FXML
    private Button saturdayButton;
    @FXML
    private Button homeButton;

    @FXML
    private ListView<Instructor> listview;

    // This should be of type Instructor now
    private ObservableList<Instructor> instructors = FXCollections.observableArrayList();


    @FXML
    private void handleHomeClick() {
        try {
           // CourseReader.readCoursesFromCSV(); (test)
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

    @FXML
    protected void onChooseFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

        // Set default file path
        File defaultFile = new File("/Users/tajbidhasan/Desktop/CS248/Teacherslink/src/main/resources/Instructors.xlsx");
        if (defaultFile.exists()) {
            fileChooser.setInitialDirectory(defaultFile.getParentFile()); // set the parent directory of the file
            fileChooser.setInitialFileName(defaultFile.getName()); // set the default filename
        }

        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                ExcelProcessor.processExcelFile(selectedFile.getPath());
               // System.out.println(selectedFile.getPath()); (test)
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Excel file processed successfully!");
                List<Instructor> allInstructors = db.getAllInstructors(); // Assuming this method returns a List of Instructor objects
                instructors.clear();
                instructors.addAll(allInstructors);
                listview.setItems(instructors);

                alert.showAndWait();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error processing Excel file!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file selected!");
            alert.showAndWait();
        }
    }

    private void displayInstructorDetailsByID(String idNo) {
        Instructor instructor = db.getInstructor(idNo);
        if (instructor != null) {
            nameLabel.setText(instructor.getName());
            idLabel.setText(instructor.getID_no());
            rankLabel.setText(instructor.getRank().toString());
            homeCampusLabel.setText(instructor.getHome_campus());
            preferredCampusLabel.setText(instructor.getCampus());
            onlineCertLabel.setText(String.valueOf(instructor.getOnline()));
            coursesLabel.setText(instructor.getAssignedCourseNames());
            secondThirdCourseLabel.setText(instructor.getSecond_course() + "/" + instructor.getThird_course());

            // Set button colors based on availability
            // Monday
            setButtonColor(instructor.getMon_friday()[0][0], monday7amButton);
            setButtonColor(instructor.getMon_friday()[0][1], mondayAmButton);
            setButtonColor(instructor.getMon_friday()[0][2], mondayPmButton);
            setButtonColor(instructor.getMon_friday()[0][3], monday3pmButton);
            setButtonColor(instructor.getMon_friday()[0][4], mondayLateAfternoonButton);
            setButtonColor(instructor.getMon_friday()[0][5], mondayEveningButton);

// Tuesday
            setButtonColor(instructor.getMon_friday()[1][0], tuesday7amButton);
            setButtonColor(instructor.getMon_friday()[1][1], tuesdayAmButton);
            setButtonColor(instructor.getMon_friday()[1][2], tuesdayPmButton);
            setButtonColor(instructor.getMon_friday()[1][3], tuesday3pmButton);
            setButtonColor(instructor.getMon_friday()[1][4], tuesdayLateAfternoonButton);
            setButtonColor(instructor.getMon_friday()[1][5], tuesdayEveningButton);

// Wednesday
            setButtonColor(instructor.getMon_friday()[2][0], wednesday7amButton);
            setButtonColor(instructor.getMon_friday()[2][1], wednesdayAmButton);
            setButtonColor(instructor.getMon_friday()[2][2], wednesdayPmButton);
            setButtonColor(instructor.getMon_friday()[2][3], wednesday3pmButton);
            setButtonColor(instructor.getMon_friday()[2][4], wednesdayLateAfternoonButton);
            setButtonColor(instructor.getMon_friday()[2][5], wednesdayEveningButton);

// Thursday
            setButtonColor(instructor.getMon_friday()[3][0], thursday7amButton);
            setButtonColor(instructor.getMon_friday()[3][1], thursdayAmButton);
            setButtonColor(instructor.getMon_friday()[3][2], thursdayPmButton);
            setButtonColor(instructor.getMon_friday()[3][3], thursday3pmButton);
            setButtonColor(instructor.getMon_friday()[3][4], thursdayLateAfternoonButton);
            setButtonColor(instructor.getMon_friday()[3][5], thursdayEveningButton);

// Friday
            setButtonColor(instructor.getMon_friday()[4][0], friday7amButton);
            setButtonColor(instructor.getMon_friday()[4][1], fridayAmButton);
            setButtonColor(instructor.getMon_friday()[4][2], fridayPmButton);
            setButtonColor(instructor.getMon_friday()[4][3], friday3pmButton);
            setButtonColor(instructor.getMon_friday()[4][4], fridayLateAfternoonButton);
            setButtonColor(instructor.getMon_friday()[4][5], fridayEveningButton);

            setButtonColor(instructor.isSaturday(), saturdayButton);
            setButtonColor(instructor.isSunday(), sundayButton);
        } else {
            clearLabels();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Instructor not found!");
            alert.showAndWait();
        }
    }

    @FXML
    protected void onSearchButtonClick() {
        String idNo = searchField.getText();
        displayInstructorDetailsByID(idNo);
    }



    public void initialize() {
        splitPane1.setDividerPositions(0.36);
        splitPane1.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            splitPane1.setDividerPositions(0.36);
        });

        splitPane2.setDividerPositions(0.55);
        splitPane2.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            splitPane2.setDividerPositions(0.55);
        });

        // Directly fetch the instructors from the database and set them to the listview
        List<Instructor> allInstructors = db.getAllInstructors();
        instructors.clear();
        instructors.addAll(allInstructors);
        listview.setItems(instructors);

        listview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayInstructorDetailsByID(newSelection.getID_no());
               // newSelection.printCourses(); (test)
            }
        });
    }



    private void setButtonColor(boolean availability, Button button) {
        if (availability) {
            button.setStyle("-fx-background-color: green;");
        } else {
            button.setStyle("-fx-background-color: red;");
        }
    }
    private void clearLabels() {
        nameLabel.setText("");
        idLabel.setText("");
        rankLabel.setText("");
        homeCampusLabel.setText("");
        preferredCampusLabel.setText("");
        onlineCertLabel.setText("");
        coursesLabel.setText("");
        secondThirdCourseLabel.setText("");

    }
    private void processDefaultExcelFile() {
        File defaultFile = new File("/Users/tajbidhasan/Desktop/CS248/Teacherslink/src/main/resources/Instructors.xlsx");
        if (defaultFile.exists()) {
            try {
                ExcelProcessor.processExcelFile(defaultFile.getPath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Default Excel file processed successfully!");
                List<Instructor> allInstructors = db.getAllInstructors(); // Assuming this method returns a List of Instructor objects
                instructors.clear();
                instructors.addAll(allInstructors);
                listview.setItems(instructors);


            } catch (IOException ioException) {
                ioException.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error processing default Excel file!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Default file not found!");
            alert.showAndWait();
        }

    }




}
