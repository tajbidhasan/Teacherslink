package com.example.teacherslink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InstructorDatabase {

    // Static instance of the class
    private static final InstructorDatabase INSTANCE = new InstructorDatabase();

    private Map<String, Instructor> instructorsMap;

    // Private constructor ensures no other instances can be created from outside this class
    private InstructorDatabase() {
        this.instructorsMap = new TreeMap<>();
    }

    // Public method to provide access to the instance
    public static InstructorDatabase getInstance() {
        return INSTANCE;
    }

    // Add or update an instructor in the database
    public void addOrUpdateInstructor(Instructor instructor) {
        instructorsMap.put(instructor.getID_no(), instructor);
    }

    // Get an instructor from the database by idNo
    public Instructor getInstructor(String idNo) {
        return instructorsMap.get(idNo);
    }

    // Remove an instructor from the database by idNo
    public void removeInstructor(String idNo) {
        instructorsMap.remove(idNo);
    }

    // Check if an instructor exists in the database by idNo
    public boolean containsInstructor(String idNo) {
        return instructorsMap.containsKey(idNo);
    }

    // Get the count of instructors in the database
    public int getInstructorCount() {
        return instructorsMap.size();
    }

    // Check if the database is empty
    public boolean isEmpty() {
        return instructorsMap.isEmpty();
    }

    // Print all instructors
    public void printAllInstructors() {
        for (Instructor instructor : instructorsMap.values()) {
            System.out.println(instructor); // Assumes the Instructor class has a suitable toString() method
        }
    }

    public List<String> getAllInstructorNames() {
        List<String> names = new ArrayList<>();

        for (Instructor instructor : instructorsMap.values()) {
            names.add(instructor.getName());  // Assuming getName() is a method in the Instructor class
        }

        return names;
    }
    public String getInstructorIDByName(String name) {
        for (Map.Entry<String, Instructor> entry : instructorsMap.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getKey();  // return the instructor ID
            }
        }
        return null; // return null if not found
    }
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructorsMap.values());
    }

    // Other methods as needed...
}
