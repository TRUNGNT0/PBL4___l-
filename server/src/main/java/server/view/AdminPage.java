package server.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import server.controller.AdminPageController;

public class AdminPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTable sessionTable;
    private DefaultTableModel tableModel;
    private JButton deleteButton;
    private JButton loadButton;
    private JPanel southPanel;

    AdminPageController controller;

    public AdminPage(AdminPageController controller) {
        this.controller = controller;
        setTitle("Admin Page - Session Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo bảng hiển thị
        String[] columnNames = {"Username", "Session ID", "Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        sessionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(sessionTable);

        // Nút xóa
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(controller);

        // Nút Load
        loadButton = new JButton("Load");
        loadButton.addActionListener(controller);
        
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        southPanel.add(loadButton);
        southPanel.add(deleteButton);
        
        // Thêm các thành phần vào giao diện
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Phương thức cập nhật bảng
    public void updateTable(Object[][] data) {
        tableModel.setRowCount(0); // Xóa tất cả dữ liệu hiện tại
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }
    
    public List<String> getSelectedUser() {
        List<String> selectedUser = new ArrayList<String>();
        int[] selectedRows = sessionTable.getSelectedRows();
        if (selectedRows.length >= 0) {
            for (int i = 0; i < selectedRows.length; i++) {
                String username = (String) tableModel.getValueAt(selectedRows[i], 0);
                selectedUser.add(username);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để xóa.");
        }
        return selectedUser;
    }
}