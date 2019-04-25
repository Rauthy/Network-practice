package com.network.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class form_draft {
    private JTextField receiver_name;
    private JTextField subject;
    private JTextArea content_field;
    private JButton btn_send;
    private JButton btn_delete;
    private JLabel receiver_label;
    private JLabel subject_label;
    private JLabel content_label;
    private JPanel panel1;

    private String username;
    private String smtp_server;
    private String pop3_server;

    public form_draft(String username,String smtp_server,String pop3_server) {
        this.username = username;
        this.smtp_server = smtp_server;
        this.pop3_server = pop3_server;

        JFrame frame = new JFrame("写邮件");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

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
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel1, "已删除！", "提示", JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                return;
            }
        });
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //需要判断是否发送成功
                /**
                 * if(success){
                 *    JOptionPane.showMessageDialog(panel1, "发送成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                 *    frame.dispose();
                 *    return;}
                 * else{
                 *    JOptionPane.showMessageDialog(panel1, "发送失败，已移至草稿箱！", "提示", JOptionPane.PLAIN_MESSAGE);
                 *    return;
                 * }
                 */
            }
        });
    }

//    public static void main(String[] args){
//        form_draft newDraft = new form_draft();
//    }
}
