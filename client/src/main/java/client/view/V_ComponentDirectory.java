package client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class V_ComponentDirectory extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Attributes
    private String directoryName;
    //private boolean isSelected;
    
    //Component
    private JLabel lbDirectoryName;
    
    
    private JPopupMenu popupMenu;
    private V_TrangChu parent; // Tham chiếu đến JFrame chứa component này

    // Constructor
    public V_ComponentDirectory(String directoryName, V_TrangChu parent) {
        this.directoryName = directoryName;
        this.parent = parent;
        //this.isSelected = false;
        init();
    }

    // Initialize components
    private void init() {
        // JPanel setup
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        this.setMinimumSize(new Dimension(0, 40));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        this.setBackground(Color.decode("#D5F5E3"));
        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // JLabel for file name
        String displayText = directoryName;
        if (directoryName.length() > 70) { // Giới hạn ký tự hiển thị
            displayText = directoryName.substring(0, 70) + "...";
        }
        lbDirectoryName = new JLabel(displayText);
        this.add(lbDirectoryName, BorderLayout.CENTER);
        this.setToolTipText(this.directoryName);

        // Context menu (popup menu)
        createPopupMenu();

        // Add mouse listener
        this.addMouseListener(this);
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
    public String getDirectoryName() {
        return directoryName;
    }

//    public void setSelected(boolean isSelected) {
//        this.isSelected = isSelected;
//        setBackground(isSelected ? Color.decode("#F6BCBA") : Color.decode("#F2DDDC")); // Cập nhật màu nền
//    }
//
//    public boolean isSelected() {
//        return isSelected;
//    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    	if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
    	    parent.goToDirectory(directoryName);
    	}
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
