package com.network.ui;

import com.network.entity.Mail;

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
    private JPanel panel1;

    public form_sent(Mail mail) {

        JFrame frame = new JFrame("邮件");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 //删除发件箱邮件
            }
        });
    }
}
