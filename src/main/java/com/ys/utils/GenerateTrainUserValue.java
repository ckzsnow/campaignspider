package com.ys.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ys.model.TrainUserModel;

public class GenerateTrainUserValue {
    private static final Logger logger = LoggerFactory.getLogger(GenerateTrainUserValue.class);
    
    public static Map<String, String> getModelValue(TrainUserModel trainUserModel) {
        Map<String, String> retMap = new HashMap<>();
            try {
                for (int i = 0; i < 10; i ++) {
                    String idString = "getDataId" + i ;
                    String txtString = "getInputBtxt" + i ;
                Method idMethod = 
                        trainUserModel.getClass().getDeclaredMethod(idString,null);
                Method txtMethod = 
                        trainUserModel.getClass().getDeclaredMethod(txtString,null);
                String  idResult = String.valueOf(idMethod.invoke(trainUserModel, new Object[]{}));
                String  txtResult = String.valueOf(txtMethod.invoke(trainUserModel, new Object[]{}));
                retMap.put("DataId" + i, idResult);
                retMap.put("InputBtxt" + i, txtResult);
                }
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        return retMap;
    }

}
