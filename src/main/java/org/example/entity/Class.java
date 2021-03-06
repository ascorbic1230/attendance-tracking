package org.example.entity;

import jakarta.persistence.*;
import org.example.utils.AppUtil;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    private int weekday;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    private String room;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "aClass")
    private Set<StudentClass> studentClass = new HashSet<>();

    public Class() {
    }

    public Class(Date startDate, Date endDate, int weekday, LocalTime startTime, LocalTime endTime, String room, Subject subject) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.subject = subject;
    }

    public Class(Subject subject) {
        this.subject = subject;
    }

    public String getWeekdayToString() {
        return AppUtil.getDayOfWeekString(weekday);
    }

    public String getTimeRangeString() {
        return startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getDateRangeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String startDateStr = formatter.format(startDate);
        String endDateStr = formatter.format(endDate);
        return startDateStr + " - " + endDateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<StudentClass> getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Set<StudentClass> studentClass) {
        this.studentClass = studentClass;
    }
}
