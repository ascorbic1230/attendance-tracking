package org.example.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Account;
import org.example.entity.Student;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentDAO {
    public static Set<Student> getAllStudents() {
        Set<Student> res = new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = builder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root);

            List<Student> students = session.createQuery(query).getResultList();

            res.addAll(students);
        }
        return res;
    }

    public static Student getStudentById(String id) {
        Student res =  null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = builder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("id").as(String.class), id);
            query.where(p1);

            List<Student> students = session.createQuery(query).getResultList();

            if (students.size() == 1)
                res = students.get(0);
        }
        return res;
    }

    public static Set<Student> getStudentsByName(String name) {
        Set<Student> res =  new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = builder.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("name").as(String.class), name);
            query.where(p1);

            List<Student> students = session.createQuery(query).getResultList();

            res.addAll(students);
        }
        return res;
    }

    public static boolean isStudentExists(String id) {
        Student student = getStudentById(id);
        return student != null;
    }

    // Tạo student đồng thời sẽ tạo account với username/password đểu là id
    public static void createStudent(String id, String name) {
        if (isStudentExists(id))
            return;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            Student student = new Student(id, name);
            String hashedPassword = BCrypt.withDefaults().hashToString(12, id.toCharArray()); // Hash password
            Account account = new Account(id, hashedPassword, true);
            student.setAccount(account);

            session.persist(student);

            session.getTransaction().commit();
        }
    }
}
