package com.github.chm.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel conditionPane;
	private JPanel configPane;	
	private JButton btnStart;
	private JButton btnStop;

	public MainJFrame() {
		// JFrame设置
		this.setTitle("本工具用来抽取311平台过车数据到华富平台");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1000, 800);
		setResizable(false);
		this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//配置面板
		configPane = new JPanel();
		configPane.setPreferredSize(new Dimension(980, 150));
		configPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "第1步：配置",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//数据库配置
		JPanel datafigPane = new JPanel();
		datafigPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		datafigPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "数据库配置",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		datafigPane.setPreferredSize(new Dimension(900, 55));
		JLabel configPane_label1 = new JLabel("    ip");
		JLabel configPane_label2 = new JLabel("    port");
		JLabel configPane_label3 = new JLabel("    db");
		JLabel configPane_label4 = new JLabel("    user");
		JLabel configPane_label5 = new JLabel("    password");
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
		hfPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		hfPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "华富接口",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		hfPane.setPreferredSize(new Dimension(900, 55));
		JLabel hfPane_label1 = new JLabel("   URL");
		JTextField hfPane_url = new JTextField("", 30);
	    hfPane.add(hfPane_label1);
	    hfPane.add(hfPane_url);
		configPane.add(datafigPane);
		configPane.add(hfPane);
		
		//条件选择面板
		conditionPane = new JPanel();
		conditionPane.setPreferredSize(new Dimension(980, 200));
		conditionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "第2步: 条件选择",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JPanel conditionPane_Pane1 = new JPanel();
		conditionPane_Pane1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel conditionPane_label1 = new JLabel("开始时间");
		JTextField conditionPane_textField_startTime = new JTextField("", 15);
		JTextField condition_VEHICLE_ID = new JTextField("",15);
		conditionPane_Pane1.add(conditionPane_label1);
		conditionPane_Pane1.add(conditionPane_textField_startTime);	
		conditionPane_Pane1.add(new JLabel("      起始过车ID"));
		conditionPane_Pane1.add(condition_VEHICLE_ID);
		conditionPane_Pane1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		conditionPane_Pane1.setPreferredSize(new Dimension(900, 50));
		
		JScrollPane conditionPane_Pane2 = new JScrollPane();
		conditionPane_Pane2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "需要抽取的卡口编号(以半角逗号隔开，空白表示全部)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		conditionPane_Pane2.setPreferredSize(new Dimension(900, 50));
	

		conditionPane.add(conditionPane_Pane1);
		conditionPane.add(conditionPane_Pane2);
		
		//开始暂停面板
		JPanel paneBtn = new JPanel();
		paneBtn.setPreferredSize(new Dimension(900, 55));
		paneBtn.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "若要关闭窗口请先停止，否则可能造成数据不一致",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		btnStart = new JButton("开始抽取数据");
		btnStop= new JButton("停止抽取数据");
		paneBtn.add(btnStart);
		paneBtn.add(btnStop);
		
		//
		contentPane = new JPanel();
		contentPane.add(configPane);
		contentPane.add(conditionPane);
		contentPane.add(paneBtn);
		setContentPane(contentPane);
	}

	private void condition_startId() {
		// TODO Auto-generated method stub
		
	}
	
}
