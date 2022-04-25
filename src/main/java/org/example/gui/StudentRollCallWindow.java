package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentRollCallWindow extends JFrame {

    public StudentRollCallWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new GridLayout(6,1));
        this.setContentPane(container);

        // Datetime Now Label
        JLabel nowLabel = new JLabel("24/04/2022 -  14h17'");
        nowLabel.setBorder(new TitledBorder("Giờ hiện tại"));
        ((TitledBorder) nowLabel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        nowLabel.setFont(GuiUtil.defaultFont);
        nowLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(nowLabel);

        // Empty panel
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        // Class open
        JLabel classLabel = new JLabel("CSC13102 - Lập trình ứng dụng Java - Thứ năm - (12:30 - 17:00)");
        classLabel.setBorder(new TitledBorder("Lớp đang diễn ra"));
        ((TitledBorder) classLabel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        classLabel.setFont(GuiUtil.defaultFont);
        classLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(classLabel);

        container.add(emptyPanel1);

        // Button Group
        JPanel groupBtn1 = new JPanel(new FlowLayout());
        container.add(groupBtn1);

        // Roll call button
        JButton rollCallBtn = new JButton("Điểm danh");
        rollCallBtn.setFont(GuiUtil.defaultFont);
        rollCallBtn.setPreferredSize(new Dimension(175, 60));
        rollCallBtn.setFocusable(false);
        groupBtn1.add(rollCallBtn);

        container.add(emptyPanel2);

        // Button Group
        JPanel groupBtn2 = new JPanel(new FlowLayout());
        container.add(groupBtn2);

        // Roll call button
        JButton backBtn = new JButton("Trở lại");
        backBtn.setFont(GuiUtil.defaultFont);
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFocusable(false);
        groupBtn2.add(backBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
