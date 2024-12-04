package client.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.controller.SettingPageController;

public class SettingPage extends JDialog {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField txtDownloadDirectory;
    private JButton btnChooseDownloadDirectory;

    private SettingPageController controller;

    public SettingPage(SettingPageController controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        setTitle("Setting");
        setSize(853, 480);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setModal(true); // Đặt chế độ modal để ngăn tương tác với cửa sổ khác

        // Tạo label, text field và button
        JLabel lblDownloadDirectory = new JLabel("Download Directory:");
        txtDownloadDirectory = new JTextField(50);
        txtDownloadDirectory.setEditable(false);
        btnChooseDownloadDirectory = new JButton("Choose Download Directory");

        // Panel để tổ chức các thành phần
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

        // Thêm label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblDownloadDirectory, gbc);

        // Thêm text field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDownloadDirectory, gbc);

        // Thêm button
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnChooseDownloadDirectory, gbc);

        // Xử lý sự kiện button
        btnChooseDownloadDirectory.addActionListener(controller);

        // Thêm panel vào dialog
        add(panel);

    }

    public void txtDownloadDirectorySetText(String s) {
        txtDownloadDirectory.setText(s);
    }
    
    public String selectFolder() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Chỉ cho phép chọn thư mục
        folderChooser.setDialogTitle("Select Download Directory");

        int result = folderChooser.showOpenDialog(this); // Hiển thị JFileChooser
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath(); // Trả về đường dẫn tuyệt đối của thư mục
        } else {
            return ""; 
        }
    }

    public void showError(String text) {
        JOptionPane.showMessageDialog(this, text, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
