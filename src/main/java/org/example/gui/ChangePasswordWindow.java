package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChangePasswordWindow extends JFrame {
    private final JPanel panel;

    public ChangePasswordWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 500);

        // Container
        JPanel container = new JPanel(new GridLayout(4, 1));
        this.setContentPane(container);

        // Title
        JLabel title = new JLabel("Đổi mật khẩu");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBorder(new EmptyBorder(40, 0,40,0)); // Margin trên dưới
        title.setHorizontalAlignment(JLabel.CENTER);
        container.add(title);

        // Vùng nhập dữ liệu
        JPanel inputField = new JPanel(new GridBagLayout());
        inputField.setBorder(new EmptyBorder(0, 150, 0, 150)); // Margin trái phải
        container.add(inputField);
        GridBagConstraints gbc = new GridBagConstraints();
        // Old Password Label
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 5,20);
        JLabel oldPassLabel = new JLabel("Mật khẩu hiện tại");
        oldPassLabel.setFont(GuiUtil.defaultFont);
        inputField.add(oldPassLabel, gbc);
        // New Password Label
        gbc.gridy++;
        JLabel newPassLabel = new JLabel("Mật khẩu mới");
        newPassLabel.setFont(GuiUtil.defaultFont);
        inputField.add(newPassLabel, gbc);
        // New Password Confirm Label
        gbc.gridy++;
        JLabel newPassConfirmLabel = new JLabel("Nhập lại mật khẩu");
        newPassConfirmLabel.setFont(GuiUtil.defaultFont);
        inputField.add(newPassConfirmLabel, gbc);
        // Old Password text field
        gbc.gridx++;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        JPasswordField oldPassTxt = new JPasswordField();
        oldPassTxt.setFont(GuiUtil.defaultFont);
        inputField.add(oldPassTxt, gbc);
        // New Password text field
        gbc.gridy++;
        JPasswordField newPassTxt = new JPasswordField();
        newPassTxt.setFont(GuiUtil.defaultFont);
        inputField.add(newPassTxt, gbc);
        // New Password Confirm text field
        gbc.gridy++;
        JPasswordField newPassConfirmTxt = new JPasswordField();
        newPassConfirmTxt.setFont(GuiUtil.defaultFont);
        inputField.add(newPassConfirmTxt, gbc);

        // Submit button
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        JButton submitBtn = new JButton("Xác nhận");
        submitBtn.setFont(GuiUtil.defaultFont);
        submitBtn.setMargin(new Insets(8,16,8,16));
        panel.add(submitBtn);
        container.add(panel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addReturnButton() {
        // Return button
        JButton returnBtn = new JButton("Trở lại");
        returnBtn.setFont(GuiUtil.defaultFont);
        returnBtn.setMargin(new Insets(8,16,8,16));
        panel.add(returnBtn);
    }
}
