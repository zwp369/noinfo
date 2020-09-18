package com.g7s.ics.testcase.OrderManagementScene;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 19:51 2020-09-17
 */
@Epic("保单管理-场景测试")
@Feature("场景测试01")
public class Scene01Test {

    ResourceBundle bundle = ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");
    String CorrectOrderPath = bundle.getString("CorrectOrderPath");
    String orderGetInfoPath = bundle.getString("orderGetInfoPath");
    String orderCrmCommitPath = bundle.getString("orderCrmCommitPath");
    String CorrectSavePaht = bundle.getString("CorrectSavePaht");
    String CorrectDetailPath = bundle.getString("CorrectDetailPath");

    public String TimeStamp;
    public String inputTime;
    public String startDate;
    public String endDate;
    public String policyNo;
    public String policyNoj;
    public String policyNos;
    public String vin;
    public static  String  orderId;

    @BeforeEach
    void BeforeEach() {
        TimeStamp = FakerUtils.getTimeStamp();
        inputTime = FakerUtils.getDateToString(TimeStamp, true);
        startDate = FakerUtils.getDateToString((Long.valueOf(TimeStamp) + 1000) + "", false);
        endDate = FakerUtils.getDateToString((Long.valueOf(TimeStamp) + 9000) + "", false);
        policyNo = "BDHA" + TimeStamp;
        policyNoj = "BDHAj" + TimeStamp;
        policyNos = "BDHAs" + TimeStamp;
        vin = "CHJA" + TimeStamp;



    }

    /**
     * 推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险
     */

    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险")
    @DisplayName("Step1： 创建交商同保的保单")
    @Order(1)
    void Step1() {

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("vin", vin);
        data.put("inputTime", inputTime);
        data.put("endDate", endDate);
        data.put("startDate", startDate);
        data.put("policyNo", policyNoj);
        data.put("riskType", 1);
        data.put("securityMode", 110);
        data.put("devicePackage", 1);
        String body = FakerUtils.template("muti/Createorder.json", data);

        Response OrderResponse = OrderManagementApi.CreateOrder(body, orderCreatePath);
        orderId = OrderResponse.path("data.orderId");
        System.out.println("看看获取到orderID没有，"+orderId);
        data.put("policyNo", policyNos);
        data.put("riskType", 2);
        String bodys = FakerUtils.template("muti/Createorder.json", data);
        Response responseDatas = OrderManagementApi.CreateOrder(bodys, orderCreatePath);


    }

    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险")
    @DisplayName("Step2： 在保单录入中，将保单推送到CRM")
    @Order(2)
    void Step2() {
        //通过orderid查询orderNo和proposalNo
        HashMap<String, Object> getData = new HashMap<String, Object>();
        getData.put("orderId",orderId);
        System.out.println("Step2:看看获取到orderID没有，"+orderId);
        Response OrderInfoResponse = OrderManagementApi.CreateOrder(getData, orderGetInfoPath);
        String orderNo = OrderInfoResponse.path("data.orderNo");
        String proposalNo = OrderInfoResponse.path("data.proposalNo");
        // 组装数据，推送CRM
        HashMap<String, Object> CRMData = new HashMap<String, Object>();
        CRMData.put("inputTime", inputTime);
        CRMData.put("policyNos", policyNos);
        CRMData.put("policyNoj", policyNoj);
        CRMData.put("orderId", orderId);
        CRMData.put("orderNo", orderNo);
        CRMData.put("proposalNo", proposalNo);
        CRMData.put("securityMode", 100);
        CRMData.put("devicePackageType", 0);
        String CRMBody = FakerUtils.template("muti/PushToCRM.json", CRMData);


        Response pushRespone = OrderManagementApi.OrderCrmCommit(CRMBody, orderCrmCommitPath);
        assertEquals(orderId, pushRespone.path("data.orderId").toString());


    }

    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险")
    @DisplayName("Step3： 在保单调整中，修改保单的信息，修改安保模式，从新装修改成为非安保模式")
    @Order(3)
    void Step3() {

        HashMap<String, Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId", orderId);
        modifyData.put("vin", vin);
        modifyData.put("inputTime", inputTime);
        modifyData.put("policyNos", policyNos);
        modifyData.put("policyNoj", policyNoj);
        modifyData.put("securityMode", 100);
        modifyData.put("devicePackageType", 0);
        modifyData.put("saveType", "reEntry");


        String modifyBody = FakerUtils.template("muti/OrderCorrectAmend.json", modifyData);

        Response modifyResponse = OrderManagementApi.CreateOrder(modifyBody, CorrectOrderPath);
        FakerUtils.veriyData(modifyResponse);

        //修改后的结果验证
        //通过orderid查询orderNo和proposalNo
        HashMap<String, Object> getData = new HashMap<String, Object>();
        getData.put("orderId", orderId);
        Response OrderInfoResponse = OrderManagementApi.CreateOrder(getData, orderGetInfoPath);

        assertAll(
                () -> assertEquals(modifyData.get("securityMode"), OrderInfoResponse.path("data.securityMode.code")),
                () -> assertEquals(modifyData.get("devicePackageType"), OrderInfoResponse.path("data.devicePackage.code"))


        );
    }


    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险")
    @DisplayName("Step4： 在保单调整中，批改商业险，并通过详情查看验证记录和类型是否批改正确")
    @Order(4)
    void Step4() {
        // 准备批改的数据
        HashMap<String, Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId", orderId);
        modifyData.put("vin", vin);
        modifyData.put("correctDate", inputTime);
        modifyData.put("policyNo", policyNos);
        modifyData.put("correctType", 1);
        modifyData.put("correctNo", TimeStamp);
        modifyData.put("riskType", 2);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json", modifyData);
        Response modifyResponse = OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        String correctId = modifyResponse.path("data.correctId");
        FakerUtils.veriyData(modifyResponse);

        //批改过后的数据验证
        //通过orderid查询到批改详情
        HashMap<String, Object> getData = new HashMap<String, Object>();
        getData.put("correctId", correctId);
        Response OrderInfoResponse = OrderManagementApi.CreateOrder(getData, CorrectDetailPath);

        assertAll(
                () -> assertEquals(modifyData.get("correctDate"), OrderInfoResponse.path("data.correctDate")),
                () -> assertEquals(modifyData.get("correctNo"), OrderInfoResponse.path("data.correctNo")),
                () -> assertEquals(modifyData.get("riskType"), OrderInfoResponse.path("data.riskType.code"))


        );
    }

    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存并提交」-批改商业险-退保交强险")
    @DisplayName("Step5： 在保单调整中，退保交强险")
    @Order(5)
    void Step5() {
        // 准备批改的数据
        HashMap<String, Object> modifyData = new HashMap<String, Object>();
        modifyData.put("orderId", orderId);
        modifyData.put("vin", vin);
        modifyData.put("correctDate", inputTime);
        //modifyData.put("policyNo",policyNos);
        modifyData.put("correctType", 2);
        modifyData.put("riskType", 1);
        String modifyBody = FakerUtils.template("muti/OrderCorrectSave.json", modifyData);
        Response modifyResponse = OrderManagementApi.CreateOrder(modifyBody, CorrectSavePaht);
        FakerUtils.veriyData(modifyResponse);


    }
}
