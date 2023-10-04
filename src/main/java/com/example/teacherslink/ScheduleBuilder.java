package com.example.teacherslink;

import java.util.ArrayList;
import java.util.List;

public class ScheduleBuilder {
    private InstructorDatabase instructorDatabase;
    private CourseDataSet courseDataSet;

    public ScheduleBuilder() {
        instructorDatabase = InstructorDatabase.getInstance();
        courseDataSet = CourseDataSet.getInstance();  // Assuming there's a singleton for course data as well.
    }

    public void assignCourseToInstructor(String courseId, String instructorId) {
        Course course = courseDataSet.getCourses().get(courseId);
        Instructor instructor = instructorDatabase.getInstructor(instructorId);

        if (course == null || instructor == null) {
            System.out.println("Invalid course or instructor ID.");
            return;
        }

        // Check if instructor is certified to teach the course
        if (!instructor.courseExists(courseId)) {
            System.out.println("Instructor is not certified to teach this course.");
            suggestInstructorForCourse(courseId);
            return;
        }

        // Check if instructor can teach another course
        if (!instructor.canTeachAnotherCourse()) {
            System.out.println("Instructor " + instructor.getName() + " has reached the maximum number of courses they can teach.");
            return;
        }

        TimeRange courseTimeRange = new TimeRange(course.getBeginTime(), course.getEndTime());

// Instead of creating a new TimeRange, use the already created TimeRange objects directly
        TimeRange instructorAvailableFrom = instructor.getAvailableFrom();
        TimeRange instructorAvailableTo = instructor.getAvailableTo();

        if (!instructor.isAvailableDuring(courseTimeRange)) {
            System.out.println("Instructor is not available during this course time.");
            suggestInstructorForCourse(courseId);
            return;
        }

        // If everything's fine, add the course to instructor's list
        instructor.assignCourse(course);
        System.out.println("Course assigned to instructor successfully!");
    }

    public void suggestInstructorForCourse(String courseId) {
        Course course = courseDataSet.getCourses().get(courseId);

        List<Instructor> suitableInstructors = new ArrayList<>();

        TimeRange courseTimeRange = new TimeRange(course.getBeginTime(), course.getEndTime());

        for (Instructor instructor : instructorDatabase.getAllInstructors()) {
            TimeRange instructorAvailableFrom = instructor.getAvailableFrom();
            TimeRange instructorAvailableTo = instructor.getAvailableTo();

            if (!instructor.isAvailableDuring(courseTimeRange)) {
                System.out.println("Instructor " + instructor.getName() + " is not available during this course time.");
                continue;  // continue to the next instructor in the loop
            }

            if (instructor.courseExists(courseId) &&
                    instructor.isAvailableDuring(course.getBeginTime(), course.getEndTime()) &&
                    instructor.canTeachAnotherCourse()) {
                suitableInstructors.add(instructor);
            }
        }

        if (!suitableInstructors.isEmpty()) {
            System.out.println("Suitable instructors for the course are:");
            for (Instructor instructor : suitableInstructors) {
                System.out.println(instructor);
            }
        } else {
            System.out.println("No suitable instructors available for this course.");
        }
    }




}
