package com.network.entity;

import com.network.mail.POPUtil;
import com.network.mail.SMTPUtil;

import java.sql.*;

public class LoginVerify{


    /*
     *     如果数据库中存在该用户名
     *     通过数据库判断用户名/密码是否匹配
     */
    public boolean userVerify(Connection conn, String s1, String s2) throws SQLException {
        Statement state = conn.createStatement();
        ResultSet rs = null;
        boolean isValid;
        String passwd="";
        String inputName = replace(s1);
        String inputPasswd = replace(s2);
        String sql = "SELECT * FROM users WHERE username='" +inputName+"'";
        rs = state.executeQuery(sql);
        if(!rs.next()){
            isValid = false;
        }else {
            passwd=rs.getString("password");
            if(inputPasswd.equals(passwd)) {

                isValid = true;
            }else {
                isValid=false;
            }
        }
        return  isValid;
    }

    /*
     *     如果数据库中不存在该用户名
     *     通过pop3及smtp登录验证是否可以成功连接服务器
     *
     *     若二者均连接成功，作为新用户添加至本地数据表中
     */
    public boolean popVerify(String name, String passwd, String server, int port)throws Exception{
        POPUtil popUtil = new POPUtil(server,port);
        return (popUtil.login(name, passwd));
    }

    public boolean smtpVerify(String name, String passwd, String server, int port)throws Exception{
        SMTPUtil smtpUtil = new SMTPUtil(server, port);
        return (smtpUtil.login(name,passwd));
    }

    public int addNewUserAndLogin(Connection conn, String name, String pswd, String pop, String smtp)throws Exception{
        PreparedStatement pstmt = null;
        int i=0;//status
        String passwd="";
        String inputName = replace(name);
        String inputPasswd = replace(pswd);
        String inputPOP = replace(pop);
        String inputSMTP = replace(smtp);

        String sql = "INSERT INTO users(username,password,smtp,pop3,isvalid)VALUES(?,?,?,?,?);";
        boolean isPOPValid = popVerify(inputName, inputPasswd, inputPOP, 110);
        boolean isSMTPValid = smtpVerify(inputName, inputPasswd, inputSMTP, 25);
        if(isPOPValid&&isSMTPValid) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, inputName);
                pstmt.setString(2, inputPasswd);
                pstmt.setString(3, inputSMTP);
                pstmt.setString(4, inputPOP);
                pstmt.setInt(5,1);
                i = pstmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                    if (pstmt != null)
                        pstmt.close();
                } catch (SQLException e) {
                }
            }
        }

        return i;

    }

    public String replace(String s) {
        try {
            if ((s == null) || (s.equals("")))
                return "";

            StringBuffer stringbuffer = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '"':
                        stringbuffer.append("&quot;");
                        break;
                    case '\'':
                        stringbuffer.append("&#039;");
                        break;
                    case '|':
                        stringbuffer.append("");
                        break;
                    case '&':
                        stringbuffer.append("&amp;");
                        break;
                    case '<':
                        stringbuffer.append("&lt;");
                        break;
                    case '>':
                        stringbuffer.append("&gt;");
                        break;
                    default:
                        stringbuffer.append(c);
                }
            }
            return stringbuffer.toString().trim();
        } catch (Exception e) {
        }
        return "";
    }



}
