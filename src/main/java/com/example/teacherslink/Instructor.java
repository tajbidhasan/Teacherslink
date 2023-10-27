package com.example.teacherslink;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Instructor implements Serializable {

    private Ranks rank;
    private String ID_no;
    private  String Home_campus;
    private String Business_number;
    private String name;
    private String address;
    private String City_state_zip;
    private String Home_phone;
    private String college_date;
    private HashMap<String, Integer> courses = new HashMap<>();
   // String rank;
    Boolean Online;
    String campus;
    Boolean Second_course;
    Boolean Third_course;




    String fall_workload;
    private TimeRange availableFrom;
    private TimeRange availableTo;
    private List<Course> assignedCourses = new ArrayList<>();
    private boolean[][] mon_friday = new boolean[5][6];
    private boolean saturday;
    private boolean sunday;

    public boolean[][] getMon_friday() {
        return mon_friday;
    }

    public void setMon_friday(boolean[][] mon_friday) {
        this.mon_friday = mon_friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getID_no() {
        return ID_no;
    }

    public void setID_no(String ID_no) {
        this.ID_no = ID_no;
    }

    public String getHome_campus() {
        return Home_campus;
    }

    public void setHome_campus(String home_campus) {
        Home_campus = home_campus;
    }


    public void setBusiness_number(String business_number) {
        Business_number = business_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public void setCity_state_zip(String city_state_zip) {
        City_state_zip = city_state_zip;
    }


    public void setHome_phone(String home_phone) {
        Home_phone = home_phone;
    }


    public void setRank(Ranks rank) {
        this.rank = rank;
    }

    public void setCollege_date(String college_date) {
        this.college_date = college_date;
    }

    public String getCoursesAsString() {
        return String.join(", ", courses.keySet());
    }

    public void setCourse(String coursesString) {
        String[] coursesArray = coursesString.split(" ");
        for(String course : coursesArray) {
            courses.put(course.trim(), 0);
        }
    }
    public int getCourseFrequency(String courseName) {
        // Check if the course exists in the map
        if (courses.containsKey(courseName)) {
            return courses.get(courseName);
        } else {
            System.out.println("Course " + courseName + " does not exist for this instructor.");
            return -1; // Return -1 or any other value to indicate that the course doesn't exist
        }
    }


    public void setCourseFrequency(String courseName, int frequency) {
        if (courses.containsKey(courseName)) {
            courses.put(courseName, frequency);
        } else {
            System.out.println("Course " + courseName + " does not exist.");
        }
    }
    public Ranks getRank() {
        return rank;
    }



    public Boolean getOnline() {
        return Online;
    }

    public void setOnline(Boolean online) {
        Online = online;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Boolean getSecond_course() {
        return Second_course;
    }

    public void setSecond_course(Boolean second_course) {
        Second_course = second_course;
    }

    public Boolean getThird_course() {
        return Third_course;
    }

    public void setThird_course(Boolean third_course) {
        Third_course = third_course;
    }











    public void setFall_workload(String fall_workload) {
        this.fall_workload = fall_workload;
    }


    public boolean courseExists(String courseToCheck) {
        return courses.containsKey(courseToCheck);
    }
    public void printCourses() {
        System.out.println("Courses and their frequencies:");
        for (Map.Entry<String, Integer> entry : courses.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // test for Availability
    private String array2DToString(boolean[][] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(Arrays.toString(array[i]));
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public boolean isAvailableToTeach(Course course) {
        if("ONLINE".equals(course.getDays()) && Online){
            return true;
        }
        if("S".equals(course.getDays()) && isSaturday()){
            return true;
        }
        // Convert the course's start and end times to a TimeRange.
        TimeRange courseTimeRange = new TimeRange(course.getBeginTime(), course.getEndTime());

        // Determine which period this TimeRange falls under.
        Period coursePeriod = null;
        for (Period period : Period.values()) {
            if (period.getTimeRange().isOverlapping(courseTimeRange)) {
                coursePeriod = period;
                break;
            }
        }

        if (coursePeriod == null) {
            return false; // The course's time doesn't match any known period.
        }
        String days = course.getDays(); // Example: "MTR"
        for (char dayChar : days.toCharArray()) {
            int dayIndex;
            switch (dayChar) {
                case 'M':
                    dayIndex = 0;
                    break;
                case 'T':
                    dayIndex = 1;
                    break;
                case 'W':
                    dayIndex = 2;
                    break;
                case 'R':
                    dayIndex = 3;
                    break;
                case 'F':
                    dayIndex = 4;
                    break;
                default:
                    return false; // Unknown day character.
            }

            int periodIndex = coursePeriod.ordinal();
            if (!mon_friday[dayIndex][periodIndex]) {
                return false; // The instructor is not available during this day and period.
            }
        }

        return true; // The instructor is available for all days and the period of the course.
    }



    // New methods to handle course assignments
    public boolean canTeachAnotherCourse() {

        return assignedCourses.size() < 5; // 5 is the maximum number of courses an instructor can teach
    }

    public void assignCourse(Course course) {
        if (canTeachAnotherCourse()) {
            assignedCourses.add(course);
        } else {
            System.out.println("Instructor " + name + " has reached the maximum number of courses they can teach.");
        }
    }
    public String getAssignedCourseNames() {
        return assignedCourses.stream()
                .map(Course::getCourse)
                .collect(Collectors.joining(", "));
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }
    @Override
    public String toString() {
        return name;
    }

    public void updateAvailability(Course course) {
        String days = course.getDays(); // Example: "MTR"

        TimeRange courseTimeRange = new TimeRange(course.getBeginTime(), course.getEndTime());
        Period coursePeriod = null;
        for (Period period : Period.values()) {
            if (period.getTimeRange().isOverlapping(courseTimeRange)) {
                coursePeriod = period;
                break;
            }
        }
        if (coursePeriod != null) {
            int periodIndex = coursePeriod.ordinal();
            for (char dayChar : days.toCharArray()) {
                int dayIndex;
                switch (dayChar) {
                    case 'M':
                        dayIndex = 0;
                        break;
                    case 'T':
                        dayIndex = 1;
                        break;
                    case 'W':
                        dayIndex = 2;
                        break;
                    case 'R':
                        dayIndex = 3;
                        break;
                    case 'F':
                        dayIndex = 4;
                        break;
                    case 'S':
                        dayIndex = 5; // 'S' corresponds to Saturday
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown day character: " + dayChar);
                }
                if (dayIndex >= 0 && dayIndex <= 4) {
                    // Update availability for Monday to Friday
                    mon_friday[dayIndex][periodIndex] = false;
                } else if (dayIndex == 5) {
                    // Update availability for Saturday
                    setSaturday(false);
                } else {
                    throw new IllegalArgumentException("Invalid day character: " + dayChar);
                }
            }
        }
    }


}