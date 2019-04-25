package com.network.ui;

import com.network.entity.Mail;
import com.network.entity.User;
import com.network.mail.SMTPUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Date;

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
    private User user;
    private Mail mail;
    private int uid;

    private String username;
    private String smtp;
    private String pop;

    public form_draft(String username,String smtp,String pop,int mid) {
        uid = user.getIdByUsername(username);
        receiver_name.setText(mail.getToAddrById(mid));
        subject.setText(mail.getSubjectById(mid));
        content_field.setText(mail.getContentById(mid));

        JFrame frame = new JFrame(mail.getSubjectById(mid));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fully_delete = JOptionPane.showConfirmDialog(panel1,"你确定要删除此邮件吗？","提示",JOptionPane.YES_NO_OPTION);
                if(fully_delete == JOptionPane.YES_OPTION){
                    user.moveToTrash(mid,uid);
                    JOptionPane.showMessageDialog(panel1, "删除成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                }
                if(fully_delete == JOptionPane.NO_OPTION){
                    return;
                }
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
                        JOptionPane.showMessageDialog(panel1, "发送失败！", "提示", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

//    public static void main(String[] args){
//        form_draft newDraft = new form_draft();
//    }
}
