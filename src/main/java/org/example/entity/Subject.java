package org.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Subject implements Serializable {
    @Id
    @Column(name = "subject_id")
    private String id;
    @Column(name = "subject_name")
    private String subjectName;

    @OneToMany(mappedBy = "subject")
    private Set<Class> classes;

    public Subject() {
    }

    public Subject(String id, String subjectName) {
        this.id = id;
        this.subjectName = subjectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
