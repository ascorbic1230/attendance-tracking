package org.example.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "attendance_tracking")
public class AttendanceTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int week;
    private Date date;
    @Column(name = "is_absent")
    private boolean isAbsent;

    @ManyToOne
    @JoinColumn(name = "student_class_id")
    private StudentClass studentClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAbsent() {
        return isAbsent;
    }

    public void setAbsent(boolean absent) {
        isAbsent = absent;
    }

    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }
}
