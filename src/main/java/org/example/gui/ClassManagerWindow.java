package org.example.gui;

import org.example.dao.AttendanceTrackingDAO;
import org.example.dao.ClassDAO;
import org.example.dao.StudentClassDAO;
import org.example.entity.AttendanceTracking;
import org.example.entity.Class;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ClassManagerWindow extends JFrame implements ActionListener, ItemListener {
    private final JButton backBtn;
    protected final JComboBox<String> selectClassCb;
    private final JButton addStudentBtn;

    private String[] classes = { "Rỗng" };
    protected ArrayList<Class> classList = null;
    private final String[] columnsName = {"STT", "Họ tên", "MSSV", "W1", "W2", "W3", "W4", "W5", "W6", "W7", "W8", "W9", "W10", "W11", "W12", "W13", "W14", "W15"};
    private Object[][] data = null;

    private DefaultTableModel tableModel;
    private JTable table;

    public ClassManagerWindow() {
        // Initial data
        getData();

        // create GUI
        this.setTitle(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 700);
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
        selectClassCb = new JComboBox<>(classes);
        selectClassCb.setFont(GuiUtil.defaultFont);
        selectClassCb.setFocusable(false);
        selectClassCb.addItemListener(this);
        row1.add(selectClassCb);

        // Table
        tableModel = new DefaultTableModel(data, columnsName);
        table = new JTable(tableModel) {
            @Override
            public java.lang.Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0, 1, 2 -> { return String.class; }
                    default -> { return Boolean.class; }
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return data[row][column] != null;
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
        tableModel.fireTableDataChanged();

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
        addStudentBtn = new JButton("Thêm sinh viên");
        addStudentBtn.setFont(GuiUtil.defaultFont);
        addStudentBtn.setPreferredSize(new Dimension(150, 50));
        addStudentBtn.setFocusable(false);
        addStudentBtn.addActionListener(this);
        btnGroup.add(addStudentBtn);

        container.add(row2, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel(new FlowLayout());
        container.add(footerPanel, BorderLayout.SOUTH);

        // Back button
        backBtn = new JButton("Trở lại");
        backBtn.setFont(GuiUtil.defaultFont);
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        footerPanel.add(backBtn);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void getData() {
        classList = new ArrayList<>(ClassDAO.getAllClasses());
        if (classList.size() != 0) {
            classes = new String[classList.size()];
            for (int i = 0; i < classList.size(); i++) {
                Class c = classList.get(i);
                String aClass = c.getSubject().getId() + " - " + c.getSubject().getSubjectName() + " - "
                        + AppUtil.getDayOfWeekString(c.getWeekday()) + " - " + c.getTimeRangeString();
                classes[i] = aClass;
            }
        }
        int classId = classList.get(0).getId();
        updateData(classId);
    }

    private String getSubjectIdFromString(String str) {
        String[] parts = str.split(" - ");
        return parts[0];
    }

    protected void updateData(int classId) {
        ArrayList<StudentClass> studentsInClass = StudentClassDAO.getAllStudentsInClass(classId);
        data = new Object[studentsInClass.size()][18];
        for (int i = 0; i < studentsInClass.size(); i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = studentsInClass.get(i).getStudent().getName();
            data[i][2] = studentsInClass.get(i).getStudent().getId();
            for (int j = 0; j < 15; j++) {
                AttendanceTracking at = AttendanceTrackingDAO.getAttendanceTracking(studentsInClass.get(i).getId(), j + 1);
                Date date = at.getDate();
                if (date.compareTo(AppUtil.getNow()) <= 0 && at.getAbsent() == null) {
                    at.setAbsent(true);
                }
                if (at.getAbsent() == null)
                    data[i][j + 3] = at.getAbsent();
                else
                    data[i][j + 3] = !at.getAbsent();
            }
        }
        // Update header
        Class aClass = ClassDAO.getClassById(classId);
        Date startDate = aClass.getStartDate();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 15; i++) {
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i * 7);
            Date date = calendar.getTime();
            updateColumnsName(i + 1, date);
        }
    }

    protected void updateTable() {
        tableModel = new DefaultTableModel(data, columnsName);
        table.setModel(tableModel);
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
        tableModel.fireTableDataChanged();
    }

    private void updateColumnsName(int week, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String strDate = dateFormat.format(date);
        String header = "<html><center>W" + String.valueOf(week) + "<br>" + strDate;
        columnsName[week + 2] = header;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.dispose();
            MainMenuWindow mainMenuWindow = new MainMenuWindow();
            mainMenuWindow.setTeacherView();
        }
        else if (e.getSource() == addStudentBtn) {
            new AddStudentWindow(this);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int selectedIndex = selectClassCb.getSelectedIndex();
            int classId = classList.get(selectedIndex).getId();
            updateData(classId);
            updateTable();
        }
    }
}
