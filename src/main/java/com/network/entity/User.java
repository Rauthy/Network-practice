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
    private boolean isValid;//true可用 false失效，默认为true

    private List<Mail> inbox;//收件箱
    private List<Mail> inbox_unread;//收件箱未读邮件
    private List<Mail> sent;//已发送
    private List<Mail> drafts;//草稿箱
    private List<Mail> outbox;//发件箱
    private List<Mail> trash;//回收站

    public User(){}
    public User(String name, String passwd, String smtp, String pop3){
        this.username = name;
        this.password = passwd;
        this.smtp = smtp;
        this.pop3 = pop3;
        this.isValid = true;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public void setPop3(String pop3) {
        this.pop3 = pop3;
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

    public List<User> getAllUser() {
        Connection conn = DBConnection.getConnection();
        List<User> list = new ArrayList<>();
        String sql = "SELECT uid,username,password,smtp,pop3,isvalid FROM users";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUid(rs.getInt("uid"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setSmtp(rs.getString("smtp"));
                u.setPop3(rs.getString("pop3"));
                u.setValid(rs.getBoolean("isvalid"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
        }
        return list;
    }

    public List<Mail> getMyInboxMail(int id){
        List<Mail> inbox = new ArrayList<Mail>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isreceive=1 AND isdel=0 AND sendcond=-1";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                inbox.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return inbox;
    }

    public List<Mail> getMyOutboxMail(int id){
        List<Mail> outbox = new ArrayList<Mail>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isreceive=0 AND isdel=0 AND sendcond=2";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                outbox.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return outbox;
    }

    public List<Mail> getMySentMail(int id){
        List<Mail> sent = new ArrayList<Mail>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isreceive=0 AND isdel=0 AND sendcond=1";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                sent.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return sent;
    }

    public List<Mail> getMyDraftMail(int id){
        List<Mail> draft = new ArrayList<Mail>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isreceive=0 AND isdel=0 AND sendcond=0";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                draft.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return draft;
    }

    public List<Mail> getMyTrashMail(int id){
        List<Mail> trash = new ArrayList<Mail>();
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isdel=1";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                trash.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return trash;
    }

    public static void main(String[] args) throws Exception {
        User u = new User();








        /**
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
        **/
    }






}
