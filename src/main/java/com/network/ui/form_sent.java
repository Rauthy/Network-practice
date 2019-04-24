package com.network.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class form_sent {
    private JTextField receiver_name;
    private JTextField subject;
    private JTextArea content_field;
    private JButton btn_delete;
    private JLabel receiver_label;
    private JLabel subject_label;
    private JLabel content_label;

    public form_sent() {

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
