package com.network.ui;

import com.network.entity.Mail;
import com.network.entity.User;

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
    private User user;
    private Mail mail;
    private int uid;

    public form_receive(String username,int mid) {
        uid = user.getIdByUsername(username);

        JFrame frame = new JFrame(mail.getSubjectById(mid));
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 //删除收件箱邮件
                int delete = JOptionPane.showConfirmDialog(panel1,"你确定要删除此邮件吗？","提示",JOptionPane.YES_NO_OPTION);
                if(delete == JOptionPane.YES_OPTION){
                    user.moveToTrash(mid,uid);
                    JOptionPane.showMessageDialog(panel1, "彻底删除成功！", "提示", JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                }
                if(delete == JOptionPane.NO_OPTION){
                    return;
                }
            }
        });

    }
}
