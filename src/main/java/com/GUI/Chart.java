package com.GUI;

import com.GUI.Entity.Relevance;
import com.GUI.Method.HintTextField;
import com.GUI.Method.ShareJPanel;
import com.GUI.Method.ShareMouseListener;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Chart extends JFrame {
    /** 自动读取用户帐号和密码 */
    public static Map<String, String[]> user = new HashMap<>();
    /** 自动读取评论地址 */
    public static List<String> url = new ArrayList<>();
    public static List<Integer> urls = new ArrayList<>();
    /** 自动读取评论数据 */
    public static List<String> data = new ArrayList<>();
    public static List<Integer> datas = new ArrayList<>();
    /** 用户集 */
    public static Map<String, Relevance> relevance = new HashMap<>();

    /**
     * 自动读取文件
     */
    public static void autoRead() {
        try{
            BufferedReader mapRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\user.txt")));
            String mapString = null;
            while((mapString = mapRead.readLine()) != null) {
                String[] str = mapString.split(",");
                user.put(str[0], str);
            }

            BufferedReader urlRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\url.txt")));
            while((mapString = urlRead.readLine()) != null) {
                url.add(mapString);
            }

            BufferedReader dataRead = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\data.txt")));
            while((mapString = dataRead.readLine()) != null) {
                data.add(mapString);
            }
        }
        catch (FileNotFoundException fe) {
            System.out.println(fe.getLocalizedMessage());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getLocalizedMessage());
        }
    }

    /**
     * 随机为用户分配评论地址和评论数据
     */
    public static void autoMatching() {
        for(String key:user.keySet()) {
            relevance.put(key, new Relevance(user.get(key), random(urls, url), random(datas, data)));
        }
    }

    public static String random(List<Integer> size, List<String> set) {
        if(size.size() == set.size()){ size.clear(); }

        while(true){
            int number =  (int)(Math.random() * set.size());
            if(!size.contains(number)) {
                size.add(number);
                return set.get(number);
            }
        }
    }

    public static void main(String[] args) {
        /** 自动获取数据并随机分配 */
        autoRead();
        autoMatching();

        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\chromedriver_75.0.3770.90.exe");
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            System.out.println("GUI桌面样式报错");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        new Chart().setVisible(true);
    }

    public Chart() {
        this.setBounds(200, 150, 800, 600);
        this.setTitle("Instagram自动化评论");

        JSplitPane one = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.top(), this.centre());
        one.setResizeWeight(0.3);
        JSplitPane two = new JSplitPane(JSplitPane.VERTICAL_SPLIT, one, this.botton());
        two.setResizeWeight(0.8);

        this.add(two);
    }

    JButton save = new JButton("保存");
    JButton start = new JButton("开始评论");

    /** 头部面板 */
    public JPanel top() {
        JButton toLead = new JButton("文本导入");
        toLead.addMouseListener(new ShareMouseListener() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser file = new JFileChooser();
                if(file.showOpenDialog(file) == JFileChooser.APPROVE_OPTION) {
                    try{
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getSelectedFile())));
                        String data = null;
                        while((data = reader.readLine()) != null){
                            System.out.println(data);
                        }
                    }
                    catch (FileNotFoundException fe) {
                        System.out.println(fe.getLocalizedMessage());
                    }
                    catch (IOException ioe) {
                        System.out.println(ioe.getLocalizedMessage());
                    }
                }
            }
        });

        JPanel top = new JPanel();
        top.setMinimumSize(new Dimension());
        top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
        top.add(Box.createHorizontalStrut(5));
        top.add(save);
        top.add(Box.createHorizontalStrut(5));
        top.add(start);
        top.add(Box.createHorizontalStrut(5));

        return top;
    }

    /** 中间面板 */
   public JSplitPane centre() {
       Object[] objects = user.keySet().toArray();
       JList<Object> jList = new JList<>(objects);

       /** 评论地址 */
       JPanel site = ShareJPanel.getJPanel("评论地址");
       JTextField siteFrame = new JTextField();
       site.add(siteFrame);

       /** 评论数据 */
       JPanel data = ShareJPanel.getJPanel("评论数据");
       JTextField dataFrame = new JTextField();
       data.add(dataFrame);

       /** 评论开关 */
       JPanel on_off = ShareJPanel.getJPanel("评论开关");
       JRadioButton on = new JRadioButton("ON");
       on.setActionCommand("false");
       JRadioButton off = new JRadioButton("OFF");
       off.setActionCommand("true");
       on_off.add(off);
       on_off.add(Box.createHorizontalStrut(10));
       on_off.add(on);
       on_off.add(Box.createHorizontalGlue());
       ButtonGroup group = new ButtonGroup();
       group.add(off);
       group.add(on);

       /** 代理设置 */
       JPanel proxy = ShareJPanel.getJPanel("代理设置");
       JTextField url = new JTextField();
       url.addFocusListener(new HintTextField(url, "URL"));
       JTextField port = new JTextField();
       port.addFocusListener(new HintTextField(port, "port"));
       proxy.add(url);
       proxy.add(Box.createHorizontalStrut(10));
       proxy.add(port);

       JPanel jPanel = new JPanel();
       jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
       jPanel.add(Box.createVerticalStrut(10));
       jPanel.add(site);
       jPanel.add(Box.createVerticalStrut(10));
       jPanel.add(data);
       jPanel.add(Box.createVerticalStrut(10));
       jPanel.add(on_off);
       jPanel.add(Box.createVerticalStrut(10));
       jPanel.add(proxy);
       jPanel.add(Box.createVerticalStrut(140));


       /** ------------------------------------------------------组件定义完毕，为组件添加事件------------------------------------------------------- */

       class FeedIn{
           /** 输入 */
           public void in(List<Object> list) {
               for(Object obj:list){
                   Relevance rel = relevance.get(obj);
                   System.out.println(rel);
                   siteFrame.setText(rel.getUrl());
                   dataFrame.setText(rel.getData());
                   (rel.isOn_off()?off:on).setSelected(true);
               }
           }
           /** 输出 */
           public void out(List<Object> list) {
               for(Object obj:list) {
                   Relevance rel = relevance.get(obj);
                   rel.setUrl(siteFrame.getText());
                   rel.setData(dataFrame.getText());
                   rel.setOn_off(Boolean.getBoolean(group.getSelection().getActionCommand()));
               }
           }
       }
       FeedIn feedIn = new FeedIn();

       jList.addMouseListener(new ShareMouseListener() {
           public void mouseClicked(MouseEvent e) {
               feedIn.in(jList.getSelectedValuesList());
           }
       });

       /*JButton save = new JButton("保存");
       JButton start = new JButton("开始评论");*/
       /** 添加保存事件 */
       save.addMouseListener(new ShareMouseListener() {
           public void mouseClicked(MouseEvent e) {
               List<Object> user = jList.getSelectedValuesList();
               if(!user.isEmpty()){ feedIn.out(user); }
           }
       });
       /** 添加评论事件 */
       start.addMouseListener(new ShareMouseListener() {
           public void mouseClicked(MouseEvent e) {
               StartComment.run();
           }
       });

       JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(jList), jPanel);
       jSplitPane.setResizeWeight(0.5);
       return jSplitPane;
    }

    /** 底部面板 */
    public JTabbedPane botton() {
        JTabbedPane queue = new JTabbedPane();
        queue.add("评论动态", new TextArea());
        return  queue;
    }
}