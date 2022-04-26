package org.example.gui;

import org.example.dao.StudentClassDAO;
import org.example.dao.StudentDAO;
import org.example.entity.Student;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AddStudentWindow extends JFrame implements ActionListener {
    private final ClassManagerWindow parent;

    private final JButton backBtn;
    private final JButton addStudentBtn;
    private final JButton getTemplateBtn;
    private final JButton importCsvBtn;
    private final JButton confirmBtn;

    private final JTextField studentIdTxt;
    private final JTextField studentNameTxt;

    private final DefaultTableModel tableModel;
    private final JTable table;

    private final String[] columnsName = {"STT", "MSSV", "Họ tên", "Chọn"};
    private String[][] data = null;

    public AddStudentWindow(ClassManagerWindow parent) {
        // create GUI
        this.setTitle(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1000, 550);
        this.setResizable(false);

        this.parent = parent;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "Mọi thay đổi chưa lưu lại sẽ bị mất. Bạn thật sự muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (answer == 0)
                    dispose();
            }
        });

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
        studentIdTxt = new JTextField();
        studentIdTxt.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentIdTxt, gbc1);

        // Student name text field
        gbc1.gridy++;
        studentNameTxt = new JTextField();
        studentNameTxt.setFont(GuiUtil.defaultFont);
        inputGroup.add(studentNameTxt, gbc1);

        JPanel panel = new JPanel(new FlowLayout());
        row1.add(panel);

        // Add Student button
        addStudentBtn = new JButton("Thêm");
        addStudentBtn.setFont(GuiUtil.defaultFont);
        addStudentBtn.setPreferredSize(new Dimension(100, 40));
        addStudentBtn.setFocusable(false);
        addStudentBtn.addActionListener(this);
        panel.add(addStudentBtn);

        GridBagConstraints gbc2 = new GridBagConstraints();

        // Button group
        JPanel btnGroup = new JPanel(new GridBagLayout());
        leftPanel.add(btnGroup);

        // Get CSV template
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.insets= new Insets(5,0,5,0);
        getTemplateBtn = new JButton("Lấy CSV template");
        getTemplateBtn.setFont(GuiUtil.defaultFont);
        getTemplateBtn.setPreferredSize(new Dimension(200, 50));
        getTemplateBtn.setFocusable(false);
        getTemplateBtn.addActionListener(this);
        btnGroup.add(getTemplateBtn, gbc2);

        // Get CSV template
        gbc2.gridy++;
        importCsvBtn = new JButton("Nhập từ file CSV");
        importCsvBtn.setFont(GuiUtil.defaultFont);
        importCsvBtn.setPreferredSize(new Dimension(200, 50));
        importCsvBtn.setFocusable(false);
        importCsvBtn.addActionListener(this);
        btnGroup.add(importCsvBtn, gbc2);

        // back button
        gbc2.gridy++;
        backBtn = new JButton("Trở lại");
        backBtn.setFont(GuiUtil.defaultFont);
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        btnGroup.add(backBtn, gbc2);

        // Right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Danh sách sinh viên tạm thời"));
        ((TitledBorder) rightPanel.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        container.add(rightPanel);

        // Table
        tableModel = new DefaultTableModel(data, columnsName);
        table = new JTable(tableModel) {
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
        confirmBtn = new JButton("Xác nhận");
        confirmBtn.setFont(GuiUtil.defaultFont);
        confirmBtn.setPreferredSize(new Dimension(200, 50));
        confirmBtn.setFocusable(false);
        confirmBtn.addActionListener(this);
        panel2.add(confirmBtn);

        // Initial data
        getData();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void getData() {
        ArrayList<Student> students = new ArrayList<>(StudentDAO.getAllStudents());
        for (Student s : students) {
            addStudentToTable(s.getId(), s.getName());
        }
    }

    private void addStudentToTable(String studentId, String studentName) {
        int numRows = table.getRowCount();
        String stt = String.valueOf(numRows + 1);
        tableModel.addRow(new Object[] {stt, studentId, studentName, false});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            int answer = JOptionPane.showConfirmDialog(null, "Mọi thay đổi chưa lưu lại sẽ bị mất. Bạn thật sự muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (answer == 0)
                this.dispose();
        }
        else if (e.getSource() == addStudentBtn) {
            String studentId = studentIdTxt.getText();
            String studentName = studentNameTxt.getText();
            if (studentId.equals("") || studentName.equals("")) {
                JOptionPane.showMessageDialog(null, "Không được bỏ trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else {
                addStudentToTable(studentId, studentName);
            }
        }
        else if (e.getSource() == getTemplateBtn) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    AppUtil.createCsvTemplate(file.getPath());
                    JOptionPane.showMessageDialog(null, "Lấy thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if (e.getSource() == importCsvBtn) {
            List<String[]> res = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                res = AppUtil.readCsvFile(file.getPath());
                for (String[] s : res) {
                    addStudentToTable(s[0], s[1]);
                }
                JOptionPane.showMessageDialog(null, "Nhập dữ liệu thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if (e.getSource() == confirmBtn) {
            int classIndex = parent.selectClassCb.getSelectedIndex();
            int classId = parent.classList.get(classIndex).getId();
            // Get data from table
            HashMap<String, String> dataFromTable = new HashMap<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ((Boolean) tableModel.getValueAt(i,3)) {
                    String studentId = tableModel.getValueAt(i, 1).toString();
                    String studentName = tableModel.getValueAt(i, 2).toString();
                    dataFromTable.put(studentId, studentName);
                }
            }
            // Create student and add to class
            Set<String> keySet = dataFromTable.keySet();
            for (String studentId : keySet) {
                if (!StudentClassDAO.isStudentInClass(studentId, classId)) {
                    String studentName = dataFromTable.get(studentId);
                    StudentDAO.createStudent(studentId, studentName);
                    StudentClassDAO.addStudentIntoClass(studentId, classId);
                }
            }
            JOptionPane.showMessageDialog(null, "Thao tác thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            parent.updateData(classId);
            parent.updateTable();
        }
    }
}
