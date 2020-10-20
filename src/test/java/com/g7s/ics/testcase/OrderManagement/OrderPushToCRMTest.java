package com.g7s.ics.testcase.OrderManagement;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 15:36 2020-09-17
 */
@Epic("保单管理-测试用例")
@Feature("保单录入")
public class OrderPushToCRMTest {
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");
    String orderGetInfoPath = bundle.getString("orderGetInfoPath");
    String orderCrmCommitPath = bundle.getString("orderCrmCommitPath");




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
    @Description("创建单商业险，查询orderNo和proposalNo,，组装成CRM需的参数，推送给CRM")
    @DisplayName("推送单商业险到CRM")
    void PushRisk2ToCRM(){

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
        //创建单商业险保单
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");


        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);
        String orderNo=OrderInfoResponse.path("data.orderNo");
        String proposalNo=OrderInfoResponse.path("data.proposalNo");

        // 组装数据，推送CRM
        HashMap<String,Object> CRMData = new HashMap<String, Object>();
        CRMData.put("inputTime",inputTime);
        CRMData.put("policyNos",policyNo);
        CRMData.put("orderId",orderId);
        CRMData.put("orderNo",orderNo);
        CRMData.put("proposalNo",proposalNo);
        CRMData.put("securityMode",110);
        CRMData.put("devicePackageType",1);
        CRMData.put("deviceDeduct",1);
        String CRMBody = FakerUtils.template("muti/PushToCRM.json",CRMData);


        Response pushRespone= OrderManagementApi.OrderCrmCommit(CRMBody, orderCrmCommitPath);
        assertEquals(orderId,pushRespone.path("data.orderId").toString());


    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建单交强险，查询orderNo和proposalNo,，组装成CRM需的参数，推送给CRM")
    @DisplayName("推送单交强险到CRM")
    void PushRisk1ToCRM(){

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
        //创建单险保单
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");


        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);
        String orderNo=OrderInfoResponse.path("data.orderNo");
        String proposalNo=OrderInfoResponse.path("data.proposalNo");

        // 组装数据，推送CRM
        HashMap<String,Object> CRMData = new HashMap<String, Object>();
        CRMData.put("inputTime",inputTime);
        CRMData.put("policyNos",policyNo);
        CRMData.put("orderId",orderId);
        CRMData.put("orderNo",orderNo);
        CRMData.put("proposalNo",proposalNo);
        CRMData.put("securityMode",100);
        CRMData.put("devicePackageType",0);
        CRMData.put("deviceDeduct",0);
        String CRMBody = FakerUtils.template("muti/PushToCRM.json",CRMData);

        Response pushRespone= OrderManagementApi.OrderCrmCommit(CRMBody, orderCrmCommitPath);
        assertEquals(orderId,pushRespone.path("data.orderId").toString());



    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同保，查询orderNo和proposalNo,，组装成CRM需的参数，推送给CRM")
    @DisplayName("推送交商同保到CRM")
    void PushRisk3ToCRM(){

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

        String orderId=responseDataj.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);


        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);
        String orderNo=OrderInfoResponse.path("data.orderNo");
        String proposalNo=OrderInfoResponse.path("data.proposalNo");

        // 组装数据，推送CRM
        HashMap<String,Object> CRMData = new HashMap<String, Object>();
        CRMData.put("inputTime",inputTime);
        CRMData.put("policyNos",policyNos);
        CRMData.put("policyNoj",policyNoj);
        CRMData.put("orderId",orderId);
        CRMData.put("orderNo",orderNo);
        CRMData.put("proposalNo",proposalNo);
        CRMData.put("securityMode",100);
        CRMData.put("devicePackageType",0);
        CRMData.put("deviceDeduct",0);
        String CRMBody = FakerUtils.template("muti/PushToCRM.json",CRMData);


        Response pushRespone= OrderManagementApi.OrderCrmCommit(CRMBody, orderCrmCommitPath);
        assertEquals(orderId,pushRespone.path("data.orderId").toString());
        //veriyData(pushRespone);


    }

}
