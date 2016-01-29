package com.ys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys.model.TrainUser;

public class TrainUserUtils {

    private static final Logger logger = LoggerFactory.getLogger(TrainUserUtils.class);
    
    private static final String FILEPATH ="/home/target/";
    private static final String FILENAME = "vie.arial.exp";
    private static final int FILENUMS = 6762;
    /**
     * Reading properties
     * @param path
     * @param fileName
     * @return TrainUser
     * @author Xiaoming
     * @date 2016年1月26日 下午2:21:38
     */
    public static TrainUser readingProperties(String path, String fileName) {
        TrainUser trainUser = new TrainUser();
        File file = new File(path);
        if(file.isFile() && file.exists()) {
            try {
                InputStreamReader input = new InputStreamReader(new FileInputStream(file),"UTF-8");
                BufferedReader reader = new BufferedReader(input);
                String lineText = null;
                while((lineText = reader.readLine()) != null) {
                   String[] infoArrays = disposeString(lineText);
                   trainUser.setBoxTxt(infoArrays[0]);
                   trainUser.setBoxX(infoArrays[1]);
                   trainUser.setBoxY(infoArrays[2]);
                   trainUser.setBoxW(infoArrays[3]);
                   trainUser.setBoxH(infoArrays[4]);
                   trainUser.setBoxB(infoArrays[5]);
                   trainUser.setCheckStatus("0");
                   trainUser.setImg(fileName);
                }
                reader.close();
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
        logger.debug("TrainUser :{} ", trainUser.toString());
        if(trainUser != null && trainUser.getImg() != null ) {
            return trainUser;
        } else {
            return null;
        }
        
    }
    private static String[] disposeString(String lineText) {
        // TODO Auto-generated method stub
        String[] infoArray = lineText.split(" ");
        return infoArray;
    }
    /**
     * Get properties information
     * @return
     * @author Xiaoming
     * @date 2016年1月26日 下午2:21:15
     */
    public static List<TrainUser> getProperInfo() {
        List<TrainUser> list = new ArrayList<>();
        for (int i = 0; i <= FILENUMS; i++ ) {
            // tif picture name
            String fileName = FILENAME + i + ".jpg"; 
            // box path
            String path = FILEPATH + FILENAME + i + ".box";
            if ((readingProperties(path, fileName)) != null  ) {
                list.add(readingProperties(path, fileName));
            }
        }
        logger.debug("List<TrainUser> size :{} ", list.size());
        return list;
    }
}
