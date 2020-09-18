package com.g7s.ics.handler;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:06 2020-09-08
 */
public abstract class AbsDataHandler implements DataHandler {


    private String jsonStr;

    public String getJsonStr() {
        return jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    void replace(JSONObject jsonObject, DataHandler dataHandler) {
        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONObject) {
                replace((JSONObject) obj, dataHandler);
            } else {
                dataHandler.handler(jsonObject, key);
            }
        }
    }



    @Override
    public void service() {
        JSONObject jsonObject=null;
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath())));
            for (String line = br2.readLine(); line != null; line = br2.readLine()) {
                jsonObject = JSONObject.parseObject(line);
                replace(jsonObject,this);
                jsonStr=jsonObject.toJSONString();
                // getProducer().send(topic(), JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue), getImei());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



}
