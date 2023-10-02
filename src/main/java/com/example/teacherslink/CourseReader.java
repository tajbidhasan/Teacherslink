package com.example.teacherslink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class CourseReader {

    private static final String CSV_FILE_PATH = "/Users/tajbidhasan/Desktop/CS248/Teacherslink/src/main/resources/CourseInformation.csv";

    public static void readCoursesFromCSV() {
        readCoursesFromCSV(CSV_FILE_PATH);
    }

    public static void readCoursesFromCSV(String file) {
        TreeMap<Integer, Course> courses = CourseDataSet.getInstance().getCourses();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(CourseReader.class.getResourceAsStream(file)))) {
            String line;
            br.readLine(); // Assuming the first line is the header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 21) { // 21 because we are accessing up to values[20]
                    System.err.println("Error: Invalid line with insufficient fields: " + line);
                    continue; // skip processing this line
                }
                String course = values[1] + values[2];
                String courseTitle = values[3];
                int crn = Integer.parseInt(values[4].trim());
                String partOfTerm = values[6];
                String campus = values[7];
                String days = values[18];
                String beginTime = values[19];
                String endTime = values[20];

                Course courseObj = new Course(crn, course);
                courseObj.setCourseTitle(courseTitle);
                courseObj.setPartOfTerm(partOfTerm);
                courseObj.setCampus(campus);
                courseObj.setDays(days);
                courseObj.setBeginTime(beginTime);
                courseObj.setEndTime(endTime);

                //System.out.println(courseObj);

                courses.put(crn, courseObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

