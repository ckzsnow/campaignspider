package com.ys.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
    
private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    
    public static String generateMD5(String input) {
        StringBuffer hexString = new StringBuffer();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
             sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                     if(hex.length()==1) hexString.append('0');
                     hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.debug(e.toString());
        }
        return hexString.toString();
    }
    
    public static List<String> generateDateList(int dayAmount) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        for (int i = 1; i <= dayAmount; i++) {
            Date date = new Date(c.getTimeInMillis());
            dateList.add(formatDate.format(date));
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }
}
