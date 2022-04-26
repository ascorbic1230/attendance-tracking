package org.example.gui;

import org.example.dao.AttendanceTrackingDAO;
import org.example.dao.StudentClassDAO;
import org.example.entity.AttendanceTracking;
import org.example.entity.StudentClass;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RollCallHistoryWindow extends JFrame implements ActionListener {
    private final JButton backBtn;

    private final String[] columnsName = {"STT", "Môn học", "W1", "W2", "W3", "W4", "W5", "W6", "W7", "W8", "W9", "W10", "W11", "W12", "W13", "W14", "W15"};
    private Object[][] data = null;

    public RollCallHistoryWindow() {
        // Initial data
        getData();

        // Create GUI
        this.setTitle(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 650);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new TitledBorder("Lịch sử điểm danh"));
        ((TitledBorder) container.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        this.setContentPane(container);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.CENTER);

        // Table
        DefaultTableModel tableModel = new DefaultTableModel(data, columnsName);
        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0, 1 -> { return String.class; }
                    default -> { return Boolean.class; }
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setPreferredSize(new Dimension(0, 60));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(GuiUtil.defaultFont);
        table.setRowHeight(35);
        // Set column width
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(350);
        columnModel.getColumn(1).setMaxWidth(350);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        // Center alignment cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        // Right alignment column 2
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);

        // Scroll panel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Group Button
        JPanel groupBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,20));
        groupBtn.setPreferredSize(new Dimension(100, 100));
        container.add(groupBtn, BorderLayout.SOUTH);

        // Back button
        backBtn = new JButton("Trở lại");
        backBtn.setFont(GuiUtil.defaultFont);
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        groupBtn.add(backBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void getData() {
        ArrayList<StudentClass> classes = StudentClassDAO.getAllClassesOfStudent(AppUtil.username);
        if (classes.size() != 0) {
           data = new Object[classes.size()][17];
           for (int i = 0; i < classes.size(); i++) {
               String stt = String.valueOf(i + 1);
               data[i][0] = stt;

               String subject = classes.get(i).getaClass().getSubject().getId() + " - " + classes.get(i).getaClass().getSubject().getSubjectName();
               data[i][1] = subject;

               for (int j = 0; j < 15; j++) {
                   AttendanceTracking at = AttendanceTrackingDAO.getAttendanceTracking(classes.get(i).getId(), j + 1);
                   if (at.getAbsent() != null) {
                       data[i][j + 2] = !at.getAbsent();
                   }
                   else {
                       data[i][j + 2] = false;
                   }
               }
           }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.dispose();
            MainMenuWindow mainMenuWindow = new MainMenuWindow();
            mainMenuWindow.setStudentView();
        }
    }
}
