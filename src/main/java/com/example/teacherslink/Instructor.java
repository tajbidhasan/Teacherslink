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
    String sevenToEight_am_classes;
    String AM_classes;
    String threeToFour_pm_classes;
    String PM_classes;

    String late_afternoon_classes;
    String evening_classes;
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

    public String getSevenToEight_am_classes() {
        return sevenToEight_am_classes;
    }

    public void setSevenToEight_am_classes(String sevenToEight_am_classes) {
        this.sevenToEight_am_classes = sevenToEight_am_classes;
    }



    public void setAM_classes(String AM_classes) {
        this.AM_classes = AM_classes;
    }


    public void setThreeToFour_pm_classes(String threeToFour_pm_classes) {
        this.threeToFour_pm_classes = threeToFour_pm_classes;
    }



    public void setPM_classes(String PM_classes) {
        this.PM_classes = PM_classes;
    }



    public void setLate_afternoon_classes(String late_afternoon_classes) {
        this.late_afternoon_classes = late_afternoon_classes;
    }



    public void setEvening_classes(String evening_classes) {
        this.evening_classes = evening_classes;
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


        // Assuming that if an instructor is available on Saturday or Sunday,
        // they are available for the whole day.
        if ((saturday && startIndex >= 0 && endIndex <= 24) ||
                (sunday && startIndex >= 0 && endIndex <= 24)) {
            return true;
        }

        return false; // Not available during this time range on any day
    }

    public TimeRange getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(TimeRange availableFrom) {
        this.availableFrom = availableFrom;
    }

    public TimeRange getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(TimeRange availableTo) {
        this.availableTo = availableTo;
    }

    public boolean isAvailableDuring(TimeRange courseTime) {
        return courseTime.getStart().isAfter(availableFrom.getStart()) && courseTime.getEnd().isBefore(availableTo.getEnd());
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
        return name + " ID: [" + ID_no + "]" ;
    }


}