package com.network.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.network.utils.DBConnection;

//读取邮件写入数据库

public class Mail {




    private int mid;//按邮件加载顺序排列，0,1,...,n
    private String mail_unique;//UIDL获取的邮件唯一标识
    private int uid;
    private String to;//收件人地址
    private String from;//发件人地址
    private String subject;
    private String content;
    private Date stime;

    private boolean isDel;//1回收站 0原位
    private boolean isReceive;//1收件 0发件
    private boolean isRead;//1收件箱已读 0收件箱未读
    private int sendCond;//0未发送，草稿箱 1发送成功，已发送 2正在发送/发送失败，发件箱；(若邮件在收件箱内，该状态默认为-1)

    public Mail(){ }

    public int getMid() {
        return mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setStime(Date stime) {
        this.stime = stime;
    }

    public String getToAddrById(int id){
        String toaddr="";
        Connection conn = DBConnection.getConnection();
        Statement state =null;
        ResultSet rs = null;
        try{
            state = conn.createStatement();
            String sql = "SELECT toaddr FROM mails WHERE mid='" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()){
                toaddr = rs.getString("toaddr");
            }
            return toaddr;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
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

    public String getFromAddrById(int id){
        String fromaddr = "";
        Connection conn = DBConnection.getConnection();
        Statement state =null;
        ResultSet rs = null;
        try{
            state = conn.createStatement();
            String sql = "SELECT fromaddr FROM mails WHERE mid='" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()){
                fromaddr = rs.getString("fromaddr");
            }
            return fromaddr;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
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

    public String getSubjectById(int id){
        String subject = "";
        Connection conn = DBConnection.getConnection();
        Statement state =null;
        ResultSet rs = null;
        try{
            state = conn.createStatement();
            String sql = "SELECT subject FROM mails WHERE mid='" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()){
                subject = rs.getString("subject");
            }
            return subject;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
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

    public String getContentById(int id){
        String content = "";
        Connection conn = DBConnection.getConnection();
        Statement state =null;
        ResultSet rs = null;
        try{
            state = conn.createStatement();
            String sql = "SELECT content FROM mails WHERE mid='" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()){
                content = rs.getString("content");
            }
            return content;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
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

    public Date getSTimeById(int id){
        Date time = null;
        Connection conn = DBConnection.getConnection();
        Statement state =null;
        ResultSet rs = null;
        try{
            state = conn.createStatement();
            String sql = "SELECT stime FROM mails WHERE mid='" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()){
                time = rs.getDate("stime");
            }
            return time;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }finally {
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

    public List<Mail> getAllMail() {
        Connection conn = DBConnection.getConnection();
        List<Mail> list = new ArrayList<Mail>();
        String sql = "SELECT mid,toaddr,fromaddr,subject,content,stime FROM mails";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()){
                Mail m = new Mail();
                m.setMid(rs.getInt("mid"));
                m.setTo(rs.getString("toaddr"));
                m.setFrom(rs.getString("fromaddr"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setStime(rs.getDate("stime"));
                list.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
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

    //写新邮件，返回mailid
    public int writeNewMail(String inputTo, String inputFrom, String inputSubject, String inputContent, java.util.Date inputDate){
        Connection conn = DBConnection.getConnection();
        int mailid = 0;
        java.sql.Date sqlDate = new java.sql.Date(inputDate.getTime());
        String sql = "INSERT INTO mails(toaddr,fromaddr,subject,content,stime)values(?,?,?,?,?);";
        String sql2 = "select * from mails order by mid desc limit 0,1;";
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,inputTo);
            pstmt.setString(2,inputFrom);
            pstmt.setString(3,inputSubject);
            pstmt.setString(4,inputContent);
            pstmt.setDate(5,sqlDate);
            pstmt.executeUpdate();
            conn.commit();
            pstmt2 = conn.prepareStatement(sql2);
            rs = pstmt2.executeQuery();
            if(rs.next()){
                mailid = rs.getInt("mid");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null)
                    conn.close();
                if (pstmt != null)
                    pstmt.close();
                if (pstmt2 != null)
                    pstmt2.close();
                if (rs != null)
                    rs.close();

            } catch (SQLException e) {
            }
        }
        return mailid;
    }

}
