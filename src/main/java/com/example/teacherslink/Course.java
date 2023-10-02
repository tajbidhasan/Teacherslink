package com.example.teacherslink;


import java.time.LocalTime;

public class Course {

    private String course;        // subject + course number
    private String courseTitle;   // course title
    private int crn;              // a unique number for each section
    private String partOfTerm;    // Part of Term
    private String campus;        // campus the course is offered at
    private String instructionMethod;  // instruction method
    private String days;          // days offered in the week
    private LocalTime beginTime;  // time that class begins
    private LocalTime endTime;    // time that class ends


    // Constructor
    public Course(int crn, String course) {
        this.crn = crn;
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public String getPartOfTerm() {
        return partOfTerm;
    }

    public void setPartOfTerm(String partOfTerm) {
        this.partOfTerm = partOfTerm;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getInstructionMethod() {
        return instructionMethod;
    }

    public void setInstructionMethod(String instructionMethod) {
        this.instructionMethod = instructionMethod;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CRN: " + crn + " | " + course + " - " + courseTitle;
    }

}


