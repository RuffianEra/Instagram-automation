package com.GUI;

import com.GUI.Entity.Relevance;
import com.GUI.Entity.URLData;
import com.GUI.Method.HintTextField;
import com.GUI.Method.ReadFile;
import com.GUI.Method.ShareJPanel;
import com.GUI.Method.ShareMouseListener;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;

public class Chart extends JFrame {

    public static void main(String[] args) {
        ReadFile.fixedMatching();
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
        this.setTitle("facebook   instagram 自动评论系统");

        JSplitPane one = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.top(), this.centre());
        one.setResizeWeight(0.3);
        JSplitPane two = new JSplitPane(JSplitPane.VERTICAL_SPLIT, one, this.botton());
        two.setResizeWeight(0.8);

        this.add(two);
        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) { }
            public void windowClosing(WindowEvent e) {
                try{
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir") + "\\user.txt", false)));
                    for(Map.Entry entry:ReadFile.relevanceMap.entrySet()){
                        Relevance rel = (Relevance) entry.getValue();
                        writer.write(rel.getUserTxt() + "\n");
                        System.out.println(rel.getUserTxt());
                    }
                    writer.close();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                Chart.this.setDefaultCloseOperation(EXIT_ON_CLOSE);             //退出窗口时关闭程序
            }
            public void windowClosed(WindowEvent e) { }
            public void windowIconified(WindowEvent e) { }
            public void windowDeiconified(WindowEvent e) { }
            public void windowActivated(WindowEvent e) { }
            public void windowDeactivated(WindowEvent e) { }
        });
    }

    JButton save = new JButton("保存");
    JButton start = new JButton("开始评论");

    /** 头部面板 */
    public JPanel top() {
        /*JButton toLead = new JButton("文本导入");
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
        });*/

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
       Object[] objects = ReadFile.user.keySet().toArray();
       JList<Object> jList = new JList<>(objects);

       /** 评论地址 */
       JPanel site = ShareJPanel.getJPanel("评论地址");
       JComboBox siteFrame = new JComboBox();
       site.add(siteFrame);

       /** 评论数据 */
       JPanel data = ShareJPanel.getJPanel("评论数据");
       JTextField dataFrame = new JTextField();
       data.add(dataFrame);

       siteFrame.addItemListener((ItemEvent e) -> {
           if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println(e.getItem());
               for(URLData urlData:ReadFile.relevanceMap.get(jList.getSelectedValue()).getUrlData()){
                   if(urlData.url.equals(e.getItem())){
                       dataFrame.setText(urlData.data);
                   }
               }
           }
       });

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
           public void in(Object obj) {
               Relevance rel = ReadFile.relevanceMap.get(obj);
               System.out.println(rel);
               siteFrame.removeAllItems();
               for(URLData urlData:rel.getUrlData()){
                   siteFrame.addItem(urlData.url);
               }
               dataFrame.setText(rel.getUrlData().get(0).data);
               (rel.isOn_off()?off:on).setSelected(true);
           }
           /** 输出 */
           public void out(Object obj) {
               Relevance relevance = ReadFile.relevanceMap.get(obj);
               for(URLData urlData:relevance.getUrlData()){
                   if(urlData.url.equals(siteFrame.getSelectedItem())){
                       dataFrame.setText(urlData.data);
                       urlData.data = dataFrame.getText();
                   }
               }
               relevance.setOn_off(group.getSelection().getActionCommand().equals("true"));
           }
       }
       FeedIn feedIn = new FeedIn();

       jList.addMouseListener(new ShareMouseListener() {
           public void mouseClicked(MouseEvent e) {
               feedIn.in(jList.getSelectedValue());
           }
       });

       /** 添加保存事件 */
       save.addMouseListener(new ShareMouseListener() {
           public void mouseClicked(MouseEvent e) {
               Object user = jList.getSelectedValue();
               if(!(user == null)){ feedIn.out(user); }
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