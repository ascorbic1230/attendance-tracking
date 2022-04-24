package org.example.gui;

import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AddSubjectWindow extends JFrame {

    private final String[] fakeColumns = {"STT", "Mã môn học", "Tên môn học"};
    private final String[][] fakeData = {
            {"1", "CSC15001", "An ninh máy tính"},
            {"2", "CSC13102", "Lập trình ứng dụng Java"},
            {"3", "CSC13010", "Thiết kế phần mềm"},
            {"4", "CSC10108", "Trực quan hoá dữ liệu"},
            {"5", "CSC15001", "An ninh máy tính"},
            {"6", "CSC13102", "Lập trình ứng dụng Java"},
            {"7", "CSC13010", "Thiết kế phần mềm"},
            {"8", "CSC10108", "Trực quan hoá dữ liệu"},
            {"9", "CSC15001", "An ninh máy tính"},
            {"10", "CSC13102", "Lập trình ứng dụng Java"},
            {"11", "CSC13010", "Thiết kế phần mềm"},
            {"12", "CSC10108", "Trực quan hoá dữ liệu"}
    };

    public AddSubjectWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 550);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout(0, 20));
        this.setContentPane(container);

        // Add Subject Panel
        JPanel panel1 = new JPanel(new GridLayout(2, 1));
        panel1.setBorder(new TitledBorder("Thêm môn học"));
        panel1.setSize(new Dimension(100, 100));
        ((TitledBorder) panel1.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        container.add(panel1, BorderLayout.NORTH);

        // Input Group
        JPanel inputGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        inputGroup.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel1.add(inputGroup);

        // Subject id label
        JLabel subjectIdLabel = new JLabel("Mã môn học");
        subjectIdLabel.setFont(GuiUtil.defaultFont);
        inputGroup.add(subjectIdLabel);

        // Subject id input field
        JTextField subjectIdTxt = new JTextField();
        subjectIdTxt.setFont(GuiUtil.defaultFont);
        subjectIdTxt.setColumns(10);
        inputGroup.add(subjectIdTxt);

        // Subject name Label
        JLabel subjectNameLabel = new JLabel("Tên môn học");
        subjectNameLabel.setFont(GuiUtil.defaultFont);
        inputGroup.add(subjectNameLabel);

        // Subject Name input field
        JTextField subjectNameTxt = new JTextField();
        subjectNameTxt.setFont(GuiUtil.defaultFont);
        subjectNameTxt.setColumns(20);
        inputGroup.add(subjectNameTxt);

        // Button Group
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel1.add(buttonGroup);

        // Add button
        JButton addBtn = new JButton("Thêm");
        addBtn.setFont(GuiUtil.defaultFont);
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.setFocusable(false);
        buttonGroup.add(addBtn);

        // Update button
        JButton updateBtn = new JButton("Sửa");
        updateBtn.setFont(GuiUtil.defaultFont);
        updateBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.setFocusable(false);
        buttonGroup.add(updateBtn);

        // Delete button
        JButton deleteBtn = new JButton("Xoá");
        deleteBtn.setFont(GuiUtil.defaultFont);
        deleteBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.setFocusable(false);
        buttonGroup.add(deleteBtn);

        // Clear button
        JButton clearBtn = new JButton("Nhập lại");
        clearBtn.setFont(GuiUtil.defaultFont);
        clearBtn.setPreferredSize(new Dimension(100, 35));
        clearBtn.setFocusable(false);
        buttonGroup.add(clearBtn);

        // Table
        JTable table = new JTable(fakeData, fakeColumns);
        table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(GuiUtil.defaultFont);
        table.setRowHeight(35);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(1).setMaxWidth(150);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // Table attach to ScrollPanel
        JScrollPane panel2 = new JScrollPane(table);
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Subject List Panel
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(new TitledBorder("Danh sách môn học"));
        ((TitledBorder) panel3.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        panel3.add(panel2);

        container.add(panel3, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
