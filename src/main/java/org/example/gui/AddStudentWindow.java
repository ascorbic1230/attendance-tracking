package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AddStudentWindow extends JFrame {
    private final String[] fakeColumns = {"STT", "MSSV", "Họ tên", "Chọn"};
    private final Object[][] fakeDatas = {
            {"1", "19120141", "Nguyễn Quốc Toàn", false},
            {"2", "19120142", "Nguyễn Văn A", false},
            {"3", "19120143", "Nguyễn Văn B", false},
    };

    public AddStudentWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 550);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new GridLayout(1,2));
        container.setBorder(new TitledBorder("Thêm sinh viên"));
        ((TitledBorder) container.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        this.setContentPane(container);

        // Left panel
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        container.add(leftPanel);

        JPanel row1 = new JPanel(new GridLayout(2,1));
        leftPanel.add(row1);

        // Input group
        JPanel inputGroup = new JPanel(new GridBagLayout());
        inputGroup.setBorder(new EmptyBorder(0,75,0,75));
        row1.add(inputGroup);

        GridBagConstraints gbc1 = new GridBagConstraints();

        // Student id label
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.ipady = 10;
        gbc1.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc1.insets = new Insets(5, 0, 5,20);
        JLabel studentIdLabel = new JLabel("MSSV");
        studentIdLabel.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentIdLabel, gbc1);

        // Student name label
        gbc1.gridy++;
        JLabel studentNameLabel = new JLabel("Họ tên");
        studentNameLabel.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentNameLabel, gbc1);

        // Student id text field
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        gbc1.gridwidth = 2;
        gbc1.fill = GridBagConstraints.HORIZONTAL;
        gbc1.weightx = 1;
        JTextField studentIdTxt = new JTextField();
        studentIdTxt.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentIdTxt, gbc1);

        // Student name text field
        gbc1.gridy++;
        JTextField studentNameField = new JTextField();
        studentNameField.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentNameField, gbc1);

        JPanel panel = new JPanel(new FlowLayout());
        row1.add(panel);

        // Add Student button
        JButton addStudentBtn = new JButton("Thêm");
        addStudentBtn.setFont(GuiUtil.defaultFont);
        addStudentBtn.setPreferredSize(new Dimension(100, 40));
        addStudentBtn.setFocusable(false);
        panel.add(addStudentBtn);

        GridBagConstraints gbc2 = new GridBagConstraints();

        // Button group
        JPanel btnGroup = new JPanel(new GridBagLayout());
        leftPanel.add(btnGroup);

        // Get CSV template
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.insets= new Insets(5,0,5,0);
        JButton getTemplateBtn = new JButton("Lấy CSV template");
        getTemplateBtn.setFont(GuiUtil.defaultFont);
        getTemplateBtn.setPreferredSize(new Dimension(200, 50));
        getTemplateBtn.setFocusable(false);
        btnGroup.add(getTemplateBtn, gbc2);

        // Get CSV template
        gbc2.gridy++;
        JButton importCsvBtn = new JButton("Nhập từ file CSV");
        importCsvBtn.setFont(GuiUtil.defaultFont);
        importCsvBtn.setPreferredSize(new Dimension(200, 50));
        importCsvBtn.setFocusable(false);
        btnGroup.add(importCsvBtn, gbc2);

        // Right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Danh sách sinh viên tạm thời"));
        ((TitledBorder) rightPanel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        container.add(rightPanel);

        // Table
        DefaultTableModel tableModel = new DefaultTableModel(fakeDatas, fakeColumns);
        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(GuiUtil.defaultFont);
        table.setRowHeight(35);
        // Set column width
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(3).setMaxWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        // Center alignment cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        // Right alignment column 3
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        columnModel.getColumn(2).setCellRenderer(leftRenderer);

        // Scroll Panel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBorder(new EmptyBorder(10,0,10,0));
        rightPanel.add(panel2, BorderLayout.SOUTH);

        // Confirm Button
        JButton confirmBtn = new JButton("Xác nhận");
        confirmBtn.setFont(GuiUtil.defaultFont);
        confirmBtn.setPreferredSize(new Dimension(200, 50));
        confirmBtn.setFocusable(false);
        panel2.add(confirmBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
