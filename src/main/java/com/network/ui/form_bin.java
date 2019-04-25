package com.network.ui;

import com.network.entity.Mail;
import com.network.entity.User;
import jdk.nashorn.internal.scripts.JO;

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
    private JButton btn_resume;
    private JPanel panel1;
    private User user;
    private int uid;
    private Mail mail;

    private String username;
    private String smtp;
    private String pop;

    public form_bin(String username,int mid) {
        uid = user.getIdByUsername(username);
        sender_name.setText(mail.getFromAddrById(mid));
        subject.setText(mail.getSubjectById(mid));
        content_field.setText(mail.getContentById(mid));

        JFrame frame = new JFrame(mail.getSubjectById(mid));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_fullyDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 int fully_delete = JOptionPane.showConfirmDialog(panel1,"你确定要彻底删除此邮件吗？","提示",JOptionPane.YES_NO_OPTION);
                 if(fully_delete == JOptionPane.YES_OPTION){
                     user.deleteTrashMail(mid,uid);
                     JOptionPane.showMessageDialog(panel1, "彻底删除成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                     frame.dispose();
                 }
                 if(fully_delete == JOptionPane.NO_OPTION){
                     return;
                 }
            }
        });
        btn_resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
