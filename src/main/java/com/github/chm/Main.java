package com.github.chm;

import javax.swing.SwingUtilities;
import com.github.chm.ui.MainJFrame;

public class Main {
	public static void main(String[] args){
		 SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				 MainJFrame frame = new MainJFrame();
				 frame.setVisible(true);				
			}
		});		 
	}
}
