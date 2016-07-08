package com.github.chm;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.chm.ui.MainJFrame;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args){
		 SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				 MainJFrame frame = new MainJFrame();
				 frame.setVisible(true);
				 logger.info("程序启动成功");
			}			
		});		 
	}
}