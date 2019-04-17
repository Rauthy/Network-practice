package com.network.ui;
/**
 * Created by caihongyang on 2019/4/16.
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Guitest {

    private static void manageComponents(JPanel mypanel) {
        mypanel.setLayout(null);

        JLabel userLabel = new JLabel("User: ");
        userLabel.setBounds(50, 30, 80, 25);
        mypanel.add(userLabel);

        JTextField userName = new JTextField(30);
        userName.setBounds(150, 30, 165, 25);
        mypanel.add(userName);

        JLabel pwLabel = new JLabel("Password: ");
        pwLabel.setBounds(50, 70, 80, 25);
        mypanel.add(pwLabel);

        JPasswordField pwContent = new JPasswordField(30);
        pwContent.setBounds(150, 70, 165, 25);
        mypanel.add(pwContent);

        JLabel smtpLabel = new JLabel("SMTP server address: ");
        smtpLabel.setBounds(10, 110, 200, 25);
        mypanel.add(smtpLabel);

        JTextField smtpAdd = new JTextField(30);
        smtpAdd.setBounds(150, 110, 165, 25);
        mypanel.add(smtpAdd);

        JLabel pop3Label = new JLabel("POP3 server address: ");
        pop3Label.setBounds(10, 150, 200, 25);
        mypanel.add(pop3Label);

        JTextField pop3Add = new JTextField(30);
        pop3Add.setBounds(150, 150, 165, 25);
        mypanel.add(pop3Add);

        JButton login = new JButton("Log in");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userName.getText().isEmpty()){
                    JOptionPane.showConfirmDialog(mypanel,"Please input your user name!","Warning",JOptionPane.WARNING_MESSAGE);
                    //System.out.println("缺少用户名！");
                    return;
                }

                if(pwContent.getPassword().length == 0){
                    JOptionPane.showConfirmDialog(mypanel,"Please input your password!","Warning",JOptionPane.WARNING_MESSAGE);
                    //System.out.println("缺少密码！");
                    return;
                }

                if(smtpAdd.getText().isEmpty() || pop3Add.getText().isEmpty()) {
                    JOptionPane.showConfirmDialog(mypanel, "Please input at least one server address!", "Warning", JOptionPane.WARNING_MESSAGE);
                    //System.out.println("缺少地址！");
                    return;
                }
            }
        });

        login.setBounds(160, 210, 80, 25);
        mypanel.add(login);
    }

        public static void main(String[] args){
            JFrame frame = new JFrame("LOG IN");
            frame.setSize(400,300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            manageComponents(panel);
            frame.add(panel);
            frame.setVisible(true);
    }

    }


