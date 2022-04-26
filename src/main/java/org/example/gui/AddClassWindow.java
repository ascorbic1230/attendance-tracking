package org.example.gui;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import org.example.dao.ClassDAO;
import org.example.dao.SubjectDAO;
import org.example.entity.Class;
import org.example.entity.Subject;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class AddClassWindow extends JFrame implements ActionListener, DocumentListener {
    private final JButton addBtn;
    private final JButton backBtn;

    private final JComboBox<String> selectSubjectCb;
    private final JDatePickerImpl startDatePicker;
    private final JTextField endDateTxt;
    private final JTextField weekdayTxt;
    private final TimePicker startTimePicker;
    private final TimePicker endTimePicker;
    private final JTextField roomTxt;

    private final DefaultTableModel tableModel;
    private final JTable table;

    private String[] subjects = { "Rỗng" };
    private final String[] columnsName = {"STT", "Môn học", "Thứ", "Thời gian", "Phòng", "Ngày bắt đầu - Ngày kết thúc"};
    private String[][] data = null;

    public AddClassWindow() {
        // Initial data
        getData();

        // Create GUI
        this.setTitle(AppUtil.getAppNameVn());
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
        selectSubjectCb = new JComboBox<>(subjects);
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
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(model, p);
        startDatePicker = new JDatePickerImpl(startDatePanel, new DateLabelFormatter());
        startDatePicker.getJFormattedTextField().setFont(GuiUtil.defaultFont);
        startDatePicker.getJFormattedTextField().getDocument().addDocumentListener(this);
        row1.add(startDatePicker);

        // End date label
        JLabel endDateLabel = new JLabel("Ngày kết thúc");
        endDateLabel.setFont(GuiUtil.defaultFont);
        row1.add(endDateLabel);

        // End date text field
        endDateTxt = new JTextField();
        endDateTxt.setFont(GuiUtil.defaultFont);
        endDateTxt.setEnabled(false);
        endDateTxt.setColumns(15);
        row1.add(endDateTxt);

        // Row 2 input group
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        inputGroup.add(row2);

        // Weekday Label
        JLabel weekdayLabel = new JLabel("Thứ trong tuần");
        weekdayLabel.setFont(GuiUtil.defaultFont);
        row2.add(weekdayLabel);

        // Weekday input field
        weekdayTxt = new JTextField();
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
        startTimePicker = new TimePicker(timeSettings);
        row2.add(startTimePicker);

        // End time Label
        JLabel endTimeLabel = new JLabel("Giờ kết thúc");
        endTimeLabel.setFont(GuiUtil.defaultFont);
        row2.add(endTimeLabel);

        // End time input field
        endTimePicker = new TimePicker(timeSettings);
        row2.add(endTimePicker);

        // Room label
        JLabel roomLabel = new JLabel("Phòng");
        roomLabel.setFont(GuiUtil.defaultFont);
        row2.add(roomLabel);

        // Room input field
        roomTxt = new JTextField();
        roomTxt.setFont(GuiUtil.defaultFont);
        roomTxt.setColumns(5);
        row2.add(roomTxt);

        // Row 3 input group
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        inputGroup.add(row3);

        // Add button
        addBtn = new JButton("Thêm");
        addBtn.setFont(GuiUtil.defaultFont);
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.setFocusable(false);
        addBtn.addActionListener(this);
        row3.add(addBtn);

        // Table
        if (data == null) {
            tableModel = new DefaultTableModel(1, columnsName.length);
            tableModel.setColumnIdentifiers(columnsName);
        }
        else {
            tableModel = new DefaultTableModel(data, columnsName);
        }
        table = new JTable(tableModel);
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
        ArrayList<Subject> subjectList = new ArrayList<>(SubjectDAO.getAllSubjects());
        if (subjectList.size() != 0) {
            subjects = new String[subjectList.size()];
            for (int i = 0; i < subjectList.size(); i++) {
                String txt = subjectList.get(i).getId() + " - " + subjectList.get(i).getSubjectName();
                subjects[i] = txt;
            }
        }
        ArrayList<Class> classList = new ArrayList<>(ClassDAO.getAllClasses());
        if (classList.size() != 0) {
            data = new String[classList.size()][7];
            for (int i = 0; i < classList.size(); i++) {
                String stt = String.valueOf(i + 1);
                data[i][0] = stt;

                String subject = classList.get(i).getSubject().getId() + " - " + classList.get(i).getSubject().getSubjectName();
                data[i][1] = subject;

                String weekday = classList.get(i).getWeekdayToString();
                data[i][2] = weekday;

                data[i][3] = classList.get(i).getTimeRangeString();

                String room = classList.get(i).getRoom();
                data[i][4] = room;

                data[i][5] = classList.get(i).getDateRangeString();

                data[i][6] = String.valueOf(classList.get(i).getId());
            }
        }
    }

    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (backBtn.equals(source)) {
            this.dispose();
            MainMenuWindow mainMenuWindow = new MainMenuWindow();
            mainMenuWindow.setTeacherView();
        }
        else if (addBtn.equals(source)) {
            try {
                String subject = (String) selectSubjectCb.getSelectedItem();
                String subjectId = subject.split(" - ")[0];

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date startDate = (Date) startDatePicker.getModel().getValue();
                calendar.setTime(startDate);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startDate = calendar.getTime();
                String endDateStr = endDateTxt.getText();
                Date endDate = null;
                if (!endDateStr.equals(""))
                    endDate = formatter.parse(endDateStr);

                String weekdayStr = weekdayTxt.getText();
                int weekday = AppUtil.getDayOfWeekInteger(weekdayStr);

                LocalTime startTime = startTimePicker.getTime();
                LocalTime endTime = endTimePicker.getTime();

                String room = roomTxt.getText();

                if ( endDateStr.equals("") || room.equals("")) {
                    JOptionPane.showMessageDialog(null, "Không được bỏ trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else if (ClassDAO.isClassExists(subjectId, startDate, startTime, endTime, room)){
                    JOptionPane.showMessageDialog(null, "Lớp học đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    ClassDAO.createClass(subjectId, startDate, endDate, weekday, startTime, endTime, room);
                    String startTimeStr = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                    String endTimeStr = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                    String time = startTimeStr + " - " + endTimeStr;

                    String startDateStr = formatter.format(startDate);
                    String date = startDateStr + "  -  " + endDateStr;
                    if (table.getValueAt(0,0) == null) {
                        table.setValueAt(1, 0,0);
                        table.setValueAt(subject,0,1);
                        table.setValueAt(weekdayStr,0,2);
                        table.setValueAt(time,0,3);
                        table.setValueAt(room, 0,4);
                        table.setValueAt(date,0,5);
                    }
                    else {
                        int numRows = table.getRowCount();
                        String stt = String.valueOf(numRows + 1);
                        tableModel.addRow(new Object[] {stt, subject, weekdayStr, time, room, date});
                    }
                }
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = (Date) startDatePicker.getModel().getValue();

        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        c.add(Calendar.DATE, 7 * 14);
        Date endDate = c.getTime();

        endDateTxt.setText(formatter.format(endDate));

        weekdayTxt.setText(AppUtil.getDayOfWeekString(weekday));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
