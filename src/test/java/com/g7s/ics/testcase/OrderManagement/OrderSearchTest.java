package com.g7s.ics.testcase.OrderManagement;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 16:34 2020-09-18
 */
@Epic("保单管理-测试用例")
@Feature("保单查询")
public class OrderSearchTest {
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String OrderPagingPath = bundle.getString("OrderPagingPath");


    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("查询保单-车架号查询,检查返回的记录不是是查询出来的记录")
    @DisplayName("查询保单-车架号查询")
    void SearchOrderForVin(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin","CHJA1600411817982");
        String body = FakerUtils.template("muti/OrderSearch.json",data);
        Response OrderResponse= OrderManagementApi.OrderSearch(body, OrderPagingPath);
        assertAll(
                () -> assertEquals("成功", OrderResponse.path("sub_msg")),
                () -> assertEquals("0", OrderResponse.path("code").toString()),
                () -> assertEquals(data.get("vin"), OrderResponse.path("data.records[0].vin").toString())

        );

    }


    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("查询保单-中银公司查询,检查返回的记录不是是查询出来的记录")
    @DisplayName("查询保单-公司查询（中银）")
    void SearchOrderForcompany(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("companyCode","BOCI");
        String body = FakerUtils.template("muti/OrderSearch.json",data);
        Response OrderResponse= OrderManagementApi.OrderSearch(body, OrderPagingPath);
        assertAll(
                () -> assertEquals("成功", OrderResponse.path("sub_msg")),
                () -> assertEquals("0", OrderResponse.path("code").toString()),
                () -> assertEquals(data.get("companyCode"), OrderResponse.path("data.records[0].company.code").toString())

        );

    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("查询保单-安保模式,检查返回的记录是不是查询出来的记录")
    @DisplayName("查询保单-安保模式（未选择安保模式）")
    void SearchOrderForSecurityMode(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("securityMode",-1);
        String body = FakerUtils.template("muti/OrderSearch.json",data);
        Response OrderResponse= OrderManagementApi.OrderSearch(body, OrderPagingPath);
        assertAll(
                () -> assertEquals("成功", OrderResponse.path("sub_msg")),
                () -> assertEquals("0", OrderResponse.path("code").toString()),
                () -> assertEquals(data.get("securityMode").toString(), OrderResponse.path("data.records[0].securityMode.code").toString())

        );

    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("查询保单-组合查询")
    @DisplayName("查询保单-组合查询（公司+车架号））")
    void SearchOrderForCompanyAndVin(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("companyCode","BOCI");
        data.put("vin","CHJA1600411817982");
        String body = FakerUtils.template("muti/OrderSearch.json",data);
        Response OrderResponse= OrderManagementApi.OrderSearch(body, OrderPagingPath);
        assertAll(
                () -> assertEquals("成功", OrderResponse.path("sub_msg")),
                () -> assertEquals("0", OrderResponse.path("code").toString()),
                () -> assertEquals(data.get("companyCode"), OrderResponse.path("data.records[0].company.code").toString()),
                () -> assertEquals(data.get("vin"), OrderResponse.path("data.records[0].vin").toString())


        );

    }

}
