//package com.g7s.ics.handler;
//
//import com.alibaba.fastjson.JSONObject;
//
///**
// * @Author: zhangwenping
// * @Description:
// * @Date: Create in 19:34 2020-09-08
// */
//
//public class CraeatOrderRepalce extends AbsDataHandler {
//    @Override
//    public void handler(JSONObject jsonObject, String key) {
//        if ("code".equals(key)) {
//            jsonObject.put("code", "2020-08-03");
//        }
//    }
//
//        @Override
//        public String filePath() {
//            return "/Users/zhangwenping/Documents/make_data/ICS_API_Test/src/main/resources/muti/CreateOrder.txt";
//        }
//
//
//}