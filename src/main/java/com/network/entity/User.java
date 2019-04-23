package com.network.entity;

import com.network.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    LoginVerify Check = new LoginVerify();

    private int uid;
    private String username;
//    private String email;
    private String password;
    private String smtp;
    private String pop3;
    //    private int role; //0普通用户，1管理员
    private boolean isValid;//1可用 0失效，默认为1

    private List<Mail> inbox;//收件箱
    private List<Mail> inbox_unread;//收件箱未读邮件
    private List<Mail> sent;//已发送
    private List<Mail> drafts;//草稿箱
    private List<Mail> outbox;//发件箱
    private List<Mail> trash;//回收站


    public User(){}

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPop3() {
        return pop3;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3;
    }

//    public int getRole() {
//        return role;
//    }
//
//    public void setRole(int role) {
//        this.role = role;
//    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public List<Mail> getInbox() {
        return inbox;
    }

    public void setInbox(List<Mail> inbox) {
        this.inbox = inbox;
    }

    public List<Mail> getInbox_unread() {
        return inbox_unread;
    }

    public void setInbox_unread(List<Mail> inbox_unread) {
        this.inbox_unread = inbox_unread;
    }

    public List<Mail> getSent() {
        return sent;
    }

    public void setSent(List<Mail> sent) {
        this.sent = sent;
    }

    public List<Mail> getDrafts() {
        return drafts;
    }

    public void setDrafts(List<Mail> drafts) {
        this.drafts = drafts;
    }

    public List<Mail> getOutbox() {
        return outbox;
    }

    public void setOutbox(List<Mail> outbox) {
        this.outbox = outbox;
    }

    public List<Mail> getTrash() {
        return trash;
    }

    public void setTrash(List<Mail> trash) {
        this.trash = trash;
    }

    public boolean userVerify(String username, String passwd) {
        try {
            Connection conn = DBConnection.getConnection();
            boolean isValid = Check.userVerify(conn, username, passwd);
            return isValid;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isLocalUser(String name){
        Connection conn = DBConnection.getConnection();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            String inputName = Check.replace(name);
            String sql = "SELECT * FROM users WHERE username='" + inputName + "'";
            rs = state.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (state != null)
                    state.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }
    }

    public int addLocalUser(String username, String passwd,String pop, String smtp) {
        try {
            Connection conn = DBConnection.getConnection();
            int isValid = Check.addNewUserAndLogin(conn,username,passwd,pop,smtp);
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getIdByUsername(String name) {
        Connection conn = DBConnection.getConnection();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();

            String inputName = Check.replace(name);
            String sql = "SELECT uid FROM users WHERE username='" + inputName + "'";
            rs = state.executeQuery(sql);
            if (rs.next()) {
                System.out.println("准备读取用户id");
                this.uid = rs.getInt("uid");
                System.out.println(uid);
            }
            return uid;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (state != null)
                    state.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        User u = new User();
        String name = "caihongyang316";
        String pass = "qaz1234qaz1234";
        String pop = "pop.126.com";
        String smtp = "smtp.126.com";
        if(u.isLocalUser(name)){
            System.out.println("local user");
            if(u.userVerify(name,pass)){
                System.out.println("local user login successfully");
            }else{
                System.out.println("local user wrong password");
            }
        }else{
            System.out.println("new user");
            System.out.println(u.addLocalUser(name,pass,pop,smtp));
        }

    }






}
