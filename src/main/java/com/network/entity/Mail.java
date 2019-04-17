package com.network.entity;

import java.util.Date;

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
    private int sendCond;//0发送失败，草稿箱 1发送成功，已发送 2正在发送，发件箱

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMail_unique() {
        return mail_unique;
    }

    public void setMail_unique(String mail_unique) {
        this.mail_unique = mail_unique;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStime() {
        return stime;
    }

    public void setStime(Date stime) {
        this.stime = stime;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getSendCond() {
        return sendCond;
    }

    public void setSendCond(int sendCond) {
        this.sendCond = sendCond;
    }




}
