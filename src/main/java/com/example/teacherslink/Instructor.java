package com.example.teacherslink;

import java.util.Arrays;

public class Instructor {
    String ID_no;
    String Home_campus;
    String Business_number;
    String name;
    String address;
    String City_state_zip;
    String Home_phone;
    String college_date;
    String course;
    String rank;
    String Online;
    String campus;
    String Second_course;
    String Third_course;
    String sevenToEight_am_classes;
    String AM_classes;
    String threeToFour_pm_classes;
    String PM_classes;

    String late_afternoon_classes;
    String evening_classes;
    String fall_workload;
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

    public String getCollege_date() {
        return college_date;
    }

    public void setCollege_date(String college_date) {
        this.college_date = college_date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getOnline() {
        return Online;
    }

    public void setOnline(String online) {
        Online = online;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getSecond_course() {
        return Second_course;
    }

    public void setSecond_course(String second_course) {
        Second_course = second_course;
    }

    public String getThird_course() {
        return Third_course;
    }

    public void setThird_course(String third_course) {
        Third_course = third_course;
    }

    public String getSevenToEight_am_classes() {
        return sevenToEight_am_classes;
    }

    public void setSevenToEight_am_classes(String sevenToEight_am_classes) {
        this.sevenToEight_am_classes = sevenToEight_am_classes;
    }

    public String getAM_classes() {
        return AM_classes;
    }

    public void setAM_classes(String AM_classes) {
        this.AM_classes = AM_classes;
    }

    public String getThreeToFour_pm_classes() {
        return threeToFour_pm_classes;
    }

    public void setThreeToFour_pm_classes(String threeToFour_pm_classes) {
        this.threeToFour_pm_classes = threeToFour_pm_classes;
    }

    public String getPM_classes() {
        return PM_classes;
    }

    public void setPM_classes(String PM_classes) {
        this.PM_classes = PM_classes;
    }

    public String getLate_afternoon_classes() {
        return late_afternoon_classes;
    }

    public void setLate_afternoon_classes(String late_afternoon_classes) {
        this.late_afternoon_classes = late_afternoon_classes;
    }

    public String getEvening_classes() {
        return evening_classes;
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

/*
    @Override
    public String toString() {
        return "Instructor{" +
                "ID_no='" + ID_no + '\'' +
                ", Home_campus='" + Home_campus + '\'' +
                ", Business_number='" + Business_number + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", City_state_zip='" + City_state_zip + '\'' +
                ", Home_phone='" + Home_phone + '\'' +
                ", college_date='" + college_date + '\'' +
                ", course='" + course + '\'' +
                ", rank='" + rank + '\'' +
                ", Online='" + Online + '\'' +
                ", campus='" + campus + '\'' +
                ", Second_course='" + Second_course + '\'' +
                ", Third_course='" + Third_course + '\'' +
                ", sevenToEight_am_classes='" + sevenToEight_am_classes + '\'' +
                ", AM_classes='" + AM_classes + '\'' +
                ", threeToFour_pm_classes='" + threeToFour_pm_classes + '\'' +
                ", PM_classes='" + PM_classes + '\'' +
                ", late_afternoon_classes='" + late_afternoon_classes + '\'' +
                ", evening_classes='" + evening_classes + '\'' +
                ", fall_workload='" + fall_workload + '\'' +
                ", mon_friday=" + array2DToString(mon_friday) + // Changed this line
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }
*/


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
    @Override
    public String toString() {
        return name + " ID: [" + ID_no + "]";
    }


}