package client.view.MyComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import client.controller.HomePageController;
import client.model.Bean.FileInformation;
import client.view.HomePage;

public class ComponentFile extends JPanel {
    private static final long serialVersionUID = 1L;

    // Attributes
    private FileInformation fileInformation;
    private boolean isSelected;
    //Component
    private JLabel lbFileName;
    
    private JPopupMenu popupMenu;
   	private ComponentFileController controller;
   	private HomePageController parentController;

    // Constructor
    public ComponentFile(FileInformation fileInformation, HomePage parentView,  HomePageController parentController) {
        this.fileInformation = fileInformation;
        this.isSelected = false;
        this.parentController = parentController;
        this.controller = new ComponentFileController(this, parentView, parentController);
        init();
    }
    
    private void init() {
        // JPanel setup
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        this.setMinimumSize(new Dimension(0, 60));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));
        this.setBackground(Color.decode("#F2DDDC"));
        
        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Center panel for file details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(2, 1)); // Hiển thị 2 dòng
        detailsPanel.setOpaque(false);

        // File name label
        String displayText = fileInformation.getName();
        if (displayText.length() > 50) { // Giới hạn ký tự hiển thị
            displayText = displayText.substring(0, 50) + "...";
        }
        lbFileName = new JLabel(displayText);
        lbFileName.setFont(new Font("Arial", Font.BOLD, 14));
        detailsPanel.add(lbFileName);
        
        if(fileInformation.isFile()) {
        	// Last modified and size labels
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JLabel lbLastModified = new JLabel("Last Modified: " + dateFormat.format(fileInformation.getLastModified()));
            JLabel lbSize = new JLabel("Size: " + formatSize(fileInformation.getSize()));
            lbLastModified.setFont(new Font("Arial", Font.PLAIN, 12));
            lbSize.setFont(new Font("Arial", Font.PLAIN, 12));

            // Add to panel
            JPanel subDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            subDetailsPanel.setOpaque(false);
            subDetailsPanel.add(lbLastModified);
            subDetailsPanel.add(lbSize);
            detailsPanel.add(subDetailsPanel);
        }
        // Add details panel to main panel
        this.add(detailsPanel, BorderLayout.CENTER);

        // Context menu (popup menu)
        createPopupMenu();

        // Add mouse listener
        this.addMouseListener(controller);
    }




    // Format size to human-readable format
    private String formatSize(long size) {
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    // Create context menu
    private void createPopupMenu() {
        popupMenu = new JPopupMenu();

        // Add menu items
        JMenuItem menuItem1 = new JMenuItem("DownLoad");
        JMenuItem menuItem2 = new JMenuItem("Delete");
        JMenuItem menuItem3 = new JMenuItem("Copy");
        menuItem1.addActionListener(parentController);
        menuItem2.addActionListener(parentController);
        menuItem3.addActionListener(parentController);

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.add(menuItem3);
    }

    // Getter and setter for fileName
    public FileInformation getFileInformation() {
        return fileInformation;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        setBackground(isSelected ? Color.decode("#F6BCBA") : Color.decode("#F2DDDC")); // Cập nhật màu nền
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public void showPopupMenu(int x, int y) {
    	popupMenu.show(this, x, y);
    }
}
