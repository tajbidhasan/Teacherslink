package com.example.teacherslink;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelProcessor {

    // Create an instance of the InstructorDatabase
    static InstructorDatabase db = InstructorDatabase.getInstance();

    public static void processExcelFile(String filePath) throws IOException {
        // Load the Excel file
        //XSSFWorkbook workbook = new XSSFWorkbook(filePath);
        //XSSFSheet sheet = workbook.getSheetAt(0);
        try (XSSFWorkbook workbook = new XSSFWorkbook(filePath)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                Cell idCell = currentRow.getCell(0);

                // Check if the current cell contains a valid ID
                if (idCell != null && isValidId(idCell.getStringCellValue())) {
                    Instructor instructor = new Instructor();

                    instructor.setID_no(idCell.getStringCellValue());
                    instructor.setHome_campus(sheet.getRow(rowIndex + 1).getCell(0).getStringCellValue());
                    instructor.setBusiness_number(sheet.getRow(rowIndex + 2).getCell(0).getStringCellValue());

                    instructor.setName(currentRow.getCell(1).getStringCellValue());
                    instructor.setAddress(sheet.getRow(rowIndex + 1).getCell(1).getStringCellValue());
                    instructor.setCity_state_zip(sheet.getRow(rowIndex + 2).getCell(1).getStringCellValue());

                    instructor.setHome_phone(currentRow.getCell(2).getStringCellValue());
                    instructor.setCollege_date(sheet.getRow(rowIndex + 1).getCell(2).getStringCellValue());
                    instructor.setCourse(sheet.getRow(rowIndex + 2).getCell(2).getStringCellValue());

                    instructor.setRank(currentRow.getCell(3).getStringCellValue());

                    if ("Y".equalsIgnoreCase(currentRow.getCell(4).getStringCellValue())) {
                        instructor.setOnline(true);
                    } else {
                        instructor.setOnline(false);
                    }

                    //changing 11/09/23
                    instructor.setCampus(currentRow.getCell(5).getStringCellValue());


                    // Check the value of cell 6 in the currentRow
                    if ("Y".equalsIgnoreCase(currentRow.getCell(6).getStringCellValue())) {
                        instructor.setSecond_course(true);
                    } else {
                        instructor.setSecond_course(false);
                    }

// Check the value of cell 6 in the next row
                    if ("Y".equalsIgnoreCase(sheet.getRow(rowIndex + 1).getCell(6).getStringCellValue())) {
                        instructor.setThird_course(true);
                    } else {
                        instructor.setThird_course(false);
                    }


                    String sevenToEightAMAvailability = currentRow.getCell(8).getStringCellValue();
                    instructor.setSevenToEight_am_classes(sevenToEightAMAvailability);
                    setAvailability(instructor, sevenToEightAMAvailability, 0);

                    String amAvailability = sheet.getRow(rowIndex + 1).getCell(8).getStringCellValue();
                    instructor.setAM_classes(amAvailability);
                    setAvailability(instructor, amAvailability, 1);

                    String threeToFourPMAvailability = currentRow.getCell(9).getStringCellValue();
                    instructor.setThreeToFour_pm_classes(threeToFourPMAvailability);
                    setAvailability(instructor, threeToFourPMAvailability, 2);


                    String pmAvailability = sheet.getRow(rowIndex + 1).getCell(9).getStringCellValue();
                    instructor.setPM_classes(pmAvailability);
                    setAvailability(instructor, pmAvailability, 3);


                    if ("sat".equalsIgnoreCase(currentRow.getCell(10).getStringCellValue())) {
                        instructor.setSaturday(true);
                    }
                    if ("sun".equalsIgnoreCase(sheet.getRow(rowIndex + 1).getCell(10).getStringCellValue())) {
                        instructor.setSunday(true);
                    }

                    String lateAfternoonAvailability = currentRow.getCell(11).getStringCellValue();
                    instructor.setLate_afternoon_classes(lateAfternoonAvailability);
                    setAvailability(instructor, lateAfternoonAvailability, 4);

                    String eveningAvailability = currentRow.getCell(12).getStringCellValue();
                    instructor.setEvening_classes(eveningAvailability);
                    setAvailability(instructor, eveningAvailability, 5);

                    instructor.setFall_workload(currentRow.getCell(14).getStringCellValue());
                    db.addOrUpdateInstructor(instructor);


                    rowIndex += 2;  // Since we've processed this instructor, skip the next two rows
                }
                // If it's not a valid ID cell, the loop will just go to the next row in the next iteration.
            }


            // Print the number of instructors in the database
            System.out.println(db.getInstructorCount());

            // Print all instructors
            db.printAllInstructors();
        }
    }
    private static void setAvailability(Instructor instructor, String availability, int column) {
        // Remove any leading asterisks or spaces
        availability = availability.replaceAll("^[*\\s]+", "");

        for (int i = 0; i < availability.length(); i++) {
            char dayChar = availability.charAt(i);
            switch (dayChar) {
                case 'M':
                    instructor.getMon_friday()[0][column] = true;
                    break;
                case 'T':
                    // If the current 'T' is preceded by 'W'
                    if (i - 1 >= 0 && availability.charAt(i - 1) == 'W') {
                        instructor.getMon_friday()[1][column] = true;  // Tuesday
                    }
                    // If the current 'T' is the last character, or if it's followed by any character other than a space or another 'T'
                    else if (i == availability.length() - 1 || (i + 1 < availability.length() && availability.charAt(i + 1) != ' ' && availability.charAt(i + 1) != 'T')) {
                        instructor.getMon_friday()[3][column] = true;  // Thursday
                    }
                    // Otherwise, treat it as Tuesday
                    else {
                        instructor.getMon_friday()[1][column] = true;  // Tuesday
                    }
                    break;
                case 'W':
                    instructor.getMon_friday()[2][column] = true;
                    break;
                case 'F':
                    instructor.getMon_friday()[4][column] = true;
                    break;
            }
        }
    }


    private static boolean isValidId(String value) {
        // Check if the value is exactly 8 characters long
        return value != null && value.length() == 8;
    }
}
