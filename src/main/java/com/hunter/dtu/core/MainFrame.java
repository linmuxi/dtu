package com.hunter.dtu.core;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class MainFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainFrame.class);

    public static void run() {
        try {
            UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());// 还可以
        } catch (Exception e) {
        }
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Dubbo Test UI");
        frame.setSize(600, 530);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - frame.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel intefaceLabel = new JLabel("Interface:");
        intefaceLabel.setBounds(10, 20, 80, 25);
        panel.add(intefaceLabel);
        final JComboBox<Object> interfaceBox = new JComboBox<Object>(getInterfaceMenuList());
        //interfaceBox.setEditable(true);
        interfaceBox.setBounds(100, 20, 400, 25);
        panel.add(interfaceBox);

        JLabel methodLabel = new JLabel("Method:");
        methodLabel.setBounds(10, 50, 80, 25);
        panel.add(methodLabel);
        final JComboBox<Object> methodBox = new JComboBox<Object>(getMethodMenuList());
        //topicBox.setEditable(true);
        methodBox.setBounds(100, 50, 400, 25);
        panel.add(methodBox);

        JLabel jsonLabel = new JLabel("JSON:");
        jsonLabel.setBounds(10, 80, 80, 25);
        panel.add(jsonLabel);
        final JTextArea jsonText = new JTextArea();

        JScrollPane scroll = new JScrollPane(jsonText);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scroll.setBounds(100, 80, 400, 350);
        jsonText.setLineWrap(true);
        panel.add(scroll);

        final JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(100, 430, 300, 25);
        panel.add(resultLabel);

        JButton loginButton = new JButton("GO");
        loginButton.setBounds(100, 460, 80, 25);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new Thread(new Runnable() {
                    public void run() {
                        resultLabel.setText("");
                        String strInterface = interfaceBox.getSelectedItem().toString();
                        String strMethod = methodBox.getSelectedItem().toString();
                        JSONObject jsonValue = JSONObject.parseObject(jsonText.getText().toString());
                        LOGGER.info("开始发送dubbo报文,Interface:{},Method:{},Value:{}",new Object[]{strInterface,strMethod,jsonValue.toString()});
                        String result = DubboUtil.invoke(strInterface, strMethod, jsonValue);
                        if(null != result && result.length() > 0){
                            resultLabel.setText("发送报文成功");
                        }else{
                            resultLabel.setText("发送报文失败");
                        }
                        LOGGER.info("发送dubbo报文完成 ,result:{}",result);
                    }
                }).start();
            }
        });
        
        interfaceBox.addItemListener(new ItemListener() {
            
            public void itemStateChanged(ItemEvent e) {
                methodBox.removeAllItems();
                List<String> methodList = DubboUtil.lsAllMethod(e.getItem().toString());
                LOGGER.info("重新获取接口{}的方法列表,MethodList:{}",e.getItem().toString(),methodList);
                if(null != methodList && methodList.size() > 0){
                    for (String string : methodList) {
                        methodBox.addItem(string);
                    }
                }
                methodBox.setVisible(true);
            }
        });

        jsonText.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
            }

            public void focusGained(FocusEvent e) {
                resultLabel.setText("");
            }
        });

        methodBox.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
            }

            public void focusGained(FocusEvent e) {
                resultLabel.setText("");
            }
        });

        panel.add(loginButton);
    }

    /**
     * 函数的目的/功能
     * @return
    */
    private static Object[] getMethodMenuList() {
        Object[] interfaces = getInterfaceMenuList();
        if(null != interfaces && interfaces.length > 0){
            List<String> methodList = DubboUtil.lsAllMethod(String.valueOf(interfaces[0]));
            if(null != methodList && methodList.size() > 0){
                return methodList.toArray();
            }   
        }
        return new Object[]{};
    }

    /**
     * 函数的目的/功能
     * @return
    */
    private static Object[] getInterfaceMenuList() {
        return DubboUtil.lsAllInterface().toArray();
    }


}
