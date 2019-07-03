package com.GUI.Method;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField implements FocusListener {
    JTextField textField;
    String init;

    public HintTextField(JTextField text, String str) {
        text.setText(str);
        text.setForeground(Color.GRAY);

        textField = text;
        init = str;
    }

    public void focusGained(FocusEvent e) {
        textField.setText("");

        textField.setForeground(Color.BLACK);
    }

    public void focusLost(FocusEvent e) {
        if(textField.getText().equals("")){
            textField.setText(init);
            textField.setForeground(Color.GRAY);
        }
    }
}