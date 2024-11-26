package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class V_ComponentFile extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Attributes
    private String fileName;
    private boolean isSelected;
    private long lastModified;
    private long size;
    //Component
    private JLabel lbFileName;
    private JLabel lbLastModified;
    private JLabel lbSize;
    
    
    private JPopupMenu popupMenu;
    private V_TrangChu parent; // Tham chiếu đến JFrame chứa component này

    // Constructor
    public V_ComponentFile(String fileName, long lastModified, long size, V_TrangChu parent) {
        this.fileName = fileName;
        this.lastModified = lastModified;
        this.size = size;
        this.parent = parent;
        this.isSelected = false;
        init();
    }

    // Initialize components
//    private void init() {
//        // JPanel setup
//        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
//        this.setMinimumSize(new Dimension(0, 40));
//        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
//        this.setBackground(Color.decode("#F2DDDC"));
//        this.setLayout(new BorderLayout());
//        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//
//        // JLabel for file name
//        String displayText = fileName;
//        if (fileName.length() > 70) { // Giới hạn ký tự hiển thị
//            displayText = fileName.substring(0, 70) + "...";
//        }
//        lbFileName = new JLabel(displayText);
//        this.add(lbFileName, BorderLayout.CENTER);
//        this.setToolTipText(this.fileName);
//
//        // Context menu (popup menu)
//        createPopupMenu();
//
//        // Add mouse listener
//        this.addMouseListener(this);
//    }
    
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
        String displayText = fileName;
        if (fileName.length() > 50) { // Giới hạn ký tự hiển thị
            displayText = fileName.substring(0, 50) + "...";
        }
        lbFileName = new JLabel(displayText);
        lbFileName.setFont(new Font("Arial", Font.BOLD, 14));
        detailsPanel.add(lbFileName);

        // Last modified and size labels
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lbLastModified = new JLabel("Last Modified: " + dateFormat.format(lastModified));
        lbSize = new JLabel("Size: " + formatSize(size));
        lbLastModified.setFont(new Font("Arial", Font.PLAIN, 12));
        lbSize.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add to panel
        JPanel subDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subDetailsPanel.setOpaque(false);
        subDetailsPanel.add(lbLastModified);
        subDetailsPanel.add(lbSize);
        detailsPanel.add(subDetailsPanel);

        // Add details panel to main panel
        this.add(detailsPanel, BorderLayout.CENTER);

        // Context menu (popup menu)
        createPopupMenu();

        // Add mouse listener
        this.addMouseListener(this);
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
        menuItem1.addActionListener(parent);
        menuItem2.addActionListener(parent);
        menuItem3.addActionListener(parent);

        popupMenu.add(menuItem1);
        popupMenu.add(menuItem2);
        popupMenu.add(menuItem3);
    }

    // Getter and setter for fileName
    public String getFileName() {
        return fileName;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        setBackground(isSelected ? Color.decode("#F6BCBA") : Color.decode("#F2DDDC")); // Cập nhật màu nền
    }

    public boolean isSelected() {
        return isSelected;
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { // Right-click to show menu
        	parent.clearSelections();
            this.isSelected = true;
            popupMenu.show(e.getComponent(), e.getX(), e.getY());
        } else if (e.isControlDown()) { // Nhấn giữ ctrl và click
            this.isSelected = !this.isSelected;
        } else { // Click bình thường
            parent.clearSelections();
            this.isSelected = true;
        }
        parent.updateSelections(this); // Cập nhật danh sách
        this.setSelected(this.isSelected); // Hiển thị trạng thái
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
