package com.network.entity;

import java.util.List;

public class User {

    private int uid;
    private String username;
    private String email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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


}
