package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Subject;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubjectDAO {
    public static Set<Subject> getAllSubjects() {
        Set<Subject> res = new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
            Root<Subject> root = query.from(Subject.class);
            query.select(root);

            List<Subject> subjects = session.createQuery(query).getResultList();

            res.addAll(subjects);
        }
        return res;
    }

    public static Subject getSubjectById(String id) {
        Subject res =  null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
            Root<Subject> root = query.from(Subject.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("id").as(String.class), id);
            query.where(p1);

            List<Subject> subjects = session.createQuery(query).getResultList();

            if (subjects.size() == 1)
                res = subjects.get(0);
        }
        return res;
    }

    public static Set<Subject> getSubjectsByName(String name) {
        Set<Subject> res =  new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Subject> query = builder.createQuery(Subject.class);
            Root<Subject> root = query.from(Subject.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("subjectName").as(String.class), name);
            query.where(p1);

            List<Subject> subjects = session.createQuery(query).getResultList();

            res.addAll(subjects);
        }
        return res;
    }

    public static void createSubject(String id, String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            Subject subject = new Subject(id, name);

            session.persist(subject);

            session.getTransaction().commit();
        }
    }
}
