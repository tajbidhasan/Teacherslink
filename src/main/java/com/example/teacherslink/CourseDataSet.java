package com.example.teacherslink;

import java.io.Serializable;
import java.util.TreeMap;

public class CourseDataSet implements Serializable{
    private static final long serialVersionUID = 1L; // Use the correct serialVersionUID value

    private static CourseDataSet instance;
    private TreeMap<Integer, Course> courses;

    private CourseDataSet() {
        courses = new TreeMap<>();
    }
    // Method to set the static instance variable after deserialization
    public static void setInstance(CourseDataSet newInstance) {
        if (newInstance != null) {
            instance = newInstance;
        }
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


