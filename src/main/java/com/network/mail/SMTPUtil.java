package com.network.mail;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SMTPUtil{

    //发出地址
    private String name;
    //密码，授权码
    private String psword;
    //smtp服务器地址
    private String smtpServer;
    //smtp端口（25）
    private int SMTP_PORT = 25;

    //socket smtp_in smtp_out
    private Socket smtpSocket;
    private BufferedReader smtp_in;
    private PrintWriter smtp_out;

    public SMTPUtil(String server, int port) throws Exception {
        try{
            this.smtpServer=server;
            this.SMTP_PORT=port;
            smtpSocket = new Socket(smtpServer,SMTP_PORT);
            smtp_in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
            smtp_out = new PrintWriter(smtpSocket.getOutputStream());
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("The url or port is wrong");
        }

    }

    public boolean login(String addr, String pswd){
        try{
            this.name =addr;
            this.psword=pswd;
//            System.out.println("receive ---> " + res);

            sendCommand("HELO " + name);
            sendCommand(("auth login"));
            sendCommand(Base64Util.toBase64(name));
            sendCommand(Base64Util.toBase64(psword));
            String res = smtp_in.readLine();

            System.out.println(res);
            System.out.println(res.substring(0,3).equals("235"));
            if(res.substring(0,3).equals("235")){
                return true;
            }
            else
                return false;
            }catch(Exception e){
            System.out.println("登录失败！");
            return false;
        }

    }

    public void sendMail(String subject, String content,String send, String receive){

            sendCommand("mail from:<" + send + ">");
            sendCommand("rcpt to:<" + receive + ">");
            sendCommand("data");
            String sendContent = new String("From:" + send + "\r\n"
                    +"To:" + receive + "\r\n"
                    +"Subject:"+subject + "\r\n" + "\r\n"
                    + content + "\r\n"
                    + ".");
            sendCommand(sendContent);
            }

    public void sendCommand(String command){
        try {
            smtp_out.print(command + "\r\n");
            smtp_out.flush();
            String res = smtp_in.readLine();
//            System.out.println("receive ---> " + res);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean checkSendStatus()throws Exception{
        try {
            smtp_in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
            String res = smtp_in.readLine();
            if (res.substring(0, 3).equals("250")) {
                return true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args)throws Exception{
           SMTPUtil smtptest = new SMTPUtil("smtp.126.com",25);
           smtptest.login("caihongyang316","qaz1234qaz1234");
    }
}
