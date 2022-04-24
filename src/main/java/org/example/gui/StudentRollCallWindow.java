package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import java.awt.*;

public class StudentRollCallWindow extends JFrame {

    public StudentRollCallWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout());
        this.setContentPane(container);

        // Header panel
        JPanel headerPanel = new JPanel();
        container.add(headerPanel, BorderLayout.NORTH);

        // Datetime Now Label
        JLabel nowLabel = new JLabel("Ngày giờ hiện tại: 24/04/2022 14h17'");
        nowLabel.setFont(GuiUtil.defaultFont);
        headerPanel.add(nowLabel);


        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
