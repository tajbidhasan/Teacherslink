package com.example.teacherslink;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Instructor {
    private Ranks rank;
    String ID_no;
    String Home_campus;
    String Business_number;
    String name;
    String address;
    String City_state_zip;
    String Home_phone;
    String college_date;
    List<String> courses = new ArrayList<>();  // changed from String to ArrayList<String>
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

    public String getBusiness_number() {
        return Business_number;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_state_zip() {
        return City_state_zip;
    }

    public void setCity_state_zip(String city_state_zip) {
        City_state_zip = city_state_zip;
    }

    public String getHome_phone() {
        return Home_phone;
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
    public List<String> getCourses() {
        return courses;
    }
    public String getCoursesAsString() {
        return String.join(", ", courses); // Assumes courses is the ArrayList<String> field in the Instructor class
    }
    public void setCourse(String coursesString) {
        String[] coursesArray = coursesString.split(" ");
        for(String course : coursesArray) {
            this.courses.add(course.trim());
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









    public String getFall_workload() {
        return fall_workload;
    }

    public void setFall_workload(String fall_workload) {
        this.fall_workload = fall_workload;
    }


    public boolean courseExists(String courseToCheck) {
        return courses.contains(courseToCheck);
    }

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
    public boolean isAvailableDuring(LocalTime startTime, LocalTime endTime) {
        if (this.availableFrom == null || this.availableTo == null) {
            return false; // Not available if times are not set.
        }

        TimeRange courseTimeRange = new TimeRange(startTime, endTime);

        // Check if the instructor's availability overlaps with the course time range
        boolean isTimeOverlap = courseTimeRange.isOverlapping(new TimeRange(this.availableFrom.getStart(), this.availableTo.getEnd()));

        // If the time doesn’t overlap, then it’s not necessary to check the days availability
        if (!isTimeOverlap) return false;

        // Check the instructor’s availability based on the days and times.
        int startIndex = startTime.getHour();
        int endIndex = endTime.getHour();

        for (int i = 0; i < 5; i++) { // Check for Mon-Fri
            boolean isAvailable = true;
            for (int j = startIndex; j < endIndex; j++) {
                if (!mon_friday[i][j]) { // Not available in this hour on this day
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                return true;
            }
        }

        // Check availability for the weekend days
        if ((saturday && startIndex >= 0 && endIndex <= 24) ||
                (sunday && startIndex >= 0 && endIndex <= 24)) {
            return true;
        }

        return false; // Not available during this time range on any day
    }
    public boolean isAvailableToTeach(Course course) {
        if("ONLINE".equals(course.getDays()) && Online){
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

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }
    @Override
    public String toString() {
        return name;
    }


}