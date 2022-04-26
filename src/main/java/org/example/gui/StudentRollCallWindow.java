package org.example.gui;

import org.example.dao.AttendanceTrackingDAO;
import org.example.dao.StudentClassDAO;
import org.example.entity.AttendanceTracking;
import org.example.entity.StudentClass;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class StudentRollCallWindow extends JFrame implements ActionListener {
    private final JButton backBtn;
    private final JButton rollCallBtn;

    private AttendanceTracking at = null;

    public StudentRollCallWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new GridLayout(6,1));
        this.setContentPane(container);

        // Get DateTime now
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String now = formatter.format(AppUtil.getNow());

        // Datetime Now Label
        JLabel nowLabel = new JLabel(now);
        nowLabel.setBorder(new TitledBorder("Giờ hiện tại"));
        ((TitledBorder) nowLabel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        nowLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nowLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(nowLabel);

        // Empty panel
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        StudentClass classNow = StudentClassDAO.getOnGoingClass(AppUtil.username);
        String str = "Hiện không có lớp học nào diễn ra";
        System.out.println(classNow);
        if (classNow != null) {
            String subject = classNow.getaClass().getSubject().getId() + " - " + classNow.getaClass().getSubject().getSubjectName();
            String weekday = AppUtil.getDayOfWeekString(classNow.getaClass().getWeekday());
            str = subject + " - " +  weekday + " - " + "(" + classNow.getaClass().getTimeRangeString() + ")";
        }

        // Class open
        JLabel classLabel = new JLabel(str);
        classLabel.setBorder(new TitledBorder("Lớp đang diễn ra"));
        ((TitledBorder) classLabel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        classLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        classLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(classLabel);

        container.add(emptyPanel1);

        // Button Group
        JPanel groupBtn1 = new JPanel(new FlowLayout());
        container.add(groupBtn1);

        // Roll call button
        rollCallBtn = new JButton("Điểm danh");
        rollCallBtn.setFont(GuiUtil.defaultFont);
        rollCallBtn.setPreferredSize(new Dimension(175, 60));
        rollCallBtn.setFocusable(false);
        rollCallBtn.addActionListener(this);
        groupBtn1.add(rollCallBtn);

        // Check roll call
        if (classNow != null) {
            at = AttendanceTrackingDAO.getAttendanceTracking(classNow.getId(), AppUtil.getNow());
            if (at.getAbsent() == null) {
                at.setAbsent(true);
                AttendanceTrackingDAO.changeAbsentStatus(at.getId(), true);
            }
            else if (!at.getAbsent()) {
                rollCallBtn.setText("Đã điểm danh");
                rollCallBtn.setEnabled(false);
            }
        }
        else {
            rollCallBtn.setEnabled(false);
        }

        container.add(emptyPanel2);

        // Button Group
        JPanel groupBtn2 = new JPanel(new FlowLayout());
        container.add(groupBtn2);

        // Back button
        backBtn = new JButton("Trở lại");
        backBtn.setFont(GuiUtil.defaultFont);
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        groupBtn2.add(backBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.dispose();
            MainMenuWindow mainMenuWindow = new MainMenuWindow();
            mainMenuWindow.setStudentView();
        }
        else if (e.getSource() == rollCallBtn) {
            rollCallBtn.setText("Đã điểm danh");
            rollCallBtn.setEnabled(false);
            if (at != null)
                AttendanceTrackingDAO.changeAbsentStatus(at.getId(), false);
        }
    }
}
