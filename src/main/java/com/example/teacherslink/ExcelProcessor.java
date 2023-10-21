package com.example.teacherslink;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelProcessor {

    // Create an instance of the InstructorDatabase
    static InstructorDatabase db = InstructorDatabase.getInstance();

    public static void processExcelFile(String filePath) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(filePath)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

                Row currentRow = sheet.getRow(rowIndex);
                if (currentRow == null) {
                    continue;  // Skip the current iteration
                }
                Cell idCell = currentRow.getCell(0);

                String idCellValue = getCellValue(idCell);

                if (idCell != null && isValidId(idCellValue)) {
                    Instructor instructor = new Instructor();

                    instructor.setID_no(idCellValue);
                    instructor.setHome_campus(getCellValue(sheet.getRow(rowIndex + 1).getCell(0)));
                    instructor.setBusiness_number(getCellValue(sheet.getRow(rowIndex + 2).getCell(0)));

                    instructor.setName(getCellValue(currentRow.getCell(1)));
                    instructor.setAddress(getCellValue(sheet.getRow(rowIndex + 1).getCell(1)));
                    instructor.setCity_state_zip(getCellValue(sheet.getRow(rowIndex + 2).getCell(1)));

                    instructor.setHome_phone(getCellValue(currentRow.getCell(2)));
                    instructor.setCollege_date(getCellValue(sheet.getRow(rowIndex + 1).getCell(2)));
                    instructor.setCourse(getCellValue(sheet.getRow(rowIndex + 2).getCell(2)));

                    String cellValue = getCellValue(currentRow.getCell(3));

                    if ("A1".equals(cellValue)) {
                        instructor.setRank(Ranks.PROF);
                    } else if ("A2".equals(cellValue)) {
                        instructor.setRank(Ranks.ASSOCIATE_PROF);
                    } else if ("A3".equals(cellValue)) {
                        instructor.setRank(Ranks.ASSISTANT_PROF);
                    } else if ("A4".equals(cellValue)) {
                        instructor.setRank(Ranks.LECTURER);
                    }

                    if ("Y".equalsIgnoreCase(getCellValue(currentRow.getCell(4)))) {
                        instructor.setOnline(true);
                    } else {
                        instructor.setOnline(false);
                    }

                    instructor.setCampus(getCellValue(currentRow.getCell(5)));

                    if ("Y".equalsIgnoreCase(getCellValue(currentRow.getCell(6)))) {
                        instructor.setSecond_course(true);
                    } else {
                        instructor.setSecond_course(false);
                    }

                    if ("Y".equalsIgnoreCase(getCellValue(sheet.getRow(rowIndex + 1).getCell(6)))) {
                        instructor.setThird_course(true);
                    } else {
                        instructor.setThird_course(false);
                    }

                    String sevenToEightAMAvailability = getCellValue(currentRow.getCell(8));

                    setAvailability(instructor, sevenToEightAMAvailability, 0);

                    String amAvailability = getCellValue(sheet.getRow(rowIndex + 1).getCell(8));

                    setAvailability(instructor, amAvailability, 1);

                    String threeToFourPMAvailability = getCellValue(currentRow.getCell(9));

                    setAvailability(instructor, threeToFourPMAvailability, 2);

                    String pmAvailability = getCellValue(sheet.getRow(rowIndex + 1).getCell(9));

                    setAvailability(instructor, pmAvailability, 3);

                    if ("sat".equalsIgnoreCase(getCellValue(currentRow.getCell(10)))) {
                        instructor.setSaturday(true);
                    }
                    if ("sun".equalsIgnoreCase(getCellValue(sheet.getRow(rowIndex + 1).getCell(10)))) {
                        instructor.setSunday(true);
                    }

                    String lateAfternoonAvailability = getCellValue(currentRow.getCell(11));

                    setAvailability(instructor, lateAfternoonAvailability, 4);

                    String eveningAvailability = getCellValue(currentRow.getCell(12));

                    setAvailability(instructor, eveningAvailability, 5);

                    instructor.setFall_workload(getCellValue(currentRow.getCell(14)));
                    db.addOrUpdateInstructor(instructor);

                    rowIndex += 2;  // Since we've processed this instructor, skip the next two rows
                }
            }

            System.out.println(db.getInstructorCount());
            db.printAllInstructors();
        }
    }
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";  // or return null, based on your preference
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
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
                    instructor.getMon_friday()[1][column] = true;  // Tuesday
                    // Check for 'T T' pattern for Thursday
                    if (i + 2 < availability.length() && availability.charAt(i + 2) == 'T') {
                        instructor.getMon_friday()[3][column] = true;  // Thursday
                    }
                    break;
                case 'W':
                    instructor.getMon_friday()[2][column] = true;  // Wednesday
                    break;
                case 'F':
                    instructor.getMon_friday()[4][column] = true;  // Friday
                    break;
            }
        }
    }



    private static boolean isValidId(String value) {
        // Check if the value is exactly 8 characters long
        return value != null && value.length() == 8;
    }
}
