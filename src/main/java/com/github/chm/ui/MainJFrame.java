package com.github.chm.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel conditionPane;
	private JPanel configPane;

	public MainJFrame() {
		// JFrame设置
		this.setTitle("本工具用来抽取311平台过车数据到华富平台");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1000, 800);
		this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//配置面板
		configPane = new JPanel();
		configPane.setPreferredSize(new Dimension(980, 150));
		configPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "配置",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//数据库配置
		JPanel datafigPane = new JPanel();
		datafigPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "数据库配置",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		datafigPane.setPreferredSize(new Dimension(960, 55));
		JLabel configPane_label1 = new JLabel("ip");
		JLabel configPane_label2 = new JLabel("port");
		JLabel configPane_label3 = new JLabel("db");
		JLabel configPane_label4 = new JLabel("user");
		JLabel configPane_label5 = new JLabel("password");
		JTextField configPane_textField_ip = new JTextField("", 10);
		JTextField configPane_textField_port = new JTextField("", 4);
		JTextField configPane_textField_db = new JTextField("", 5);
		JTextField configPane_textField_user = new JTextField("", 10);
		JTextField configPane_textField_password = new JTextField("", 10);
		datafigPane.add(configPane_label1);
		datafigPane.add(configPane_textField_ip);
		datafigPane.add(configPane_label2);
		datafigPane.add(configPane_textField_port);
		datafigPane.add(configPane_label3);
		datafigPane.add(configPane_textField_db);
		datafigPane.add(configPane_label4);
		datafigPane.add(configPane_textField_user);
		datafigPane.add(configPane_label5);
		datafigPane.add(configPane_textField_password);
		JPanel hfPane = new JPanel();
		hfPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "华富接口",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		hfPane.setPreferredSize(new Dimension(960, 55));
		JLabel hfPane_label1 = new JLabel("URL");
		JTextField hfPane_url = new JTextField("", 55);
	    hfPane.add(hfPane_label1);
	    hfPane.add(hfPane_url);
		configPane.add(datafigPane);
		configPane.add(hfPane);
		
		//条件选择面板
		conditionPane = new JPanel();
		conditionPane.setPreferredSize(new Dimension(980, 100));
		conditionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "第一步: 条件选择",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JLabel conditionPane_label1 = new JLabel("开始时间");
		JTextField conditionPane_textField_text = new JTextField("", 15);
		
		conditionPane.add(conditionPane_label1);
		conditionPane.add(conditionPane_textField_text);

		//
		contentPane = new JPanel();
		contentPane.add(configPane);
		contentPane.add(conditionPane);
		setContentPane(contentPane);
	}
}
