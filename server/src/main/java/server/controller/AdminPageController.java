package server.controller;

import server.model.BO.SessionManager;
import server.view.AdminPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingUtilities;

public class AdminPageController implements ActionListener{
    private AdminPage view;
    private SessionManager model;

    public AdminPageController(SessionManager sessionManager) {
        this.model = sessionManager;
        this.view = new AdminPage(this);

        // Cập nhật giao diện lần đầu
        updateView();

        // Tạo luồng riêng để lắng nghe thay đổi
//        new Thread(() -> {
//            while (true) {
//                updateView();
//                try {
//                    Thread.sleep(1000); // Cập nhật mỗi giây
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    // Phương thức cập nhật giao diện
    private void updateView() {
        SwingUtilities.invokeLater(() -> {
            Object[][] data = model.getAllSessions();
            view.updateTable(data);
        });
    }

    // Phương thức xóa session
    public void deleteSession(String username) {
        model.removeSessionId(username);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
	    case "Delete":
	    	List<String> selectedUser = view.getSelectedUser();
	    	for(String x : selectedUser) {
	    		deleteSession(x);
	    	}
	    	updateView();
	        break;
	        
	    case "Load":
	    	updateView();
	        break;
	        
	    default:
	        
	        break;
		}
		
	}
}
