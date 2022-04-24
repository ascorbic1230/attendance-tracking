package org.example.gui;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Properties;

public class AddClassWindow extends JFrame {

    private final String[] fakeData = {
            "CSC15001 - An ninh máy tính",
            "CSC13102 - Lập trình ứng dụng Java",
            "CSC13010 - Thiết kế phần mềm",
            "CSC10108 - Trực quan hoá dữ liệu"
    };

    private final String[] fakeColumn = {"STT", "Môn học", "Thứ", "Thời gian", "Phòng", "Ngày bắt đầu - Ngày kết thúc"};
    private final String[][] fakeDatas = {
            {"1", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"2", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"},
            {"3", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"4", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"},
            {"5", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"6", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"},
            {"7", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"8", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"},
            {"9", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"10", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"},
            {"11", "CSC15001 - An ninh máy tính", "Thứ tư", "7:30 - 11:30", "E102", "10/04/2022 - 11/05/2022"},
            {"12", "CSC13102 - Lập trình ứng dụng Java", "Thứ năm", "12:30 - 17:00", "E102", "08/02/2022 - 20/11/2022"}
    };

    public AddClassWindow() {
        super(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 650);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout(0, 20));
        this.setContentPane(container);

        // Add Class Panel
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1.setBorder(new TitledBorder("Thêm lớp học"));
        panel1.setSize(new Dimension(100, 100));
        ((TitledBorder) panel1.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 20));
        container.add(panel1, BorderLayout.NORTH);

        // Input Group
        JPanel inputGroup = new JPanel(new GridLayout(3,1));
        inputGroup.setBorder(new EmptyBorder(20, 0, 20, 0));
        panel1.add(inputGroup);

        // Row 1 input group
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        inputGroup.add(row1);

        // Choose Subject label
        JLabel selectSubjectLabel = new JLabel("Môn học");
        selectSubjectLabel.setFont(GuiUtil.defaultFont);
        row1.add(selectSubjectLabel);

        // Choose Subject ComboBox
        JComboBox<String> selectSubjectCb = new JComboBox<>(fakeData);
        selectSubjectCb.setFont(GuiUtil.defaultFont);
        selectSubjectCb.setFocusable(false);
        row1.add(selectSubjectCb);

        // Start date label
        JLabel startDateLabel = new JLabel("Ngày bắt đầu");
        startDateLabel.setFont(GuiUtil.defaultFont);
        row1.add(startDateLabel);

        // JDatePicker set up
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        // Start date select
        UtilDateModel model1 = new UtilDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(model1, p);
        JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        startDatePicker.getJFormattedTextField().setFont(GuiUtil.defaultFont);
        row1.add(startDatePicker);

        // End date label
        JLabel endDateLabel = new JLabel("Ngày kết thúc");
        endDateLabel.setFont(GuiUtil.defaultFont);
        row1.add(endDateLabel);

        // End date select
        UtilDateModel model2 = new UtilDateModel();
        JDatePanelImpl endDatePanel = new JDatePanelImpl(model2, p);
        JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateLabelFormatter());
        endDatePicker.getJFormattedTextField().setFont(GuiUtil.defaultFont);
        row1.add(endDatePicker);

        // Row 2 input group
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        inputGroup.add(row2);

        // Weekday Label
        JLabel weekdayLabel = new JLabel("Thứ trong tuần");
        weekdayLabel.setFont(GuiUtil.defaultFont);
        row2.add(weekdayLabel);

        // Weekday input field
        JTextField weekdayTxt = new JTextField();
        weekdayTxt.setFont(GuiUtil.defaultFont);
        weekdayTxt.setColumns(7);
        weekdayTxt.setText("Thứ tư");
        weekdayTxt.setEnabled(false);
        row2.add(weekdayTxt);

        // Start time Label
        JLabel startTimeLabel = new JLabel("Giờ bắt đầu");
        startTimeLabel.setFont(GuiUtil.defaultFont);
        row2.add(startTimeLabel);

        // Time picker set up
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.initialTime = LocalTime.now();
        timeSettings.generatePotentialMenuTimes(TimePickerSettings.TimeIncrement.FifteenMinutes, LocalTime.of(0, 0), LocalTime.of(23, 59));
        timeSettings.use24HourClockFormat();
        timeSettings.fontValidTime = GuiUtil.defaultFont;
        timeSettings.fontInvalidTime = new Font("Arial", Font.PLAIN, 16);

        // Start time input field
        TimePicker startTimePicker = new TimePicker(timeSettings);
        row2.add(startTimePicker);

        // End time Label
        JLabel endTimeLabel = new JLabel("Giờ kết thúc");
        endTimeLabel.setFont(GuiUtil.defaultFont);
        row2.add(endTimeLabel);

        // End time input field
        TimePicker endTimePicker = new TimePicker(timeSettings);
        row2.add(endTimePicker);

        // Room label
        JLabel roomLabel = new JLabel("Phòng");
        roomLabel.setFont(GuiUtil.defaultFont);
        row2.add(roomLabel);

        // Room input field
        JTextField roomTxt = new JTextField();
        roomTxt.setFont(GuiUtil.defaultFont);
        roomTxt.setColumns(5);
        row2.add(roomTxt);

        // Row 3 input group
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        inputGroup.add(row3);

        // Add button
        JButton addBtn = new JButton("Thêm");
        addBtn.setFont(GuiUtil.defaultFont);
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.setFocusable(false);
        row3.add(addBtn);

        // Update button
        JButton updateBtn = new JButton("Sửa");
        updateBtn.setFont(GuiUtil.defaultFont);
        updateBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.setFocusable(false);
        row3.add(updateBtn);

        // Delete button
        JButton deleteBtn = new JButton("Xoá");
        deleteBtn.setFont(GuiUtil.defaultFont);
        deleteBtn.setPreferredSize(new Dimension(100, 35));
        deleteBtn.setFocusable(false);
        row3.add(deleteBtn);

        // Table
        JTable table = new JTable(fakeDatas, fakeColumn);
        table.getTableHeader().setPreferredSize(new Dimension(0, 50));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(GuiUtil.defaultFont);
        table.setRowHeight(35);
        // Set column width
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(2).setMaxWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(3).setMaxWidth(150);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(4).setMaxWidth(100);
        columnModel.getColumn(5).setPreferredWidth(350);
        columnModel.getColumn(5).setMaxWidth(350);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        // Center alignment cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        // Right alignment column 2
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        columnModel.getColumn(1).setCellRenderer(leftRenderer);

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

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
