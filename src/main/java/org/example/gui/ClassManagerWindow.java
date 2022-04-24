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

public class ClassManagerWindow extends JFrame {

    private final String[] fakeData = {
            "CSC15001 - An ninh máy tính - Thứ tư - (7:30 - 11:30)",
            "CSC13102 - Lập trình ứng dụng Java - Thứ năm - (12:30 - 17:00)"
    };

    private final String[] fakeColumn = {"STT", "Họ tên", "MSSV", "<html><center>W1<br>24/02/22", "<html><center>W2<br>31/03/22", "W3", "W4", "W5", "W6", "W7", "W8", "W9", "W10", "W11", "W12", "W13", "W14", "W15"};
    private final Object[][] fakeDatas = {
            {"1", "Nguyễn Quốc Toàn", "19120141", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"2", "Nguyễn Văn A","19120142", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"3", "Nguyễn Văn B","19120143", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"4", "Nguyễn Quốc Toàn", "19120141", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"5", "Nguyễn Văn A","19120142", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"6", "Nguyễn Văn B","19120143", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"7", "Nguyễn Quốc Toàn", "19120141", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"8", "Nguyễn Văn A","19120142", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"9", "Nguyễn Văn B","19120143", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"10", "Nguyễn Quốc Toàn", "19120141", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"11", "Nguyễn Văn A","19120142", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
            {"12", "Nguyễn Văn B","19120143", false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
    };

    public ClassManagerWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 650);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new TitledBorder("Quản lý lớp học"));
        ((TitledBorder) container.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        this.setContentPane(container);

        // Row 1 panel
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.setBorder(new EmptyBorder(16,8,16,0));
        container.add(row1, BorderLayout.NORTH);

        // Class selector Label
        JLabel selectClassLabel = new JLabel("Lớp học");
        selectClassLabel.setFont(GuiUtil.defaultFont);
        row1.add(selectClassLabel);

        // Class selector ComboBox
        JComboBox<String> selectClassCb = new JComboBox<>(fakeData);
        selectClassCb.setFont(GuiUtil.defaultFont);
        selectClassCb.setFocusable(false);
        row1.add(selectClassCb);

        // Table
        DefaultTableModel tableModel = new DefaultTableModel(fakeDatas, fakeColumn);
        JTable table = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0, 1, 2 -> { return String.class; }
                    default -> { return Boolean.class; }
                }
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
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(1).setMaxWidth(250);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        // Center alignment cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        // Right alignment column 2
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);

        // Table attach to ScrollPanel
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Student List Panel
        JPanel row2 = new JPanel(new BorderLayout());
        row2.setBorder(new TitledBorder("Danh sách sinh viên"));
        ((TitledBorder) row2.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        row2.add(scrollPane, BorderLayout.CENTER);

        // Button group
        JPanel btnGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        row2.add(btnGroup, BorderLayout.SOUTH);

        // Add student button
        JButton addStudentBtn = new JButton("Thêm sinh viên");
        addStudentBtn.setFont(GuiUtil.defaultFont);
        addStudentBtn.setPreferredSize(new Dimension(150, 50));
        addStudentBtn.setFocusable(false);
        btnGroup.add(addStudentBtn);

        container.add(row2, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
