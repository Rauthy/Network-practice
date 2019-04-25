package com.network.mail;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.lang.model.element.Element;

import com.network.entity.Mail;

public class POPUtil {
    private Socket socket;
    private Scanner socketReader;
    private PrintWriter socketWriter;
    private int port=110;
    private String name;
    private String password;

    public POPUtil(String server,String name,String password) throws Exception{
        this.name=name;
        this.password=password;

        socket=new Socket(server, port);
        socketReader=new Scanner(socket.getInputStream());
        socketWriter=new PrintWriter(socket.getOutputStream(),true);



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
    public boolean login() {
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

    /* public Map<String,String> getMailHead(int i) {
         Map<String, String> result=new HashMap<>();
         String command="top "+i+" 1";
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

     }
     */
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
        //System.out.println(result);
        for(String s:result) {
            System.out.println(s);
        }
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
    }

    /*
     * 或取邮件列表List<Mail>
     *
     */
    public List<Mail> getMailList() throws UnsupportedEncodingException, ParseException{
        //按照先后顺序读取邮件
        int num=getMailNum();
        ArrayList<Mail> list=new ArrayList<>();
        for(int i=num;i>0;i--) {
            list.add(geti_thMail(i));
        }

        return list;



    }
    /*
     * 或取第i封邮件
     *
     */
    public Mail geti_thMail(int i) throws UnsupportedEncodingException, ParseException {
        Vector<String> result=getContent(i);
        String uid=getID(i);
        String fromAddr="";
        String toAddr="";
        String date="";
        String content="";
        String subject="";
        Date d=new Date();
        boolean isContent=false;
        Mail mail=new Mail();
        for(int j=0;j<result.size();j++) {
            String temp=result.get(j);
            if(temp.startsWith("From")) {
                fromAddr=temp.split(":")[1].trim();
            }
            else if(temp.startsWith("To")) {
                toAddr=temp.split(":")[1].trim();
            }
            else if(temp.startsWith("Subject")) {
                subject=temp.split(":")[1].trim();
            }
            else if(temp.startsWith("Date")) {
                date=temp;
            }
            if(temp.startsWith("Content-Transfer-Encoding")&&result.get(j+1).equals("")) {
                isContent=true;
                continue;
            }
            else if(temp.equals("")){
                if(result.get(j+1).startsWith("------=")) {
                    continue;
                }
                else {
                    isContent=true;
                }
            }
            if(isContent) {
                if(temp.startsWith("------=")) {
                    isContent=false;
                    continue;
                }
                content+=temp;
            }
            else if(temp.startsWith("------=")) {
                continue;
            }
        }
        mail.setContent(Base64Util.fromBase64(content));
        mail.setFrom(fromAddr);
        mail.setTo(toAddr);
        mail.setSubject(subject);
        System.out.println("");
        //System.out.println(Base64Util.fromBase64(content));
        System.out.println(Base64Util.fromBase64(fromAddr));
        System.out.println(toAddr);
        System.out.println(subject);
        System.out.println(date);
        if(!date.equals("")) {
            String[] times=date.split(" ");
            String year=times[4];
            String time=times[5];
            String abbreMonth=getNumberOfMonth(times[3]);
            String day=times[2];
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String parseStr=year+"-"+abbreMonth+"-"+day+" "+time;
            d=df.parse(parseStr);
        }
        System.out.println(d);
        mail.setStime(d);
        return mail;
    }

    /*
     * 获取第i封邮件的uid
     *
     */
    public String getID(int i) {
        String command="uidl "+i;
        sendCommand(command);
        String result=socketReader.nextLine();
        if(!result.contains("+OK"))
            return "";
        else {
            return result.split(" ")[2];
        }

    }
    //月份的转换
    public String getNumberOfMonth(String month) {
        String lowMon=month.toLowerCase();
        if(lowMon.startsWith("jan")) {
            return "01";
        }
        if(lowMon.startsWith("feb")) {
            return "02";
        }
        if(lowMon.startsWith("mar")) {
            return "03";
        }
        if(lowMon.startsWith("apr")) {
            return "04";
        }
        if(lowMon.startsWith("may")) {
            return "05";
        }
        if(lowMon.startsWith("jun")) {
            return "06";
        }
        if(lowMon.startsWith("jul")) {
            return "07";
        }
        if(lowMon.startsWith("aug")) {
            return "08";
        }
        if(lowMon.startsWith("sep")) {
            return "09";
        }
        if(lowMon.startsWith("oct")) {
            return "10";
        }
        if(lowMon.startsWith("nov")) {
            return "11";
        }
        else {
            return "12";
        }

    }





    //测试用代码

    public static void main(String[] args) throws Exception {
        POPUtil popUtil=new POPUtil("pop.163.com", "13197389627","Wc123456789");
        popUtil.login();
        System.out.println(popUtil.getMailNum());
        //popUtil.getContent(2);
        //popUtil.deleteItem(2);
        popUtil.geti_thMail(2);


    }



}
