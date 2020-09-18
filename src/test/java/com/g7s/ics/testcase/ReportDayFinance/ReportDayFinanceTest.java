package com.g7s.ics.testcase.ReportDayFinance;


import com.g7s.ics.api.ReportDayFinanceApi;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description: 获取财务日报信息
 * @Date: Create in 11:51 2020-08-04
 */


@Epic("财务日报-测试用例")
@Feature("日报查询")
public class ReportDayFinanceTest {
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String FinanceDayReportPath = bundle.getString("FinanceDayReportPath");



    @Test
    @Description("调用接口：/inward/v1/report/day/finance，查询固定值，2020-08-01 的财务日报")
    @DisplayName("查询固定值，2020-08-01 的财务日报，验证接口返回数据正常")
    public void getFinanceDayReport(){
        HashMap<String,Object> Date = new HashMap<String, Object>();
        Date.put("id",0);
        Date.put("pageSize",100);
        //0 全部，1 正常 2 灰 3 黑
        Date.put("reportDate","2020-08-01");

        Response response = ReportDayFinanceApi.getDayReport(Date,FinanceDayReportPath);
        assertAll(
                //()->assertEquals(pageDate.get("type"),response.path("data.records.type[0]")),
                ()->assertEquals("0",response.path("code").toString()),
                ()->assertEquals("成功",response.path("sub_msg"))

    );}

}
