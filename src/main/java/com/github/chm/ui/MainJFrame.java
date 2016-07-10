package com.github.chm.ui;

import com.github.chm.common.DateUtil;
import com.github.chm.common.HttpRequest;
import com.github.chm.common.JdbcUtil;
import com.github.chm.common.Util;
import com.github.chm.exception.InitDataConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

public class MainJFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    public static MainJFrame frame;

    //数据库字段
    private JTextField textField_ip = new JTextField("", 10);
    private JTextField textField_port = new JTextField("", 4);
    private JTextField textField_db = new JTextField("", 5);
    private JTextField textField_user = new JTextField("", 10);
    private JTextField textField_password = new JTextField("", 10);
    private JTextField textField_hfurl = new JTextField("0",30);

    private JTextField textField_vehicleStartTime = new JTextField("", 15);//开始抽取的时间
    private JTextField textField_vehicleStartId = new JTextField("", 15);//开始抽取的id
    private JTextArea TextArea_vehicleIds = new JTextArea();//需要抽取的卡口编号

    private JButton btnStart;
    private JButton btnStop;
    private JTextArea consoleTextArea;

    //记录程序状态
    private volatile boolean isCanceled = true;
    private volatile int status = 0;//0 表示未启动 1表示启动 -1表示停止。
    //记录运行结果
    private AtomicLong allCount = new AtomicLong(0);
    private AtomicLong successCount = new AtomicLong(0);
    private AtomicLong failCount = new AtomicLong(0);
    private JTextField textField_allCount = new JTextField("0", 10);
    private JTextField textField_successCount = new JTextField("0", 10);
    private JTextField textField_failCount = new JTextField("0", 10);



    static Logger logger = LoggerFactory.getLogger(MainJFrame.class);

    public static void main(String[] args) {
        MainJFrame frame = new MainJFrame();
        frame.setVisible(true);
        logger.info("程序启动成功");
    }

    public MainJFrame() {
        // JFrame设置
        frame = this;
        this.setTitle("本工具用来抽取311平台过车数据到华富平台");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(1000, 800);
        //setResizable(false);
        this.setLocation((dimension.width - this.getWidth()) / 2, (dimension.height - this.getHeight()) / 2);

        //配置面板
        JPanel configPane = new JPanel();
        configPane.setPreferredSize(new Dimension(980, 150));
        configPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "第1步：配置",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        //数据库配置
        JPanel datafigPane = new JPanel();
        datafigPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        datafigPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "311数据库配置",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        datafigPane.setPreferredSize(new Dimension(958, 55));

        JLabel configPane_label1 = new JLabel("    ip");
        JLabel configPane_label2 = new JLabel("    port");
        JLabel configPane_label3 = new JLabel("    db");
        JLabel configPane_label4 = new JLabel("    user");
        JLabel configPane_label5 = new JLabel("    password");
        datafigPane.add(configPane_label1);
        datafigPane.add(textField_ip);
        datafigPane.add(configPane_label2);
        datafigPane.add(textField_port);
        datafigPane.add(configPane_label3);
        datafigPane.add(textField_db);
        datafigPane.add(configPane_label4);
        datafigPane.add(textField_user);
        datafigPane.add(configPane_label5);
        datafigPane.add(textField_password);
        JPanel hfPane = new JPanel();
        hfPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        hfPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "华富接口",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        hfPane.setPreferredSize(new Dimension(700, 55));
        hfPane.add(new JLabel("   URL"));
        hfPane.add(textField_hfurl);
        configPane.add(datafigPane);
        configPane.add(hfPane);
        JButton btnSaveConfig = new JButton("保存到配置文件");
        configPane.add(btnSaveConfig);
        configPane.setLayout(new FlowLayout(FlowLayout.LEFT));


        //条件选择面板
        JPanel conditionPane = new JPanel();
        conditionPane.setPreferredSize(new Dimension(980, 200));
        conditionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "第2步: 条件选择",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JPanel conditionPane_Pane1 = new JPanel();
        conditionPane_Pane1.setLayout(new FlowLayout(FlowLayout.LEFT));
        conditionPane_Pane1.add(new JLabel("开始时间"));
        conditionPane_Pane1.add(textField_vehicleStartTime);
        conditionPane_Pane1.add(new JLabel("      起始过车ID"));
        conditionPane_Pane1.add(textField_vehicleStartId);
        conditionPane_Pane1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        conditionPane_Pane1.setPreferredSize(new Dimension(900, 50));

        JPanel conditionPane_Pane2 = new JPanel();
        conditionPane_Pane2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "需要抽取的卡口编号(以半角逗号隔开，空白表示全部)",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        conditionPane_Pane2.setPreferredSize(new Dimension(900, 100));
        conditionPane_Pane2.setLayout(new GridLayout(0, 1, 0, 0));
        JScrollPane Pane_vehicleIds = new JScrollPane();
        TextArea_vehicleIds.setLineWrap(true);
        TextArea_vehicleIds.setWrapStyleWord(true);
        Pane_vehicleIds.setViewportView(TextArea_vehicleIds);
        DefaultCaret caret = (DefaultCaret) TextArea_vehicleIds.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        conditionPane_Pane2.add(Pane_vehicleIds);

        conditionPane.add(conditionPane_Pane1);
        conditionPane.add(conditionPane_Pane2);

        //开始暂停面板
        JPanel paneBtn = new JPanel();
        paneBtn.setPreferredSize(new Dimension(900, 55));
        paneBtn.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "若要关闭窗口请停止抽取数据并等它结束",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        btnStart = new JButton("开始抽取数据");
        btnStop = new JButton("停止抽取数据");
        paneBtn.add(btnStart);
        paneBtn.add(btnStop);

        //结果展示面板
        JPanel resultPane = new JPanel();
        resultPane.setPreferredSize(new Dimension(980, 80));
        resultPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "结果记录",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        resultPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        resultPane.add(new JLabel("   共抽取记录数"));
        resultPane.add(textField_allCount);
        resultPane.add(new JLabel("   成功记录数"));
        resultPane.add(textField_successCount);
        resultPane.add(new JLabel("   失败记录数"));
        resultPane.add(textField_failCount);
        textField_allCount.setEditable(false);
        textField_successCount.setEditable(false);
        textField_failCount.setEditable(false);


        //控制台
        JPanel consolePane = new JPanel();
        consolePane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "运行日志",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        consolePane.setPreferredSize(new Dimension(980, 250));
        consolePane.setLayout(new GridLayout(0, 1, 0, 0));
        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        ((DefaultCaret) consoleTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consolePane.add(new JScrollPane(consoleTextArea));

        //添加到wrapPane
        JPanel wrapPane = new JPanel();
        wrapPane.setPreferredSize(new Dimension(100, 800));
        wrapPane.add(configPane);
        wrapPane.add(conditionPane);
        wrapPane.add(paneBtn);
        wrapPane.add(resultPane);
        wrapPane.add(consolePane);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(0, 1, 0, 0));
        JScrollPane rootWrapPane = new JScrollPane();
        contentPane.add(rootWrapPane);
        rootWrapPane.setViewportView(wrapPane);
        setContentPane(contentPane);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        letCompomentsLosePower();
                        isCanceled = false;
                        status = 1;

                        //初始化数据库连接池
                        try {
                            String ip = textField_ip.getText();
                            String port = textField_port.getText();
                            String db = textField_db.getText();
                            String username = textField_user.getText();
                            String password = textField_password.getText();
                            String jdbcUrl = Util.getMysqlJdbcUrl(ip, port, db);
                            JdbcUtil.initConnectionPool(jdbcUrl, username, password);
                            appendMessage("初始化数据库连接池成功");
                        } catch (InitDataConnectionPoolException e1) {
                            appendMessage(e1.getMessage());
                            logger.error(e1.getMessage(), e1);
                            isCanceled = true;
                            status = -1;
                            recoverCompomentsPower();
                            return;
                        }
                        //todo 测试hfUrl是否可以访问
                        //开始抽取任务
                        ExecutorService executor = Executors.newCachedThreadPool();
                        for (; ; ) {
                            //step1 从数据库里面抽取数据
                            if(isCanceled == true){
                                status = -1;
                                recoverCompomentsPower();
                                return;
                            }
                            Long lastVehicleId = 0L;
                            Connection conn = null;
                            try {
                                conn = JdbcUtil.getConnection();
                                allCount.compareAndSet(allCount.get(), allCount.get() + 20L);
                                List<Future<Boolean>> futures = new ArrayList<Future<Boolean>>();
                                for (int i = 0; i < 20; i++) {
                                    Future<Boolean> future = executor.submit(new Callable<Boolean>() {
                                        @Override
                                        public Boolean call() throws Exception {
                                            String url = "http://image18-c.poco.cn/mypoco/myphoto/20160707/21/17369701120160707215339095_640.jpg?682x1024_120";
                                            String s = new HttpRequest().getBase64Img(url);
                                            return true;
                                        }
                                    });
                                    futures.add(future);
                                }
                                //等待所有任务完成
                                for (; ; ) {
                                    boolean hasJob = false;
                                    for(Future<Boolean> future: futures){
                                        if(!future.isDone()){
                                            hasJob = true;
                                            break;
                                        }
                                    }
                                    if(!hasJob){
                                        break;
                                    }
                                }
                                for(Future future : futures){
                                    try {
                                        future.get();
                                        successCount.incrementAndGet();
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                        failCount.incrementAndGet();
                                    } catch (ExecutionException e1) {
                                        failCount.incrementAndGet();
                                        appendMessage(e1.getMessage());
                                        e1.printStackTrace();
                                    }
                                }
                                refreshResult();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }finally{
                                //必须关闭数据库连接
                                if(conn !=null){
                                    try {
                                        conn.close();
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }

                        }
                    }
                }).start();
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isCanceled = true;
            }
        });
        btnSaveConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties prop = new Properties();
                prop.setProperty("ip", textField_ip.getText());
                prop.setProperty("port", textField_port.getText());
                prop.setProperty("db", textField_db.getText());
                prop.setProperty("username", textField_user.getText());
                prop.setProperty("password", textField_password.getText());
                prop.setProperty("hfurl", textField_hfurl.getText());
                if (Util.writePropertiesToFile("tmp", "config.properties", prop, "save prop")) {
                    appendMessage("保存配置到文件成功");
                } else {
                    appendMessage("保存配置到文件失败");
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                if (isCanceled = true && status != 1) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    appendMessage("程序正在运行或正在停止");
                }
            }

            @Override
            public void windowOpened(WindowEvent event) {
                Properties prop = Util.readPropertiesFromFile("tmp", "config.properties");
                String ip = prop.getProperty("ip", "");
                String port = prop.getProperty("port", "");
                String db = prop.getProperty("db", "");
                String username = prop.getProperty("username", "");
                String password = prop.getProperty("password", "");
                String hfurl = prop.getProperty("hfurl","");
                textField_ip.setText(ip);
                textField_port.setText(port);
                textField_db.setText(db);
                textField_user.setText(username);
                textField_password.setText(password);
                textField_hfurl.setText(hfurl);
            }
        });

    }

    public void appendMessage(final String msg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String time = DateUtil.format(new java.util.Date(System.currentTimeMillis()));
                consoleTextArea.append(time + "  " + msg + "\n");
                String text = consoleTextArea.getText();
                if (text != null && text.length() > 30000) {
                    text = text.substring(text.length() - 10000, text.length());
                    consoleTextArea.setText(text);
                    consoleTextArea.setCaretPosition(consoleTextArea.getDocument().getLength());//设置光标总是在最后一行
                }
            }
        });
    }

    public void refreshResult() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField_allCount.setText(allCount.toString());
                textField_successCount.setText(successCount.toString());
                textField_failCount.setText(failCount.toString());
            }
        });
    }
    public void letCompomentsLosePower(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField_ip.setEditable(false);
                textField_port.setEditable(false);
                textField_db.setEditable(false);
                textField_user.setEditable(false);
                textField_password.setEditable(false);
                textField_hfurl.setEditable(false);
                textField_vehicleStartTime.setEditable(false);
                textField_vehicleStartId.setEditable(false);
                btnStart.setEnabled(false);
            }
        });
    }
    public void recoverCompomentsPower(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField_ip.setEditable(true);
                textField_port.setEditable(true);
                textField_db.setEditable(true);
                textField_user.setEditable(true);
                textField_password.setEditable(true);
                textField_hfurl.setEditable(true);
                textField_vehicleStartTime.setEditable(true);
                textField_vehicleStartId.setEditable(true);
                btnStart.setEnabled(true);
            }
        });
    }
}
