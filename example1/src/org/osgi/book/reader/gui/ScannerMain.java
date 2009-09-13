package org.osgi.book.reader.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScannerMain {

	public static void main(String[] args) {
		ScannerFrame frame = new ScannerFrame();
		frame.pack();
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		frame.setVisible(true);
	}
	
}
