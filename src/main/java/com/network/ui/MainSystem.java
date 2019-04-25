package com.network.ui;

import com.network.entity.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainSystem {
    public JPanel getPanel1() {
        return panel1;
    }

//    private JFrame frame;
    private JPanel panel1;
    private JList selection_board;
    private JButton btn_quit;
    private JTable table_maillist;
    private JButton btn_compose;
    private JLabel label_username;
    private String username;
    private String smtp;
    private String pop;
    private User user;

    public MainSystem(String username){

          user.getIdByUsername(username);

        JFrame frame = new JFrame("邮件系统");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setVisible(true);

        label_username.setText(username);

        //退出
        btn_quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        //写邮件
        btn_compose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  form_compose fc = new form_compose(username,smtp,pop);
            }
        });
    }

    //获取list当前所选项
    public void handleSelection() {

        selection_board.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                int index = selection_board.getSelectedIndex();
                switch (index){
                    case 0:
                        loadTables(0);//收件箱被选中
                    case 1:
                        loadTables(1);//草稿箱被选中
                    case 2:
                        loadTables(2);//发件箱被选中
                    case 3:
                        loadTables(3);//已发送被选中
                    case 4:
                        loadTables(4);//回收站被选中
                }
            }

        });

    }

    public void loadTables(int index){

    }

    public static void main(String[] args){

//        JFrame frame = new JFrame("邮件系统");
//        frame.setContentPane(new MainSystem("Cai","smtp.qq.com","pop3.qq.com").panel1);
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.pack();
//        frame.setSize(800,600);
//        frame.setVisible(true);

        MainSystem ms = new MainSystem("Cai");
    }

}
