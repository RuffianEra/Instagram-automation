package com.GUI.Method;

import javax.swing.*;

public class ShareJPanel {

    /** 获取JPanel面板，所有内容只显示一行 */
    public static JPanel getJPanel(String str) {
        JPanel site = new JPanel();
        site.setLayout(new BoxLayout(site, BoxLayout.LINE_AXIS));
        site.add(Box.createHorizontalStrut(10));
        site.add(new JLabel(str));
        site.add(Box.createHorizontalStrut(10));
        return site;
    }
}