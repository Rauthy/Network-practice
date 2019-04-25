package com.network.entity;

import com.network.utils.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.Date;

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


    public String getUsername() {
        return username;
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
        String sql = "SELECT mid, toaddr,fromaddr,subject,content,stime " +
                "FROM (user_mail RIGHT JOIN mails ON user_mail.mid=mails.mid)" +
                "WHERE uid = '" + id + "' AND isreceive=1 AND isdel=0 AND sendcond=-1";
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Mail m = new Mail();
                m.setMid(rs.getInt("mid"));
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


    public int receiveNewMail(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt1 = null;
        int i = 0;
        String sql1 = "INSERT INTO user_mail(mid, uid,isdel,isreceive,isread,sendcond)VALUES(?,?,?,?,?,?);";
        try{
            pstmt1 = conn.prepareStatement(sql1);

            pstmt1.setInt(1,mailid);
            pstmt1.setInt(2,userid);
            pstmt1.setInt(3,0);
            pstmt1.setInt(4,1);
            pstmt1.setInt(5,0);
            pstmt1.setInt(6,-1);
            i = pstmt1.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt1 != null)
                    pstmt1.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    public int readMyMail(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        String sql = "UPDATE user_mail SET isread=1 WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }



    //草稿箱
    public int writeMyMail(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt1 = null;
        int i = 0;
        String sql1 = "INSERT INTO user_mail(mid, uid,isdel,isreceive,isread,sendcond)VALUES(?,?,?,?,?,?);";
        try{
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1,mailid);
            pstmt1.setInt(2,userid);
            pstmt1.setInt(3,0);
            pstmt1.setInt(4,0);
            pstmt1.setInt(5,0);
            pstmt1.setInt(6,0);
            i = pstmt1.executeUpdate();
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt1 != null)
                    pstmt1.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    //发件箱
    public int moveMyMailToOutbox(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        String sql = "UPDATE user_mail SET sendcond=1 WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }


    //已发送
    public int moveToSent(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        String sql = "UPDATE user_mail SET sendcond=2 WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    //回收站
    public int moveToTrash(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        String sql = "UPDATE user_mail SET isdel=1 WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    //从回收站恢复邮件
    public int restoreTrashMail(int mailid,int userid){
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        int i = 0;
        String sql = "UPDATE user_mail SET isdel=0 WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }

    //从回收站彻底删除邮件
    public int deleteTrashMail(int mailid, int userid){
        Connection conn = DBConnection.getConnection();
        String sql1 = "DELETE FROM user_mail WHERE mid='"+mailid+"' AND uid='"+userid+"';";
        String sql2 = "DELETE FROM mails WHERE mid='"+mailid+"';";
        int i = 0;
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement(sql1);
            i=pstmt.executeUpdate();
            System.out.println(i);
            pstmt = conn.prepareStatement(sql2);
            i=pstmt.executeUpdate();
            System.out.println(i);
            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();

        }
        finally {
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
        return i;
    }


    public static void main(String[] args) throws Exception {
        User u = new User();



    //登录
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


    //新建编写邮件
        Mail newWrite  = new Mail();
        int mid = newWrite.writeNewMail("toaddr","fromaddr","subject","content",new Date());
        System.out.println("mid:"+mid);
        u.writeMyMail(mid,u.getIdByUsername(name));


    }






}
