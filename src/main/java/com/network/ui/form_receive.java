package com.network.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class form_receive {
    private JTextField sender_name;
    private JTextField subject;
    private JTextArea content_filed;
    private JButton btn_delete;
    private JLabel subject_label;
    private JLabel content_label;
    private JLabel sender_label;

    public form_receive() {

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    }
}
