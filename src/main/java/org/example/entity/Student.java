package org.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student implements Serializable {
    @Id
    @Column(name = "student_id")
    private String id;
    private String name;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "student")
    private Set<StudentClass> studentClass = new HashSet<>();

    public Student() {
    }

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<StudentClass> getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(Set<StudentClass> studentClass) {
        this.studentClass = studentClass;
    }
}
