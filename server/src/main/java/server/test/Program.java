package server.test;

import javax.swing.SwingUtilities;
import server.view.V_Setup;

public class Program {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new V_Setup());
	}
}
