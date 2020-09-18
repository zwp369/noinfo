package com.g7s.ics.handler;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 19:58 2020-09-08
 */
import com.alibaba.fastjson.JSONObject;

public interface DataHandler {
    void handler(JSONObject jsonObject, String key);

    String filePath();

    //String topic();

    void service();

}