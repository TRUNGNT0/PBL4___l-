package client.view.MyComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import client.controller.HomePageController;
import client.view.HomePage;

public class ComponentFileController implements MouseListener{
	private ComponentFile view;
	private HomePage parentView;
	private HomePageController parentController;
	
	public ComponentFileController(ComponentFile view, HomePage parentView, HomePageController parentController) {
		this.view = view;
		this.parentView = parentView;
		this.parentController = parentController;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {	//Double click
			parentController.componentFile_doubleClick(view.getFileInformation());
		} else if (e.getButton() == MouseEvent.BUTTON3) { // Xử lý chuột phải 
            if (!view.isSelected()) {
                parentView.clearSelections(); 
                view.setSelected(true);   
            }
            view.showPopupMenu(e.getX(), e.getY());
        } else if (e.isControlDown()) { // Xử lý Ctrl + Click (chọn/bỏ chọn nhiều mục)
        	view.setSelected(!view.isSelected());
        } else { // Xử lý click chuột trái (chọn đơn lẻ)
            parentView.clearSelections(); 
            view.setSelected(!view.isSelected());
        }

        // Cập nhật danh sách lựa chọn trong đối tượng cha
        parentView.updateSelections(view);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(!view.isSelected()) {
			view.changeBackground("#F2DDDC");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(!view.isSelected()) {
			view.changeBackground("#F9F7F1");
		}
	}
}
