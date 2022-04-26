package org.example.gui;

import org.example.dao.AccountDAO;
import org.example.entity.Account;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginWindow extends JFrame implements ActionListener, KeyListener {
    private final JButton submitBtn;
    private final JTextField usernameTxt;
    private final JPasswordField passwordTxt;

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
        usernameTxt = new JTextField();
        usernameTxt.setFont(GuiUtil.defaultFont);
        inputField.add(usernameTxt, gbc);
        // Password text field
        gbc.gridy++;
        passwordTxt = new JPasswordField();
        passwordTxt.setFont(GuiUtil.defaultFont);
        passwordTxt.addKeyListener(this);
        inputField.add(passwordTxt, gbc);

        // Submit button
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        submitBtn = new JButton("Đăng nhập");
        submitBtn.setFont(GuiUtil.defaultFont);
        submitBtn.setMargin(new Insets(8,16,8,16));
        submitBtn.addActionListener(this);
        panel.add(submitBtn);
        container.add(panel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void Login() {
        String username = usernameTxt.getText();
        String password = String.valueOf(passwordTxt.getPassword());
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Không được bỏ trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        else if (!AccountDAO.isAccountExists(username)) {
            JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        else if (AccountDAO.checkPassword(username, password)) {
            JOptionPane.showMessageDialog(null, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            AppUtil.username = username;
            this.dispose();
            Account acc = AccountDAO.getAccountByUsername(username);
            if (!acc.isLogin() && acc.isStudent()) {
                new ChangePasswordWindow();
            }
            else {
                MainMenuWindow mainMenuWindow = new MainMenuWindow();
                if (acc.isStudent()) {
                    mainMenuWindow.setStudentView();
                }
                else {
                    mainMenuWindow.setTeacherView();
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Mật khẩu không đúng", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Press login button
        if (e.getSource() == submitBtn) {
            Login();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Press enter
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Login();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
