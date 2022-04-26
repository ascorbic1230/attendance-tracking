package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Class;
import org.example.entity.Subject;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalTime;
import java.util.*;

public class ClassDAO {
    public static Set<Class> getAllClasses() {
        Set<Class> res = new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Class> query = builder.createQuery(Class.class);
            Root<Class> root = query.from(Class.class);
            query.select(root);
            List<Class> classes = session.createQuery(query).getResultList();

            res.addAll(classes);
        }
        return res;
    }

    public static Class getClassById(int id) {
        Class res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Class> query = builder.createQuery(Class.class);
            Root<Class> root = query.from(Class.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("id").as(Integer.class), id);
            query.where(p1);

            List<Class> classes = session.createQuery(query).getResultList();

            if (classes.size() == 1)
                res = classes.get(0);
        }
        return res;
    }

    public static boolean isClassExists(String subjectId, Date startDate, LocalTime startTime, LocalTime endTime, String room) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Class> query = builder.createQuery(Class.class);
            Root<Class> root = query.from(Class.class);
            query.select(root);

            List<Predicate> p = new ArrayList<>();

            p.add(builder.equal(root.get("subject").get("id").as(String.class), subjectId));
            p.add(builder.equal(root.get("startDate").as(Date.class), startDate));
            p.add(builder.equal(root.get("startTime").as(LocalTime.class), startTime));
            p.add(builder.equal(root.get("endTime").as(LocalTime.class), endTime));
            p.add(builder.equal(root.get("room").as(String.class), room));

            query.where(p.toArray(new Predicate[]{}));

            List<Class> classes = session.createQuery(query).getResultList();

            if (classes.size() != 0)
                return true;
        }
        return false;
    }

    public static void createClass(String subjectId, Date startDate, Date endDate, int weekday, LocalTime startTime, LocalTime endTime, String room) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            Subject subject = SubjectDAO.getSubjectById(subjectId);
            if (subject == null)
                return;
            Class aClass = new Class(startDate, endDate, weekday, startTime, endTime, room, subject);

            session.persist(aClass);

            session.getTransaction().commit();
        }
    }
}
