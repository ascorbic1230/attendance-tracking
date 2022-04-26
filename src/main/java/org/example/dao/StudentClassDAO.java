package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.entity.Class;
import org.example.entity.Student;
import org.example.entity.StudentClass;
import org.example.utils.AppUtil;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudentClassDAO {
    public static void addStudentIntoClass(String studentId, int classId) {
        if (canStudentJoinClass(studentId, classId)) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                session.getTransaction().begin();

                Student student = StudentDAO.getStudentById(studentId);
                Class aClass = ClassDAO.getClassById(classId);

                StudentClass sc = new StudentClass(student, aClass);
                Date startDate = aClass.getStartDate();
                session.saveOrUpdate(sc);

                session.getTransaction().commit();

                AttendanceTrackingDAO.addAttendanceTracking(sc, startDate);
            }
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

    public static ArrayList<StudentClass> getAllStudentsInClass(int classId) {
        ArrayList<StudentClass> res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<StudentClass> query = builder.createQuery(StudentClass.class);
            Root<StudentClass> root = query.from(StudentClass.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("aClass").get("id").as(Integer.class), classId);
            query.where(p1);

            res = new ArrayList<>(session.createQuery(query).getResultList());
        }
        return res;
    }

    public static ArrayList<StudentClass> getAllClassesOfStudent(String student_id) {
        ArrayList<StudentClass> res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<StudentClass> query = builder.createQuery(StudentClass.class);
            Root<StudentClass> root = query.from(StudentClass.class);
            query.select(root);

            Predicate p1 = builder.equal(root.get("student").get("id").as(String.class), student_id);
            query.where(p1);

            res = new ArrayList<>(session.createQuery(query).getResultList());
        }
        return res;
    }

    public static ArrayList<StudentClass> getAllActiveClassesOfStudent(String student_id) {
        ArrayList<StudentClass> res = new ArrayList<>();
        ArrayList<StudentClass> temp = getAllClassesOfStudent(student_id);
        Date date = new Date();
        for (StudentClass sc : temp) {
            if (sc.getaClass().getStartDate().compareTo(date) <= 0 && sc.getaClass().getEndDate().compareTo(date) >= 0 )
                res.add(sc);
        }
        return res;
    }

    public static StudentClass getOnGoingClass(String student_id) {
        ArrayList<StudentClass> classes = getAllActiveClassesOfStudent(student_id);
        Calendar calendar = Calendar.getInstance();
        Date now = AppUtil.getNow();
        calendar.setTime(now);
        String weekdayNow = AppUtil.getDayOfWeekString(calendar.get(Calendar.DAY_OF_WEEK));
        LocalTime timeNow = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        for (StudentClass aClass : classes) {
            if (weekdayNow != null && weekdayNow.equals(aClass.getaClass().getWeekdayToString())
                    && timeNow.compareTo(aClass.getaClass().getStartTime()) >= 0 && timeNow.compareTo(aClass.getaClass().getEndTime()) <= 0) {
                return aClass;
            }
        }
        return null;
    }

    public static boolean isStudentInClass(String student_id, int class_id) {
        StudentClass st = getStudentClass(student_id, class_id);
        return st != null;
    }

    public static boolean canStudentJoinClass(String student_id, int class_id) {
        if (isStudentInClass(student_id, class_id))
            return false;
        else {
            ArrayList<StudentClass> classes = getAllActiveClassesOfStudent(student_id);
            Class aClass = ClassDAO.getClassById(class_id);
            for (StudentClass sc : classes) {
                if (sc.getaClass().getWeekday() == aClass.getWeekday()
                        && AppUtil.isSameTime(sc.getaClass().getStartTime(), sc.getaClass().getEndTime(), aClass.getStartTime(), aClass.getEndTime())) {
                    return false;
                }
            }
        }
        return true;
    }
}
