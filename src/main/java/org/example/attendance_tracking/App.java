package org.example.attendance_tracking;

import org.example.entity.Subject;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class App {
    public static void main(String[] args) {
        Subject newSubject = new Subject();
        newSubject.setSubjectId("MTH001");
        newSubject.setSubjectName("ABC");

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();

        Transaction ts = session.beginTransaction();

        session.save(newSubject);

        ts.commit();
    }
}
