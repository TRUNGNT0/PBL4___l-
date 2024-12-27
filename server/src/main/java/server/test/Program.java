package server.test;

import javax.swing.SwingUtilities;
import server.view.StartView;

public class Program {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new StartView());
	}
}
