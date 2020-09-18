package com.g7s.ics.api;

import com.g7s.ics.RequestClasse;
import com.g7s.ics.utils.FakerUtils;
import io.restassured.response.Response;


/**
 * @Author: zhangwenping
 * @Description:  为了做用例之间的解耦，实现单独用例执行
 * @Date: Create in 16:50 2020-07-24
 */
public class ListManagementApi {

// 车辆lits调用
    public static  Response createVechicle(Object data, String basePath){

        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath), FakerUtils.getbasehost(basePath));


    }
    public static  Response updateVechicle(Object data, String basePath){
        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));


    }
    public static  Response detailVechicle(Object data, String basePath){
        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));

    }
    public static  Response pagingVechicle(Object data, String basePath){
        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));

    }

    /** 以下是客户list
     *
     * @param data
     * @param basePath
     * @return
     */

    public static  Response createCustomer(Object data, String basePath){

        return  RequestClasse.Request(data,FakerUtils.getheader(), FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));


    }
    public static  Response updateCustomer(Object data, String basePath){
        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));


    }
    public static  Response detailCustomer(Object data, String basePath){
        return  RequestClasse.Request(data,FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));

    }
    public static  Response pagingCustomer(Object data, String basePath) {
        return RequestClasse.Request(data, FakerUtils.getheader(), FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));
    }

    /** 以下是查看日志
     *
     * @param data
     * @param basePath
     * @return
     */


    public static  Response getLog(Object data, String basePath) {

        return RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParams(basePath),FakerUtils.getbasehost(basePath));
    }

}
