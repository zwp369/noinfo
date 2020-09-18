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
 * @Description:
 * @Date: Create in 14:25 2020-07-28
 *
 *
 */


@Epic("名单管理-测试用例")
@Feature("客户名单管理")
public class CustomerListTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(com.g7s.ics.testcase.ListManagement.CustomerListTest.class);

    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String createCustomerPaht = bundle.getString("createCustomerPaht");
    String updateCustomerPaht = bundle.getString("updateCustomerPaht");
    String detailCustomerPaht = bundle.getString("detailCustomerPaht");
    String pagingCustomerPaht = bundle.getString("pagingCustomerPaht");
    String name ="名称"+ FakerUtils.getTimeStamp();

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
    @Description("调用接口：/v1/blacklist/customer/create， 各创建一次创建客户名单: 1 正常，2灰 ，3黑 -- 相当于备注")
    @DisplayName("各创建一次创建客户名单: 1 正常，2灰 ，3黑")
    @CsvFileSource(resources = "/data/VechicleTypeId/Type.csv",numLinesToSkip = 2)
    @ParameterizedTest()
    public void createCustomerList(int typeList, String remarke){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("name",name);
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",typeList);
        data.put("role",1);//1是投保人
        Response createResponse = ListManagementApi.createCustomer(data,createCustomerPaht);
        assertEquals(name,createResponse.path("data.name"));

    }




    //同时修改状态
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/customer/update，修改客户名单类型，从灰到黑")
    @DisplayName("修改客户名单类型，从灰到黑，断言是否是修改后的数据")
    public void updateCustomerList(){

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


    }


    //单个数据查询
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/customer/detail，新增一个客户并查询出详细信息")
    @DisplayName("新增一个客户并查询出详细信息")
    public void detailCustomer(){
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("createUserId",123);
        data.put("createUsername","zwp别名");
        data.put("name",name);
        //0 全部，1 正常 2 灰 3 黑
        data.put("type",2);
        data.put("role",1);//1是投保人
        Response createResponse = ListManagementApi.createCustomer(data,createCustomerPaht);
        String blacklistId = createResponse.then().extract().path("data.blacklistId");
        HashMap<String,Object> detailData = new HashMap<String, Object>();
        detailData.put("blacklistId",blacklistId);

        Response detailResponse = ListManagementApi.detailCustomer(detailData,detailCustomerPaht);
        assertEquals(detailData.get("blacklistId"),detailResponse.path("data.blacklistId"));

    }
    // 列表页面数据查询
    //@Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Description("调用接口：/v1/blacklist/customer/paging，分别查询各种类型")
    @DisplayName("查询第1页到数据，和指定到给灰名单，并断言当前页数和列表数据到状态是灰名单")
    @CsvFileSource(resources = "/data/VechicleTypeId/Type.csv",numLinesToSkip = 2)
    @ParameterizedTest()
    public void pagingCustomer(int typeList,String remarke){
        HashMap<String,Object> pageDate = new HashMap<String, Object>();
        pageDate.put("current",1);
        pageDate.put("pageSize",10);
        //pageDate.put("plateNo","");
        //0 全部，1 正常 2 灰 3 黑
        pageDate.put("type",typeList);
        //pageDate.put("vin",vin);
        Response pagingResponse = ListManagementApi.pagingCustomer(pageDate,pagingCustomerPaht);
        assertAll(
                ()->assertEquals(pageDate.get("type"),pagingResponse.path("data.records.type[0]")),
                ()->assertEquals(pageDate.get("current"),pagingResponse.path("data.current")),
                ()->assertEquals(pageDate.get("pageSize"),pagingResponse.path("data.size"))

        );



    }
    @Test
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Tag("a")
    @Description("调用接口：/v1/blacklist/customer/paging，查询所有的客户数据")
    @DisplayName("验证所有数据的查询，每页20条数据")
    public void pagingAllCustomer(){
        HashMap<String,Object> pageDate = new HashMap<String, Object>();
        pageDate.put("current",1);
        pageDate.put("pageSize",20);
        //0 全部，1 正常 2 灰 3 黑
        pageDate.put("type",0);

        Response pagingResponse = ListManagementApi.pagingCustomer(pageDate,pagingCustomerPaht);

        assertAll(
                //()->assertEquals(pageDate.get("type"),pagingResponse.path("data.records.type[0]")),
                ()->assertEquals(pageDate.get("current"),pagingResponse.path("data.current")),
                ()->assertEquals(pageDate.get("pageSize"),pagingResponse.path("data.size"))

        );



    }


}
