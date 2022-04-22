package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Class;
import org.example.entity.Student;
import org.example.entity.StudentClass;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public class StudentClassDAO {
    public static void addStudentIntoClass(Student student, Class aClass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            StudentClass sc = new StudentClass(student, aClass);
            Date startDate = aClass.getStartDate();
            AttendanceTrackingDAO.addAttendanceTracking(sc, startDate);
            session.persist(sc);

            session.getTransaction().commit();
        }
    }

    public static StudentClass getStudentClass(String studentId, int classId) {
        StudentClass sc = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<StudentClass> query = builder.createQuery(StudentClass.class);
            Root<StudentClass> root = query.from(StudentClass.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("student").get("id").as(String.class), studentId);
            Predicate p2 = builder.equal(root.get("aClass").get("id").as(Integer.class), classId);
            query = query.where(builder.and(p1, p2));

            List<StudentClass> scs = session.createQuery(query).getResultList();

            if (scs.size() == 1)
                sc = scs.get(0);
        }
        return sc;
    }
}
