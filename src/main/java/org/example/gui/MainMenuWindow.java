package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenuWindow extends JFrame {
    private final JLabel subTitle;
    private final JPanel mainPanel;
    private final JButton addSubjectBtn;
    private final JButton addClassBtn;
    private final JButton classManagerBtn;
    private final JButton rollCallBtn;
    private final JButton rollCallHistoryBtn;

    public MainMenuWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new GridLayout(3,1));
        this.setContentPane(container);

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        container.add(topPanel);

        // Title
        JLabel title = new JLabel("Phần mềm quản lý điểm danh");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setVerticalAlignment(JLabel.BOTTOM);
        title.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(title);

        // subtitle
        subTitle = new JLabel();
        subTitle.setFont(new Font("Arial", Font.ITALIC, 20));
        subTitle.setVerticalAlignment(JLabel.TOP);
        subTitle.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(subTitle);

        // Main Panel
        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        container.add(mainPanel);

        // Add Subject Button
        ImageIcon icon1 = new ImageIcon("./assets/icons/subject.png");
        addSubjectBtn = new JButton("Thêm môn học");
        addSubjectBtn.setFont(GuiUtil.defaultFont);
        addSubjectBtn.setIcon(icon1);
        addSubjectBtn.setMargin(new Insets(8,16,8,16));

        // Add new class Button
        ImageIcon icon2 = new ImageIcon("./assets/icons/class.png");
        addClassBtn = new JButton("Thêm lớp học");
        addClassBtn.setFont(GuiUtil.defaultFont);
        addClassBtn.setIcon(icon2);
        addClassBtn.setMargin(new Insets(8,16,8,16));


        // Class manager Button
        ImageIcon icon3 = new ImageIcon("./assets/icons/class_manager.png");
        classManagerBtn = new JButton("Quản lý lớp học");
        classManagerBtn.setFont(GuiUtil.defaultFont);
        classManagerBtn.setIcon(icon3);
        classManagerBtn.setMargin(new Insets(8,16,8,16));

        // Roll-call Button
        ImageIcon icon5 = new ImageIcon("./assets/icons/roll_call.png");
        rollCallBtn = new JButton("Điểm danh");
        rollCallBtn.setFont(GuiUtil.defaultFont);
        rollCallBtn.setIcon(icon5);
        rollCallBtn.setMargin(new Insets(8,16,8,16));


        // History Roll-call Button
        ImageIcon icon6 = new ImageIcon("./assets/icons/roll_call_history.png");
        rollCallHistoryBtn = new JButton("Lịch sử điểm danh");
        rollCallHistoryBtn.setFont(GuiUtil.defaultFont);
        rollCallHistoryBtn.setIcon(icon6);
        rollCallHistoryBtn.setMargin(new Insets(8,16,8,16));


        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        container.add(bottomPanel);

        // Logout Button
        ImageIcon icon4 = new ImageIcon("./assets/icons/log_out.png");
        JButton logoutBtn = new JButton("Đăng xuất");
        logoutBtn.setFocusable(false);
        logoutBtn.setFont(GuiUtil.defaultFont);
        logoutBtn.setIcon(icon4);
        logoutBtn.setMargin(new Insets(8,16,8,16));
        bottomPanel.add(logoutBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setStudentView() {
        // subtitle
        subTitle.setText("(Dành cho sinh viên)");

        // Main Panel
        mainPanel.add(rollCallBtn);
        mainPanel.add(rollCallHistoryBtn);
    }

    public void setTeacherView() {
        // subtitle
        subTitle.setText("(Dành cho giáo vụ)");

        // Main panel
        mainPanel.add(addSubjectBtn);
        mainPanel.add(addClassBtn);
        mainPanel.add(classManagerBtn);
    }
}
