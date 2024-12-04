package client.view;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import client.controller.HomePageController;
import client.model.Bean.FileInformation;
import client.view.MyComponent.ComponentFile;

public class HomePage extends JFrame {
    private static final long serialVersionUID = 1L;

    private HomePageController controller;
    
    private JPanel menuPanel;
    private JPanel fileListPanel;
    private JScrollPane scrollPane;
    private List<ComponentFile> selectedComponents; // Danh sách các file được chọn

    public HomePage(HomePageController controller) {
    	this.controller = controller;
        selectedComponents = new ArrayList<>();
        
        initUI();

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
        setLocationRelativeTo(null);
        
        // Tiêu đề
        JLabel title = new JLabel("Ứng dụng quản lý upload và dowload file", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(Color.decode("#8E44AD"));
        add(title, BorderLayout.NORTH);

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
        String[] menuItems = {"UpLoad", "Load", "New Directory", "DownLoad", "Delete", "Back", "Setting"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            button.setBackground(Color.decode("#9B59B6"));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.addActionListener(controller);

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
    public void updateFileList(List<FileInformation> fileInformationList) {
        fileListPanel.removeAll();
        selectedComponents = new ArrayList<>();
        if (!fileInformationList.isEmpty()) {
            for (FileInformation fileInformation : fileInformationList) {
            	ComponentFile fileComponent = new ComponentFile(fileInformation, this ,this.controller);
            	fileListPanel.add(fileComponent);
            }
        }
        fileListPanel.revalidate();
        fileListPanel.repaint();
    }

    /**
     * Cập nhật danh sách các file được chọn.
     */
    public void updateSelections(ComponentFile component) {
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
        for (ComponentFile component : selectedComponents) {
            component.setSelected(false);
        }
        selectedComponents.clear();
    }
    
    public void showError(String text) {
    	JOptionPane.showMessageDialog(this, text, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    public String chooseFolder() {
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
    
    public String chooseFile() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Chỉ cho phép chọn thư mục
        folderChooser.setDialogTitle("Select Download Directory");

        int result = folderChooser.showOpenDialog(this); // Hiển thị JFileChooser
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            return selectedFolder.getAbsolutePath(); // Trả về đường dẫn tuyệt đối của thư mục
        } else {
            return "";
        }
    }
    
    public List<FileInformation> getselectedFile(){
    	List<FileInformation> selectedFile = new ArrayList<FileInformation>();
    	for(ComponentFile componentFile : selectedComponents) {
    		selectedFile.add(componentFile.getFileInformation());
    	}
    	return selectedFile;
    }
    
    
}
