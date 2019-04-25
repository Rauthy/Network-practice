package com.network.ui;

import com.network.entity.Mail;
import com.network.entity.User;
import com.network.mail.SMTPUtil;

import java.io.InputStreamReader;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

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
    private User user;
    private int uid;
    private String username;
    private String smtp;
    private String pop;

    public form_compose(String username,String smtp,String pop) {
        this.smtp = smtp;
        uid = user.getIdByUsername(username);

        JFrame frame = new JFrame("写邮件");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_addToDraft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mail newWrite = new Mail();
                int mid = newWrite.writeNewMail(receiver_name.getText(), username, subject.getText(), content_field.getText(), new Date());
                user.writeMyMail(mid, uid);
                JOptionPane.showMessageDialog(panel1, "已成功移动到草稿箱！", "提示", JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                return;
            }
        });
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //需要判断是否发送成功
                    Mail newWrite = new Mail();
                    int mid = newWrite.writeNewMail(receiver_name.getText(), username, subject.getText(), content_field.getText(), new Date());
                    user.writeMyMail(mid, uid);
                    user.moveMyMailToOutbox(mid,uid);

                    SMTPUtil nsmtp = new SMTPUtil(smtp, 25);
                    boolean status = nsmtp.checkSendStatus();

                    if (status) {
                        user.moveToSent(mid,uid);
                        JOptionPane.showMessageDialog(panel1, "发送成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(panel1, "发送失败，已移至草稿箱！", "提示", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                     ex.printStackTrace();
                }
            }
        });


    }

    public static void main(String[] args){
        form_compose fc = new form_compose("Cai","smtp.126.com","pop3.126.com");
    }
}
