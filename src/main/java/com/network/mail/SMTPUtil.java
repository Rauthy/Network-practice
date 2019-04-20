package com.network.mail;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPUtil {
    //主题
    private String subject;
    //发出地址
    private String senderAdd;
    //密码，授权码
    private String psword;
    //smtp服务器地址
    private String smtpServer;
    //smtp端口（25）
    final int SMTP_PORT = 25;
    //收件地址
    private String receiverAdd;
    //内容
    private String content;
    //socket smtp_in smtp_out
    private Socket smtpSocket;
    private BufferedReader smtp_in;
    private PrintWriter smtp_out;


    public void setSubject(String subject){
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setSenderAdd(String senderAdd){
        this.senderAdd = senderAdd;
    }

    public String getSenderAdd(){
        return senderAdd;
    }

    public void setPsword(String psword){
        this.psword = psword;
    }

    public String getPsword(){
        return psword;
    }

    public void setSmtpServer(String smtpServer){
        this.smtpServer = smtpServer;
    }

    public String getSmtpServer(){
        return smtpServer;
    }

    public void setReceiverAdd(String receiverAdd){
        this.receiverAdd = receiverAdd;
    }

    public String getReceiverAdd(){
        return receiverAdd;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    /*public void SMTPUtil(String smtpServer, String senderAdd, String psword){
        setSmtpServer(smtpServer);
        setSenderAdd(senderAdd);
        setPsword(psword);
        //连接服务器
        login();
    }*/


    public void login(){
        try{
            smtpSocket = new Socket(smtpServer,SMTP_PORT);
            smtp_in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
            smtp_out = new PrintWriter(smtpSocket.getOutputStream());
            String res = smtp_in.readLine();
            System.out.println("receive ---> " + res);

            sendCommand("HELO " + senderAdd);

            sendCommand(("auth login"));

            sendCommand(Base64Util.toBase64(senderAdd.substring(0,senderAdd.indexOf("@"))));

            sendCommand(Base64Util.toBase64(psword));

        }catch(Exception e){
            System.out.println("登录失败！");
        }

    }

    public void sendMail(String subject, String content){
        try{
            sendCommand("mail from:<" + senderAdd + ">");
            sendCommand("rcpt to:<" + senderAdd + ">");
            sendCommand("data");
            String sendContent = new String("From:" + senderAdd + "\r\n"
                                                   +"To:" + receiverAdd + "\r\n"
                                                   +"Subject:"+subject + "\r\n" + "\r\n"
                                                   + content + "\r\n"
                                                   + ".");
            sendCommand(sendContent);

        }catch(Exception e){

        }
    }

    public void sendCommand(String command){
        try {
            smtp_out.print(command + "\r\n");
            smtp_out.flush();
            String res = smtp_in.readLine();
            System.out.println("receive ---> " + res);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
           SMTPUtil smtptest1 = new SMTPUtil();
           smtptest1.setSmtpServer("smtp.qq.com");
           smtptest1.setSenderAdd("506444834@qq.com");
           smtptest1.setPsword("etmsztykjmjzcaif");
           smtptest1.setReceiverAdd("caihongyang316@126.com");
           smtptest1.login();
           smtptest1.sendMail("test","Hello!");
    }
}
