package com.network.ui;
/**
 * Created by caihongyang on 2019/4/16.
 */
import com.network.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {

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
                String username = userName.getText();
                String password = String.valueOf(pwContent.getPassword());
                String smtp_server = smtpAdd.getText();
                String pop3_server = pop3Add.getText();

                if(username.isEmpty()){
                    JOptionPane.showConfirmDialog(mypanel,"Please input your user name!","Warning",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(password.isEmpty()){
                    JOptionPane.showConfirmDialog(mypanel,"Please input your password!","Warning",JOptionPane.WARNING_MESSAGE);
                    return;

                }

                if(smtp_server.isEmpty() || pop3_server.isEmpty()) {
                    JOptionPane.showConfirmDialog(mypanel, "Please input at least one server address!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                User u = new User();
                if(u.isLocalUser(username)){
                    System.out.println("local user");
                    if(u.userVerify(username,password)){
                        System.out.println("local user login successfully");

                        JFrame frame = new JFrame("邮箱系统");
                        frame.setContentPane(new MainSystem(username).getPanel1());
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setSize(800,600);
                        frame.setVisible(true);
                    }else{
                        System.out.println("local user wrong password");
                    }
                }else{
                    System.out.println("new user");
                    int status = u.addLocalUser(username,password,pop3_server,smtp_server);
                    if(status<=0){
                        JOptionPane.showConfirmDialog(mypanel, "Please check your server address!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                }


            }
        });

        login.setBounds(160, 210, 80, 25);
        mypanel.add(login);
    }




        public static void main(String[] args){
            JFrame frame = new JFrame("LOG IN");
            frame.setSize(400,300);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            manageComponents(panel);
            frame.add(panel);
            frame.setVisible(true);
    }

}


