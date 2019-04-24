package com.network.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class form_compose {
    private JPanel panel1;
    private JTextField receiver_name;
    private JTextField subject;
    private JTextArea content_field;
    private JButton btn_send;
    private JButton btn_addToDraft;
    private JLabel receiver_label;
    private JLabel subject_label;
    private JLabel content_label;

    public form_compose() {
        receiver_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        subject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        content_field.addComponentListener(new ComponentAdapter() {
        });
        btn_addToDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
