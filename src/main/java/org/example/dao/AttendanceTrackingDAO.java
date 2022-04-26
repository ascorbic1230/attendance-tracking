package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.AttendanceTracking;
import org.example.entity.StudentClass;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.*;

public class AttendanceTrackingDAO {
    private static final int NUM_WEEKS = 15;

    // Thêm vào 15 tuần tương ứng
    public static void addAttendanceTracking(StudentClass sc, Date startDate) {
        Calendar calendar = Calendar.getInstance();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            for (int i = 0; i < NUM_WEEKS; i++) {
                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 7 * i);
                Date d = calendar.getTime();
                AttendanceTracking at = new AttendanceTracking(i + 1, d, null, sc);
                session.getTransaction().begin();
                session.saveOrUpdate(at);
                session.getTransaction().commit();
            }
        }
    }

    public static Set<AttendanceTracking> getAllStudentsInClass(int classId) {
        Set<AttendanceTracking> res = new HashSet<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceTracking> query = builder.createQuery(AttendanceTracking.class);
            Root<AttendanceTracking> root = query.from(AttendanceTracking.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("studentClass").get("aClass").get("id").as(Integer.class), classId);
            query.where(p1);

            List<AttendanceTracking> ats = session.createQuery(query).getResultList();

            res.addAll(ats);
        }
        return res;
    }

    public static AttendanceTracking getAttendanceTracking(int attendanceTrackingId) {
        AttendanceTracking at = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceTracking> query = builder.createQuery(AttendanceTracking.class);
            Root<AttendanceTracking> root = query.from(AttendanceTracking.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("id").as(Integer.class), attendanceTrackingId);
            query.where(p1);

            List<AttendanceTracking> ats = session.createQuery(query).getResultList();

            if (ats.size() == 1)
                at = ats.get(0);
        }
        return at;
    }

    public static AttendanceTracking getAttendanceTracking(int studentClassId, int week) {
        AttendanceTracking at = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceTracking> query = builder.createQuery(AttendanceTracking.class);
            Root<AttendanceTracking> root = query.from(AttendanceTracking.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId);
            Predicate p2 = builder.equal(root.get("week").as(Integer.class), week);
            query = query.where(builder.and(p1,p2));

            List<AttendanceTracking> ats = session.createQuery(query).getResultList();

            if (ats.size() == 1)
                at = ats.get(0);
        }
        return at;
    }

    public static AttendanceTracking getAttendanceTracking(int studentClassId, Date date) {
        AttendanceTracking at = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        date = calendar.getTime();
        System.out.println(date);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<AttendanceTracking> query = builder.createQuery(AttendanceTracking.class);
            Root<AttendanceTracking> root = query.from(AttendanceTracking.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId);
            Predicate p2 = builder.equal(root.get("date").as(Date.class), date);
            query = query.where(builder.and(p1,p2));

            List<AttendanceTracking> ats = session.createQuery(query).getResultList();

            if (ats.size() == 1)
                at = ats.get(0);
        }
        return at;
    }

    // Thay đổi trạng thái điểm danh của học sinh thông qua studentId, classId và thứ tự tuần
    public static void changeAbsentStatus(String studentId, int classId, int week, Boolean isAbsent) {
        StudentClass sc = StudentClassDAO.getStudentClass(studentId, classId);
        if (sc == null)
            return;
        AttendanceTracking at = getAttendanceTracking(sc.getId(), week);
        if (at == null)
            return;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            session.evict(at);
            at.setAbsent(isAbsent);
            session.update(at);

            session.getTransaction().commit();
        }
    }

    // Thay đổi trạng thái điểm danh của học sinh thông qua studentId, classId và ngày học
    public static void changeAbsentStatus(String studentId, int classId, Date date, Boolean isAbsent) {
        StudentClass sc = StudentClassDAO.getStudentClass(studentId, classId);
        if (sc == null)
            return;
        AttendanceTracking at = getAttendanceTracking(sc.getId(), date);
        if (at == null)
            return;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            session.evict(at);
            at.setAbsent(isAbsent);
            session.update(at);

            session.getTransaction().commit();
        }
    }

    public static void changeAbsentStatus(int attendanceTrackingId,  Boolean isAbsent) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();

            AttendanceTracking at = getAttendanceTracking(attendanceTrackingId);
            session.evict(at);
            at.setAbsent(isAbsent);
            session.update(at);

            session.getTransaction().commit();
        }
    }
}
