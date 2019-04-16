package com.network.project.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class Base64Util {
    public static String toBase64(String source) throws UnsupportedEncodingException {
        if(source==null)
            return null;
        BASE64Encoder encoder=new BASE64Encoder();
        String result=encoder.encode(source.getBytes());
        return result;
    }
    public static String fromBase64(String source) {
        if(source==null)
            return null;
        BASE64Decoder decoder=new BASE64Decoder();
        try {
            byte[] result=decoder.decodeBuffer(source);
            return new String(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    
}
