package com.g7s.ics.testcase.OrderManagement;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 17:21 2020-09-17
 */
@Epic("保单管理-测试用例")
@Feature("保单调整")
public class OrdermodifyTest {

    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");
    String CorrectOrderPath = bundle.getString("CorrectOrderPath");
    String orderGetInfoPath = bundle.getString("orderGetInfoPath");
    String CorrectSavePaht = bundle.getString("CorrectSavePaht");
    String CorrectDetailPath = bundle.getString("CorrectDetailPath");




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
    @Description("创建商业险-制单错误「保存」，从安保新装修改成为非安保模式，并且修改保费")
    @DisplayName("制单错误未推送过的商业险「保存」：修改安保模式")
    @Story("制单错误")
    void OrderModifyRisk2(){
        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",2);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("inputTime",inputTime);
        modifyData.put("policyNos",policyNo);
        modifyData.put("securityMode",100);
        modifyData.put("devicePackageType",0);
        modifyData.put("saveType","save");


        String modifyBody = FakerUtils.template("muti/OrderCorrectAmend.json",modifyData);

        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectOrderPath);
        FakerUtils.veriyData(modifyResponse);

        //修改后的结果验证
        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);

        assertAll(
                ()->assertEquals(modifyData.get("securityMode"),OrderInfoResponse.path("data.securityMode.code")),
                ()->assertEquals(modifyData.get("devicePackageType"),OrderInfoResponse.path("data.devicePackage.code"))



        );


    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建商业险-制单错误「保存并提交」，从安保新装修改成为非安保模式，并且修改保费")
    @DisplayName("制单错误未推送过的商业险「保存并提交」：修改安保模式")
    @Story("制单错误")
    void OrderModifyRisk2ReEntry(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",2);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("inputTime",inputTime);
        modifyData.put("policyNos",policyNo);
        modifyData.put("securityMode",100);
        modifyData.put("devicePackageType",0);
        modifyData.put("saveType","reEntry");


        String modifyBody = FakerUtils.template("muti/OrderCorrectAmend.json",modifyData);

        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectOrderPath);
        FakerUtils.veriyData(modifyResponse);

        //修改后的结果验证
        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);

        assertAll(
                ()->assertEquals(modifyData.get("securityMode"),OrderInfoResponse.path("data.securityMode.code")),
                ()->assertEquals(modifyData.get("devicePackageType"),OrderInfoResponse.path("data.devicePackage.code"))
        );

    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交强险-制单错误「保存」，修改保单号")
    @DisplayName("制单错误未推送过的交强险「保存」：修改保单号")
    @Story("制单错误")
    void OrderModifyRisk1ReEntry(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",1);
        data.put("securityMode",100);
        data.put("devicePackage",0);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        String newpolicyNo = policyNo+"1";
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("inputTime",inputTime);
        modifyData.put("policyNoj",newpolicyNo);
        modifyData.put("securityMode",100);
        modifyData.put("devicePackageType",0);
        modifyData.put("saveType","save");


        String modifyBody = FakerUtils.template("muti/OrderCorrectAmend.json",modifyData);

        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectOrderPath);
        FakerUtils.veriyData(modifyResponse);

        //修改后的结果验证
        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);

        assertAll(
                ()->assertEquals(modifyData.get("securityMode"),OrderInfoResponse.path("data.securityMode.code")),
                ()->assertEquals(modifyData.get("devicePackageType"),OrderInfoResponse.path("data.devicePackage.code")),
                ()->assertEquals(modifyData.get("policyNoj"),OrderInfoResponse.path("data.sub[0].policyNo"))

        );

    }


    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同保-制单错误「保存并提交」，从安保新装修改成为非安保模式，并且修改保费")
    @DisplayName("制单错误未推送过的交商同保「保存并提交」：修改安保模式")
    @Story("制单错误")
    void OrderModifyRisk3ReEntry(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);


        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("inputTime",inputTime);
        modifyData.put("policyNos",policyNos);
        modifyData.put("policyNoj",policyNoj);
        modifyData.put("securityMode",100);
        modifyData.put("devicePackageType",0);
        modifyData.put("saveType","reEntry");


        String modifyBody = FakerUtils.template("muti/OrderCorrectAmend.json",modifyData);

        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectOrderPath);
        FakerUtils.veriyData(modifyResponse);

        //修改后的结果验证
        //通过orderid查询orderNo和proposalNo
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, orderGetInfoPath);

        assertAll(
                ()->assertEquals(modifyData.get("securityMode"),OrderInfoResponse.path("data.securityMode.code")),
                ()->assertEquals(modifyData.get("devicePackageType"),OrderInfoResponse.path("data.devicePackage.code"))



        );
    }

    /**
     * 批改用例
     */

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建商业险-批改")
    @DisplayName("批改未推送过的商业险")
    @Story("批改")
    void OrderCorrectSaveRisk2(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",2);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNo);
        modifyData.put("correctType",1);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",2);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        String correctId=modifyResponse.path("data.correctId");
        FakerUtils.veriyData(modifyResponse);

        //批改过后的数据验证
        //通过orderid查询到批改详情
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("correctId",correctId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, CorrectDetailPath);

        assertAll(
                ()->assertEquals(modifyData.get("correctDate"),OrderInfoResponse.path("data.correctDate")),
                ()->assertEquals(modifyData.get("correctNo"),OrderInfoResponse.path("data.correctNo")),
                ()->assertEquals(modifyData.get("riskType"),OrderInfoResponse.path("data.riskType.code"))


        );


    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交强险-批改")
    @DisplayName("批改未推送过的交强险")
    @Story("批改")
    void OrderCorrectSaveRisk1(){


        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",1);
        data.put("securityMode",100);
        data.put("devicePackage",0);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNo);
        modifyData.put("correctType",1);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        String correctId=modifyResponse.path("data.correctId");
        FakerUtils.veriyData(modifyResponse);

        //批改过后的数据验证
        //通过orderid查询到批改详情
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("correctId",correctId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, CorrectDetailPath);

        assertAll(
                ()->assertEquals(modifyData.get("correctDate"),OrderInfoResponse.path("data.correctDate")),
                ()->assertEquals(modifyData.get("correctNo"),OrderInfoResponse.path("data.correctNo")),
                ()->assertEquals(modifyData.get("riskType"),OrderInfoResponse.path("data.riskType.code"))


        );

    }


    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-批改商业险")
    @DisplayName("批改未推送过的交商同保-商业险")
    @Story("批改")
    void OrderCorrectSaveRisk3_2(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",1);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",2);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        String correctId=modifyResponse.path("data.correctId");
        FakerUtils.veriyData(modifyResponse);

        //批改过后的数据验证
        //通过orderid查询到批改详情
        HashMap<String,Object> getData = new HashMap<String, Object>();
        getData.put("correctId",correctId);
        Response OrderInfoResponse= OrderManagementApi.CreateOrder(getData, CorrectDetailPath);

        assertAll(
                ()->assertEquals(modifyData.get("correctDate"),OrderInfoResponse.path("data.correctDate")),
                ()->assertEquals(modifyData.get("correctNo"),OrderInfoResponse.path("data.correctNo")),
                ()->assertEquals(modifyData.get("riskType"),OrderInfoResponse.path("data.riskType.code"))


        );

    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-批改交强险")
    @DisplayName("批改未推送过的交商同保-交强险")
    @Story("批改")
    void OrderCorrectSaveRisk3_1(){


        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",1);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        String correctId=modifyResponse.path("data.correctId");
        FakerUtils.veriyData(modifyResponse);

    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-同时批改交强险和商业险")
    @DisplayName("批改未推送过的交商同保-同时批改交强险和商业险")
    @Story("批改")
    void OrderCorrectSaveRisk3(){


        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",1);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        FakerUtils.veriyData(modifyResponse);
        modifyData.put("riskType",2);
        modifyData.put("policyNo",policyNoj);
        modifyData.put("correctNo",TimeStamp+"1");
        String modifyBody2 = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse2= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);


    }

    /**
     * 退保的用例
     */
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建商业险-退保")
    @DisplayName("退保未推送过的商业险")
    @Story("退保")
    void OrderCorrectSaveRisk2_tuibao(){


        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",2);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNo);
        modifyData.put("correctType",2);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",2);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        FakerUtils.veriyData(modifyResponse);



    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交强险-退保")
    @DisplayName("退保未推送过的交强险")
    @Story("退保")
    void OrderCorrectSaveRisk1_tuibao(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNo);
        data.put("riskType",1);
        data.put("securityMode",100);
        data.put("devicePackage",0);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        modifyData.put("policyNo",policyNo);
        modifyData.put("correctType",2);
        modifyData.put("correctNo",TimeStamp);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        FakerUtils.veriyData(modifyResponse);



    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-同时退保交强险和商业险")
    @DisplayName("退保未推送过的交商同保-同时退保交强险和商业险")
    @Story("退保")
    void OrderCorrectSaveRisk3_tuibao(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);
        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        //modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",2);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        modifyData.put("riskType",2);
       // modifyData.put("policyNo",policyNoj);
        String modifyBody2 = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse2= OrderManagementApi.CreateOrder(modifyBody2, CorrectSavePaht);


    }

    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-退保交强险")
    @DisplayName("退保未推送过的交商同保-退保交强险")
    @Story("退保")
    void OrderCorrectSaveRisk3_1_tuibao(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        //modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",2);
        modifyData.put("riskType",1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);


    }
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("创建交商同胞-退保商业险")
    @DisplayName("退保未推送过的交商同保-退保商业险")
    @Story("退保")
    void OrderCorrectSaveRisk3_2_tuibao(){

        HashMap<String,Object> data = new HashMap<String, Object>();
        data.put("vin",vin);
        data.put("inputTime",inputTime);
        data.put("endDate",endDate);
        data.put("startDate",startDate);
        data.put("policyNo",policyNoj);
        data.put("riskType",1);
        data.put("securityMode",110);
        data.put("devicePackage",1);
        String body = FakerUtils.template("muti/Createorder.json",data);

        Response OrderResponse= OrderManagementApi.CreateOrder(body, orderCreatePath);
        String orderId=OrderResponse.path("data.orderId");
        data.put("policyNo",policyNos);
        data.put("riskType",2);
        String bodys = FakerUtils.template("muti/Createorder.json",data);
        Response responseDatas= OrderManagementApi.CreateOrder(bodys, orderCreatePath);

        // 准备批改的数据
        HashMap<String,Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId",orderId);
        modifyData.put("vin",vin);
        modifyData.put("correctDate",inputTime);
        //modifyData.put("policyNo",policyNos);
        modifyData.put("correctType",2);
        modifyData.put("riskType",2);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json",modifyData);
        Response modifyResponse= OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);


    }

}
