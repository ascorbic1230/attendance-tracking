package org.example.gui;

import org.example.dao.SubjectDAO;
import org.example.entity.Subject;
import org.example.utils.AppUtil;
import org.example.utils.GuiUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class AddSubjectWindow extends JFrame implements ActionListener, MouseListener {
    private final JButton backBtn;
    private final JButton addBtn;
    private final JButton updateBtn;
    private final JButton clearBtn;

    private final JTextField subjectIdTxt;
    private final JTextField subjectNameTxt;

    private final DefaultTableModel tableModel;
    private final JTable table;

    private final String[] columnsName = {"STT", "Mã môn học", "Tên môn học"};
    private String[][] data = null;

    public AddSubjectWindow() {
        // Initial data
        getData();

        // Create GUI
        this.setTitle(AppUtil.getAppNameVn());
        this.setIconImage(AppUtil.getAppLogo());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 550);
        this.setResizable(false);

        // Container
        JPanel container = new JPanel(new BorderLayout(0, 15));
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
        subjectIdTxt = new JTextField();
        subjectIdTxt.setFont(GuiUtil.defaultFont);
        subjectIdTxt.setColumns(10);
        inputGroup.add(subjectIdTxt);

        // Subject name Label
        JLabel subjectNameLabel = new JLabel("Tên môn học");
        subjectNameLabel.setFont(GuiUtil.defaultFont);
        inputGroup.add(subjectNameLabel);

        // Subject Name input field
        subjectNameTxt = new JTextField();
        subjectNameTxt.setFont(GuiUtil.defaultFont);
        subjectNameTxt.setColumns(20);
        inputGroup.add(subjectNameTxt);

        // Button Group
        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel1.add(buttonGroup);

        // Add button
        addBtn = new JButton("Thêm");
        addBtn.setFont(GuiUtil.defaultFont);
        addBtn.setPreferredSize(new Dimension(100, 35));
        addBtn.setFocusable(false);
        addBtn.addActionListener(this);
        buttonGroup.add(addBtn);

        // Update button
        updateBtn = new JButton("Sửa");
        updateBtn.setFont(GuiUtil.defaultFont);
        updateBtn.setPreferredSize(new Dimension(100, 35));
        updateBtn.setFocusable(false);
        updateBtn.addActionListener(this);
        buttonGroup.add(updateBtn);

        // Clear button
        clearBtn = new JButton("Nhập lại");
        clearBtn.setFont(GuiUtil.defaultFont);
        clearBtn.setPreferredSize(new Dimension(100, 35));
        clearBtn.setFocusable(false);
        clearBtn.addActionListener(this);
        buttonGroup.add(clearBtn);

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
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(1).setMaxWidth(150);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.addMouseListener(this);

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
        List<Subject> subjects = new ArrayList<>(SubjectDAO.getAllSubjects());
        if (subjects.size() != 0) {
            data = new String[subjects.size()][3];
            for (int i = 0; i < subjects.size(); i++) {
                String stt = String.valueOf(i + 1);
                data[i][0] = stt;
                data[i][1] = subjects.get(i).getId();
                data[i][2] = subjects.get(i).getSubjectName();
            }
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
            String subjectId = subjectIdTxt.getText();
            String subjectName = subjectNameTxt.getText();
            if (subjectId.equals("") || subjectName.equals("")) {
                JOptionPane.showMessageDialog(null, "Không được bỏ trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else if (SubjectDAO.isSubjectExists(subjectId)) {
                JOptionPane.showMessageDialog(null, "Môn này đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            else {
                SubjectDAO.createSubject(subjectId, subjectName);
                if (table.getValueAt(0,0) == null) {
                    table.setValueAt(1, 0,0);
                    table.setValueAt(subjectId,0,1);
                    table.setValueAt(subjectName,0,2);
                }
                else {
                    int numRows = table.getRowCount();
                    String stt = String.valueOf(numRows + 1);
                    tableModel.addRow(new Object[] {stt, subjectId, subjectName});
                }
            }
        }
        else if (updateBtn.equals(source)) {
            String subjectId = subjectIdTxt.getText();
            String subjectName = subjectNameTxt.getText();
            if (SubjectDAO.isSubjectExists(subjectId)) {
                SubjectDAO.updateSubject(subjectId, subjectName);
                table.setValueAt(subjectName,table.getSelectedRow(),2);
            }
        }
        else if (clearBtn.equals(source)) {
            subjectIdTxt.setText("");
            subjectNameTxt.setText("");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        subjectIdTxt.setText((String) table.getValueAt(table.getSelectedRow(), 1));
        subjectNameTxt.setText((String) table.getValueAt(table.getSelectedRow(), 2));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
