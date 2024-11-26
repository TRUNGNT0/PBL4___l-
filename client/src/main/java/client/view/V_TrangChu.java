package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import client.controller.C_MyClient;
import client.model.Bean.FileInformation;

public class V_TrangChu extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private C_MyClient client;
    private JPanel menuPanel;
    private JFileChooser fileChooser;
    private JPanel fileListPanel;
    private JScrollPane scrollPane;
    private List<V_ComponentFile> selectedComponents; // Danh sách các file được chọn
    //private List<V_ComponentDirectory> selectedDirectories;	//Danh sách các Directory được chọn

    public V_TrangChu() {
        initUI();
        client = C_MyClient.getInstance("localhost", 8888);
        selectedComponents = new ArrayList<>();
        //selectedDirectories = new ArrayList<>();

        // Tải danh sách file từ server
        updateFileList(client.load());
    }

    /**
     * Khởi tạo giao diện người dùng với cải tiến về UI.
     */
    private void initUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Trang chủ");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel title = new JLabel("Ứng dụng quản lý upload và dowload file", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(Color.decode("#8E44AD"));
        add(title, BorderLayout.NORTH);

        // File chooser
        fileChooser = new JFileChooser();

        // File list panel
        fileListPanel = new JPanel();
        fileListPanel.setLayout(new BoxLayout(fileListPanel, BoxLayout.Y_AXIS));
        fileListPanel.setBackground(Color.decode("#F4E7F8"));

        scrollPane = new JScrollPane(fileListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.decode("#E3AADD"));
        add(menuPanel, BorderLayout.WEST);

        // Menu buttons
        String[] menuItems = {"UpLoad", "Load", "DownLoad", "Delete", "Back", "Setting"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            button.setBackground(Color.decode("#9B59B6"));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.addActionListener(this);

            // Thêm hiệu ứng hover
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.decode("#8E44AD"));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(Color.decode("#9B59B6"));
                }
            });

            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        setVisible(true);
    }

    /**
     * Hiển thị danh sách file từ server.
     */
    private void updateFileList(FileInformation[] fileInformationList) {
        fileListPanel.removeAll();
        selectedComponents = new ArrayList<>();

        if (fileInformationList != null) {
            for (FileInformation file : fileInformationList) {
            	if(file.isFile()) {
            		V_ComponentFile fileComponent = new V_ComponentFile(file.getName(), file.getLastModified(), file.getSize(), this);
            		fileListPanel.add(fileComponent);
            	} else {
            		V_ComponentDirectory directoryComponentDirectory = new V_ComponentDirectory(file.getName(), this);
            		fileListPanel.add(directoryComponentDirectory);
            	}
            }
        }

        fileListPanel.revalidate();
        fileListPanel.repaint();
    }

    /**
     * Cập nhật danh sách các file được chọn.
     */
    public void updateSelections(V_ComponentFile component) {
        if (component.isSelected() && !selectedComponents.contains(component)) {
            selectedComponents.add(component);
        } else if (!component.isSelected()) {
            selectedComponents.remove(component);
        }
    }

    /**
     * Hủy chọn toàn bộ các file.
     */
    public void clearSelections() {
        for (V_ComponentFile component : selectedComponents) {
            component.setSelected(false);
        }
        selectedComponents.clear();
    }
    
    /**
     * Cập nhật danh sách các folder được chọn.
     */
    
//    public void updateDirectorySelections(V_ComponentDirectory component) {
//        if (component.isSelected() && !selectedDirectories.contains(component)) {
//            selectedDirectories.add(component);
//        } else if (!component.isSelected()) {
//            selectedDirectories.remove(component);
//        }
//    }
//    
//    /**
//     * Hủy chọn toàn bộ các folder.
//     */
//
//    public void clearDirectorySelections() {
//        for (V_ComponentDirectory component : selectedDirectories) {
//            component.setSelected(false);
//        }
//        selectedDirectories.clear();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "UpLoad":
                handleUpload();
                break;

            case "Load":
                updateFileList(client.load());
                break;

            case "DownLoad":
                handleDownload();
                break;

            case "Delete":
                handleDelete();
                break;
            case "Back":
            	goBackDirectory();
            	break;
            default:
                JOptionPane.showMessageDialog(this, "Lựa chọn không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    /**
     * Xử lý tải lên file.
     */
    private void handleUpload() {
    	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            client.upLoad(selectedFile.getAbsolutePath());
            updateFileList(client.load());
        }
    }

    /**
     * Xử lý tải xuống file.
     */
    private void handleDownload() {
        if (!selectedComponents.isEmpty()) {
        	if(client.checkDownLoad()) {
        		List<String> selectedFiles = new ArrayList<>();
                for (V_ComponentFile component : selectedComponents) {
                    selectedFiles.add(component.getFileName());
                }
        		client.downLoadFile(selectedFiles, "OKE");
        	} else {
        		// Mở JFileChooser để chọn thư mục lưu file
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File directory = fileChooser.getSelectedFile();
                    List<String> selectedFiles = new ArrayList<>();
                    for (V_ComponentFile component : selectedComponents) {
                        selectedFiles.add(component.getFileName());
                    }
                    client.setDownLoad(directory.getAbsolutePath());
                    //client.downLoadFile(selectedFiles, directory.getAbsolutePath());
                    client.downLoadFile(selectedFiles, "Sum");
                }
        	}
        	
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn file để tải xuống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Xử lý xóa file.
     */
    private void handleDelete() {
        if (!selectedComponents.isEmpty()) {
            List<String> selectedFiles = new ArrayList<>();
            for (V_ComponentFile component : selectedComponents) {
                selectedFiles.add(component.getFileName());
            }
            client.deleteFile(selectedFiles);
            updateFileList(client.load());
        } else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn file để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void goToDirectory(String directoryName) {
    	client.goToDirectory(directoryName);
    	updateFileList(client.load());
    }
    
    public void goBackDirectory() {
        client.goBackDirectory();
        updateFileList(client.load());
    }
}
