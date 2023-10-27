package com.example.teacherslink;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelWriter {
    private static String convertTo12HourFormat(String time24) {
        try {
            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm");
            Date date = sdf24.parse(time24);
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm a");
            return sdf12.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return time24; // Return original if there's an error
        }
    }

    public static void writeCoursesWithInstructorsToFile(String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Courses with Instructors");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("CRN");
            headerRow.createCell(1).setCellValue("Courses");
            headerRow.createCell(2).setCellValue("Days");
            headerRow.createCell(3).setCellValue("Start Time");
            headerRow.createCell(4).setCellValue("End Time");
            headerRow.createCell(5).setCellValue("Instructors");

            int rowNum = 1; // start from second row as first is header
            for (Course course : CourseDataSet.getInstance().getCourses().values()) {
                Instructor instructor = course.getInstructor();
                if (instructor != null) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(course.getCrn());
                    row.createCell(1).setCellValue(course.getCourse());
                    row.createCell(2).setCellValue(course.getDays());
                    row.createCell(3).setCellValue(convertTo12HourFormat(course.getBeginTime().toString()));
                    row.createCell(4).setCellValue(convertTo12HourFormat(course.getEndTime().toString()));
                    row.createCell(5).setCellValue(instructor.getName());
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeInstructorsWithCoursesToFile(String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Instructors with Courses");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Instructor ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Assigned Course 1");
            headerRow.createCell(3).setCellValue("Assigned Course 2");
            headerRow.createCell(4).setCellValue("Assigned Course 3");
            headerRow.createCell(5).setCellValue("Assigned Course 4");
            headerRow.createCell(6).setCellValue("Assigned Course 5");

            int rowNum = 1;
            for (Instructor instructor : InstructorDatabase.getInstance().getAllInstructors()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(instructor.getID_no());
                row.createCell(1).setCellValue(instructor.getName());

                List<Course> assignedCourses = instructor.getAssignedCourses();
                if (assignedCourses != null) {
                    for (int i = 0, col = 2; i < assignedCourses.size() && col <= 6; i++) {
                        Course course = assignedCourses.get(i);
                        if (course != null) {
                            String courseDetails = String.format("%s (CRN: %s, Days: %s, Time: %s-%s)",
                                    course.getCourse(),
                                    course.getCrn(),
                                    course.getDays(),
                                    convertTo12HourFormat(course.getBeginTime().toString()),
                                    convertTo12HourFormat(course.getEndTime().toString()));
                            row.createCell(col++).setCellValue(courseDetails);
                        }
                    }
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(filename)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

