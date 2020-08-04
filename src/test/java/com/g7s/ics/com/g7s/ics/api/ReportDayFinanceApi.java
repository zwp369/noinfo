package com.g7s.ics.com.g7s.ics.api;

import com.g7s.ics.RequestClasse;
import com.g7s.ics.utils.FakerUtils;
import io.restassured.response.Response;

import java.util.HashMap;

/**
 * @Author: zhangwenping
 * @Description: 提供接口给业务方，使用inward调用方式
 * @Date: Create in 11:53 2020-08-04
 */
public class ReportDayFinanceApi {
    public static Response getDayReport(HashMap data, String basePath){

        return  RequestClasse.Request(data, FakerUtils.getheader(),FakerUtils.getQueryParamsInward(basePath), FakerUtils.getbasehost(basePath));


    }
}
