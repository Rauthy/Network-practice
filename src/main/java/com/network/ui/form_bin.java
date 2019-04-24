package com.network.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class form_bin {
    private JTextField sender_name;
    private JTextField subject;
    private JTextArea content_field;
    private JButton btn_fullyDelete;
    private JLabel sender_label;
    private JLabel subject_label;
    private JLabel content_label;


    public form_bin() {
        btn_fullyDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
