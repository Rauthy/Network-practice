package com.network.ui;

import com.network.entity.Mail;

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
    private JPanel panel1;

    public form_receive(int mail_id) {

        JFrame frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 //删除收件箱邮件
            }
        });

    }
}
