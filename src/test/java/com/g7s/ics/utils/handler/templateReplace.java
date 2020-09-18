package com.g7s.ics.handler;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @Author: zhangwenping
 * @Description: 这种方式被废弃，由于 数据中的字段无法替换，加上数组中的key重复，会造成所有相同key的内容都被替换，改用模版技术替换
 * @Date: Create in 14:26 2020-09-08
 */
public class templateReplace {

    //step1 读取文件

    public String service(String filePath) {
        JSONObject jsonObject=null;
        try {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            for (String line = br2.readLine(); line != null; line = br2.readLine()) {
                jsonObject = JSONObject.parseObject(line);
                replace(jsonObject);

               // getProducer().send(topic(), JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue), getImei());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  jsonObject.toJSONString();
    }

    @Test
    void Test(){
        System.out.println(service("/Users/zhangwenping/Documents/make_data/ICS_API_Test/src/main/resources/muti/inward_v1_report_day_finance.txt"));

    }


    //step2 遍历找到需要替换的key，调用handler替换value
   void replace(JSONObject jsonObject) {

        Set<String> keys = jsonObject.keySet();
        for (String key : keys) {
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONObject) {
                replace((JSONObject) obj);
            } else {
                handler(jsonObject, key);
            }
        }

    }


    //step3 handde
//    public static void handler(JSONObject jsonObject, String key) {
//
//        if ("code".equals(key)) {
//            jsonObject.put("code", "2020-08-03");
//        }
//
//
//    }
}


