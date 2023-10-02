package com.example.teacherslink;

import java.util.TreeMap;

public class CourseDataSet {

    private static CourseDataSet instance;
    private TreeMap<Integer, Course> courses;

    private CourseDataSet() {
        courses = new TreeMap<>();
    }

    public static synchronized CourseDataSet getInstance() {
        if (instance == null) {
            instance = new CourseDataSet();
        }
        return instance;
    }

    public TreeMap<Integer, Course> getCourses() {
        return courses;
    }
}


