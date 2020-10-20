package com.g7s.ics.testcase.OrderManagement;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:42 2020-09-08
 */
@Epic("保单管理-测试用例")
@Feature("保单补录")
public class OrderBuLuTest {
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");


    public  String TimeStamp ;
    public String inputTime;
    public String startDate;
    public String endDate;
    public String policyNo;
    public String policyNoj;
    public String policyNos;
    public String vin;

    @BeforeEach
    void BeforeEach(){
        TimeStamp =  FakerUtils.getTimeStamp();
        inputTime = FakerUtils.getDateToString(TimeStamp,true);
        startDate= FakerUtils.getDateToString((Long.valueOf(TimeStamp)+1000)+"",false);
        endDate= FakerUtils.getDateToString((Long.valueOf(TimeStamp)+9000)+"" ,false);
        policyNo="BDHA"+TimeStamp;
        policyNoj="BDHAj"+TimeStamp;
        policyNos="BDHAs"+TimeStamp;
        vin="CHJA"+TimeStamp;

    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("调用接口：/v1/report/day/finance，创建中银山西商业险保单，新安装-有预约地址信息")
    @DisplayName("创建商业险保单")
    @Story("新增")
    void CreateshangyeRisk(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",2);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        data.put("deviceDeduct",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response responseData= OrderManagementApi.CreateOrder(body, orderCreatePath);
        FakerUtils.veriyData(responseData);

    }


    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("调用接口：/v1/report/day/finance，创建中银山西交强险保单")
    @DisplayName("创建交强险保单")
    @Story("新增")
    void CreatejiaoqiangRisk(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",1);
        data.put("securityMode",100);
        data.put("devicePackage",0);
        data.put("deviceDeduct",0);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response responseData= OrderManagementApi.CreateOrder(body, orderCreatePath);
        FakerUtils.veriyData(responseData);


    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("调用接口：/v1/report/day/finance，创建中银山西交商同保的保单")
    @DisplayName("创建交商同保的保单")
    @Story("新增")
    void CreatejiaoshangRisk(){

        //创建交强险
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",100);
        data.put("devicePackage",0);
        data.put("deviceDeduct",0);
        String bodyj = FakerUtils.template("muti/Createorder.json",data);
        Response responseDataj= OrderManagementApi.CreateOrder(bodyj, orderCreatePath);
        FakerUtils.veriyData(responseDataj);

        //创建商业险
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);
       FakerUtils.veriyData(responseDatas);


    }
}
