package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginWindow extends JFrame {

    public LoginWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);

        // Container
        JPanel container = new JPanel(new GridLayout(4,1));
        this.setContentPane(container);

        // Tên ứng dụng
        JLabel title = new JLabel("Đăng nhập hệ thống");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(new EmptyBorder(40, 0,40,0)); // Margin trên dưới
        title.setHorizontalAlignment(JLabel.CENTER);
        container.add(title);

        // Vùng nhập username/password
        JPanel inputField = new JPanel(new GridBagLayout());
        inputField.setBorder(new EmptyBorder(0, 150, 0, 150)); // Margin trái phải
        container.add(inputField);
        GridBagConstraints gbc = new GridBagConstraints();
        // Username Label
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 5,20);
        JLabel usernameLabel = new JLabel("Tên đăng nhập");
        usernameLabel.setFont(GuiUtil.defaultFont);
        inputField.add(usernameLabel, gbc);
        // Password Label
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setFont(GuiUtil.defaultFont);
        inputField.add(passwordLabel, gbc);
        // Username text field
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        JTextField usernameTxt = new JTextField();
        usernameTxt.setFont(GuiUtil.defaultFont);
        inputField.add(usernameTxt, gbc);
        // Password text field
        gbc.gridy++;
        JPasswordField passwordTxt = new JPasswordField();
        passwordTxt.setFont(GuiUtil.defaultFont);
        inputField.add(passwordTxt, gbc);

        // Submit button
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        JButton submitBtn = new JButton("Đăng nhập");
        submitBtn.setFont(GuiUtil.defaultFont);
        submitBtn.setMargin(new Insets(8,16,8,16));
        panel.add(submitBtn);
        container.add(panel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
