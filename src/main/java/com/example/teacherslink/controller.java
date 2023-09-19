package com.example.teacherslink;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.teacherslink.ExcelProcessor.db;

public class controller {

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
    private ListView<Instructor> listview;

    // This should be of type Instructor now
    private ObservableList<Instructor> instructors = FXCollections.observableArrayList();




    @FXML
    protected void onChooseFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());
        if (selectedFile != null) {
            try {
                ExcelProcessor.processExcelFile(selectedFile.getPath());
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
            rankLabel.setText(instructor.getRank());
            homeCampusLabel.setText(instructor.getHome_campus());
            preferredCampusLabel.setText(instructor.getCampus());
            onlineCertLabel.setText(instructor.getOnline());
            coursesLabel.setText(instructor.getCourse());
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
        // Set the initial position of the divider for splitPane1 to 0.33 (33%)
        splitPane1.setDividerPositions(0.36);

        // Add a listener to the divider's position property to make it non-movable
        splitPane1.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            splitPane1.setDividerPositions(0.36);
        });

        // Set the initial position of the divider for splitPane2 to 0.45 (45%)
        splitPane2.setDividerPositions(0.55);

        // Add a listener to the divider's position property to make it non-movable
        splitPane2.getDividers().get(0).positionProperty().addListener((obs, oldVal, newVal) -> {
            splitPane2.setDividerPositions(0.55);
        });
        listview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayInstructorDetailsByID(newSelection.getID_no());
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




}
