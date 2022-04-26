package org.example.attendance_tracking;

import org.example.gui.*;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

public class App {
    public static void main(String[] args) {
        // first run hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
        // start application
        new LoginWindow();
    }
}
