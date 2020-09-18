package com.g7s.ics.api;

import com.g7s.ics.RequestClasse;
import com.g7s.ics.utils.FakerUtils;
import io.restassured.response.Response;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:41 2020-09-08
 */
public class OrderManagementApi {
    public static Response CreateOrder(Object data, String basePath){

        return  RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParams(basePath), FakerUtils.getbasehost(basePath));


    }


    public static Response GetOrderInfo(Object data, String basePath){

        return  RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParams(basePath), FakerUtils.getbasehost(basePath));


    }

    public static Response OrderCrmCommit(Object data, String basePath){

        return  RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParams(basePath), FakerUtils.getbasehost(basePath));


    }



    public static Response CorrectOrder(Object data, String basePath){

        return  RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParams(basePath), FakerUtils.getbasehost(basePath));


    }
}
