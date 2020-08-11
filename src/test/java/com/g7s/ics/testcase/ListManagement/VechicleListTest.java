package com.g7s.ics.testcase.ListManagement;

import com.g7s.ics.api.ListManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description: 名单管理，增加，修改内容，修改名单，查询
 * @Date: Create in 14:23 2020-07-24
 */

@Epic("名单管理-测试用例")
@Feature("车辆名单管理")
public class VechicleListTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(com.g7s.ics.testcase.ListManagement.VechicleListTest.class);
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String createVechiclePaht = bundle.getString("createVechiclePaht");
    String updateVechiclePaht = bundle.getString("updateVechiclePaht");
    String detailVechiclePaht = bundle.getString("detailVechiclePaht");
    String pagingVechiclePaht = bundle.getString("pagingVechiclePaht");

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

    //创建车辆名单
    //@Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/vehicle/create， 各创建一次创建车辆名单: 1 正常，2灰 ，3黑 -- 相当于备注")
    @DisplayName("各创建一次创建车辆名单: 1 正常，2灰 ，3黑")
    @CsvFileSource(resources = "/data/VechicleTypeId/Type.csv",numLinesToSkip = 2)
    @ParameterizedTest()
    public void createVehicleList(int typeList, String remarke){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("plateNo","AA00001");
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",typeList);
        data.put("vin",vin);
        Response createResponse = ListManagementApi.createVechicle(data,createVechiclePaht);
        assertEquals(vin,createResponse.path("data.vin"));

    }




    //同时修改状态和车牌号
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/vehicle/update，修改车辆名单类型，从灰到黑，并且修车牌号，断言是否是修改后的数据")
    @DisplayName("修改车辆名单类型，从灰到黑，并且修车牌号，断言是否是修改后的数据")
    public void updateVehicleList(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("plateNo","AA00001");
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",2);
        data.put("vin",vin);
        Response createResponse = ListManagementApi.createVechicle(data,createVechiclePaht);
        String blacklistId = createResponse.then().extract().path("data.blacklistId");

        HashMap<String,Object> updateData = new HashMap<String, Object>();
        updateData.put("updateUserId",123);
        updateData.put("updateUsername","zwp别名");
        updateData.put("plateNo","AA00002");
        //0 全部，1 正常 2 灰 3 黑
        updateData.put("type",3);
        updateData.put("vin",vin);
        updateData.put("blacklistId",blacklistId);
        Response updateResponse = ListManagementApi.updateVechicle(updateData,updateVechiclePaht);
        assertAll(
                ()->assertEquals(updateData.get("plateNo"),updateResponse.path("data.plateNo")),
                ()->assertEquals(updateData.get("type"),updateResponse.path("data.type"))


        );


    }


    //单个数据查询
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/vehicle/detail，新增一个车辆并查询出详细信息")
    @DisplayName("新增一个车辆并查询出详细信息")
    public void detailVehicle(){
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("plateNo","AA00001");
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",2);
        data.put("vin",vin);
        Response createResponse = ListManagementApi.createVechicle(data,createVechiclePaht);
        String blacklistId = createResponse.then().extract().path("data.blacklistId");
        HashMap<String,Object> detailData = new HashMap<String, Object>();
        detailData.put("blacklistId",blacklistId);

        Response detailResponse = ListManagementApi.detailVechicle(detailData,detailVechiclePaht);
        assertEquals(detailData.get("blacklistId"),detailResponse.path("data.blacklistId"));

    }
// 列表页面数据查询
    //@Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/vehicle/paging，分别查询各种类型")
    @DisplayName("查询第1页到数据，和指定到给灰名单，并断言当前页数和列表数据到状态是灰名单")
    @CsvFileSource(resources = "/data/VechicleTypeId/Type.csv",numLinesToSkip = 2)
    @ParameterizedTest()
    public void pagingVehicle(int typeList,String remarke){
        HashMap<String,Object> pageDate = new HashMap<String, Object>();
        pageDate.put("current",1);
        pageDate.put("pageSize",10);
        //pageDate.put("plateNo","");
        //0 全部，1 正常 2 灰 3 黑
        pageDate.put("type",typeList);
        //pageDate.put("vin",vin);
        Response pagingResponse = ListManagementApi.pagingVechicle(pageDate,pagingVechiclePaht);
        assertAll(
                ()->assertEquals(pageDate.get("type"),pagingResponse.path("data.records.type[0]")),
                ()->assertEquals(pageDate.get("current"),pagingResponse.path("data.current")),
                ()->assertEquals(pageDate.get("pageSize"),pagingResponse.path("data.size"))

        );



    }
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/vehicle/paging，查询所有的车辆数据")
    @DisplayName("验证所有数据的查询，每页20条数据")
    public void pagingAllVehicle(){
        HashMap<String,Object> pageDate = new HashMap<String, Object>();
        pageDate.put("current",1);
        pageDate.put("pageSize",20);
        //pageDate.put("plateNo","");
        //0 全部，1 正常 2 灰 3 黑
        pageDate.put("type",0);
        //pageDate.put("vin",vin);
        Response pagingResponse = ListManagementApi.pagingVechicle(pageDate,pagingVechiclePaht);
        assertAll(
                //()->assertEquals(pageDate.get("type"),pagingResponse.path("data.records.type[0]")),
                ()->assertEquals(pageDate.get("current"),pagingResponse.path("data.current")),
                ()->assertEquals(pageDate.get("pageSize"),pagingResponse.path("data.size"))

        );



    }


}
