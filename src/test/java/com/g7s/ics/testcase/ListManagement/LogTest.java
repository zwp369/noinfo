package com.g7s.ics.testcase.ListManagement;

import com.g7s.ics.api.ListManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.api.Test;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 14:56 2020-07-28
 */

@Epic("名单管理-测试用例")
@Feature("日志查询")
public class LogTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(com.g7s.ics.testcase.ListManagement.LogTest.class);
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String logPath = bundle.getString("logPath");
    String createCustomerPaht = bundle.getString("createCustomerPaht");
    String updateCustomerPaht = bundle.getString("updateCustomerPaht");
    String createVechiclePaht = bundle.getString("createVechiclePaht");
    String updateVechiclePaht = bundle.getString("updateVechiclePaht");

    String name ="名称"+ FakerUtils.getTimeStamp();
    String vin ="cjh12"+ FakerUtils.getTimeStamp();


    @AfterAll
    public static void cleanForSQL(){
        String sqlVehicle ="DELETE  FROM ics_blacklist_vehicle WHERE create_username='zwp别名'";
        String sqlCustomer ="DELETE  FROM ics_blacklist_customer WHERE create_username='zwp别名'";
        log.info("清理数据开始");
        FakerUtils.cleanMysqlData(sqlVehicle);
        FakerUtils.cleanMysqlData(sqlCustomer);
        log.info("清理数据结束");
    }

    @Test
    @Tag("1")
    @DisplayName("新增车辆后，变更状态，在获取变更日志")
    @Description("车辆变更日志查询")
    public void VehicleLog() {

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("createUserId", 123);
        data.put("createUsername", "zwp别名");
        data.put("plateNo", "AA00001");
        //0 全部，1 正常 2 灰 3 黑
        data.put("type", 2);
        data.put("vin", vin);
        Response createResponse = ListManagementApi.createVechicle(data, createVechiclePaht);
        String blacklistId = createResponse.then().extract().path("data.blacklistId");

        HashMap<String, Object> updateData = new HashMap<String, Object>();
        updateData.put("updateUserId", 123);
        updateData.put("updateUsername", "zwp别名");
        updateData.put("plateNo", "AA00002");
        //0 全部，1 正常 2 灰 3 黑
        updateData.put("type", 3);
        updateData.put("vin", vin);
        updateData.put("blacklistId", blacklistId);
        Response updateResponse = ListManagementApi.updateVechicle(updateData, updateVechiclePaht);
        assertAll(
                () -> assertEquals(updateData.get("plateNo"), updateResponse.path("data.plateNo")),
                () -> assertEquals(updateData.get("type"), updateResponse.path("data.type"))


        );
        HashMap<String, Object> logData = new HashMap<String, Object>();
        logData.put("blacklistTab", "vehicle");
        logData.put("blacklistTabId", blacklistId);


        Response logResponse = ListManagementApi.getLog(logData, logPath);
        assertEquals("0", logResponse.path("code").toString());


    }

    @Test
    @DisplayName("新增客户后，变更状态，在获取变更日志")
    @Description("客户变更日志查询")
    public void CustomerLog() {


        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("name",name);
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",2);
        data.put("role",1);//1是投保人
        Response createResponse = ListManagementApi.createCustomer(data,createCustomerPaht);
        String blacklistId = createResponse.then().extract().path("data.blacklistId");

        HashMap<String,Object> updateData = new HashMap<String, Object>();
        updateData.put("blacklistId",blacklistId);
        updateData.put("updateUserId",123);
        updateData.put("updateUsername","zwp别名");
        updateData.put("name",name);
        //0 全部，1 正常 2 灰 3 黑
        updateData.put("type",3);
        updateData.put("role",1);//1是投保人
        Response updateResponse = ListManagementApi.updateCustomer(updateData,updateCustomerPaht);
        assertAll(
                ()->assertEquals(updateData.get("name"),updateResponse.path("data.name")),
                ()->assertEquals(updateData.get("type"),updateResponse.path("data.type"))


        );

        HashMap<String, Object> logData = new HashMap<String, Object>();
        logData.put("blacklistTab", "customer");
        logData.put("blacklistTabId", blacklistId);


        Response logResponse = ListManagementApi.getLog(logData, logPath);
        assertEquals("0", logResponse.path("code").toString());


    }


}
