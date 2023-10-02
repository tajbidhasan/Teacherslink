package com.example.teacherslink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class CourseReader {

    private static final String CSV_FILE_PATH = "/CourseInformation.csv";

    public static void readCoursesFromCSV() {
        readCoursesFromCSV(CSV_FILE_PATH);
    }

    public static void readCoursesFromCSV(String file) {
        TreeMap<Integer, Course> courses = CourseDataSet.getInstance().getCourses();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(CourseReader.class.getResourceAsStream(file)))) {
            String line;
            br.readLine(); // Assuming the first line is the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 21) {
                    System.err.println("Error: Invalid line with insufficient fields: " + line);
                    continue;
                }
                String course = values[1] + values[2];
                String courseTitle = values[3].replace("\"", ""); // Removing double quotes
                int crn = Integer.parseInt(values[4].trim());
                String partOfTerm = values[6];
                String campus = values[7];
                String days = values[18];
                String beginTimeStr = values[19];
                String endTimeStr = values[20];

                Course courseObj = new Course(crn, course);
                courseObj.setCourseTitle(courseTitle);
                courseObj.setPartOfTerm(partOfTerm);
                courseObj.setCampus(campus);
                if (days == null || days.trim().isEmpty()) {
                    courseObj.setDays("ONLINE");
                } else {
                    courseObj.setDays(days);
                }

                if (beginTimeStr != null && !beginTimeStr.trim().isEmpty()) {
                    LocalTime parsedBeginTime = LocalTime.parse(beginTimeStr.trim(), formatter);
                    courseObj.setBeginTime(parsedBeginTime);
                } else {
                    // Here, instead of a warning, set the course as ONLINE CLASS for begin time
                    courseObj.setBeginTime(LocalTime.parse("00:00"));
                }

                if (endTimeStr != null && !endTimeStr.trim().isEmpty()) {
                    LocalTime parsedEndTime = LocalTime.parse(endTimeStr.trim(), formatter);
                    courseObj.setEndTime(parsedEndTime);
                } else {
                    // Here, instead of a warning, set the course as ONLINE CLASS for end time
                    courseObj.setEndTime(LocalTime.parse("00:00"));
                }

                courses.put(crn, courseObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
