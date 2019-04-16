package com.network.project.mail;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class POPUtil {
    private Socket socket;
    private Scanner socketReader;
    private PrintWriter socketWriter;
    public POPUtil(String server, int port) throws Exception {
        try {
            socket=new Socket(server, port);
            socketReader=new Scanner(socket.getInputStream());
            socketWriter=new PrintWriter(socket.getOutputStream(),true);
            
        } catch (Exception e) {
            // TODO: handle exception
            throw new Exception("The url or port is wrong");
        }
        
    }
    public void sendCommand(String s) {
        socketWriter.print(s+"\r\n");
        socketWriter.flush();
    }
    
    public Vector<String> getAnswer() {
        Vector<String> result=new Vector<>();
        String temp = "";
        try {
            while (!(temp = socketReader.nextLine()).equals(".")||temp==null) {
                result.addElement(temp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }
    
    //我用的是网易邮箱，这里的密码指的是授权码
    public boolean login(String name, String password) {
        sendCommand("user "+name);
        if(!socketReader.nextLine().startsWith("+OK")) {
            System.out.println("Wrong username");
        }
        sendCommand("pass "+password);
        if(!socketReader.nextLine().startsWith("+OK")) {
            System.out.println("Wrong password");
        }
        System.out.println("login successfully");
        return true;
    }
    //返回邮件数量
    public int getMailNum() {
        sendCommand("list");
        int i=0;
        String temp=socketReader.nextLine();
        try {
            i=Integer.parseInt(temp.split(" ")[1]);
            getAnswer();
        }catch (Exception e){
            System.out.println("There is an error!");
        }
        
        return i;
    }
    //获取邮件头
    /*
     * 测试的时候出现了问题，top命令没有回应
     */
    /*public Map<String,String> getMailHead(int i) {
        Map<String, String> result=new HashMap<>();
        String command="top "+i;
        sendCommand(command);
        String temp;
        while(!((temp=socketReader.nextLine()).equals("."))) {
            if(temp.contains("OK"))
                System.out.println("Success");
            if (temp.contains(":")) {
                String property = temp.split(":")[0];
                String content = temp.split(":")[1];
                System.out.println(property + ":" + content);
                result.put(property, content);
            }
        }
        return result;
        
    }*/
    
    //接收邮件(第i份邮件)
    public Vector<String> getContent(int i) throws UnsupportedEncodingException {
        Vector<String> result=new Vector<>();
        try {
            String command = "retr " + i;
            sendCommand(command);
            String temp = socketReader.nextLine();
            if (temp.contains("OK"))
                System.out.println("Success");
            else {
                System.out.println("Error");
                return null;
            }
            while (!((temp = socketReader.nextLine()).equals("."))) {
                result.addElement(temp);
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
        
    }
    /*
     * 删除第i封邮件
     */
    public boolean deleteItem(int i) {
        String command="dele "+i;
        sendCommand(command);
        String line=socketReader.nextLine();
        if(line.length() == 0 || !line.startsWith("+OK")){
            System.out.println("Delete failed!");
            return false;
        }else {
            System.out.println("Delete "+i+" successfully");
            return true;
        }
        //return true;
    }
    
    public static void main(String[] args) throws Exception {
        POPUtil popUtil=new POPUtil("pop.163.com", 110);
        popUtil.login("13197389627", "Wc123456789");
        System.out.println(popUtil.getMailNum());
        popUtil.getContent(2);
        //popUtil.deleteItem(2);
        popUtil.getContent(4);
    }
    
    
    
}
