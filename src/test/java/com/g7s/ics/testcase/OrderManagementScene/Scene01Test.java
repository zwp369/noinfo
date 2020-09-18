package com.g7s.ics.testcase.OrderManagementScene;

import com.g7s.ics.api.OrderManagementApi;
import com.g7s.ics.utils.FakerUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 19:51 2020-09-17
 */
@Epic("保单管理")
@Feature("场景测试用例")
public class SceneTest {

    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");
    String CorrectOrderPath = bundle.getString("CorrectOrderPath");
    String orderGetInfoPath = bundle.getString("orderGetInfoPath");
    String orderCrmCommitPath = bundle.getString("orderCrmCommitPath");
    String CorrectSavePaht = bundle.getString("CorrectSavePaht");

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

    /**
     * 推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存」-批改商业险-退保交强险
     */
    @Tags({@Tag("Hafl"),@Tag("Smoke")})
    @Test
    @Description("推送过的数据场景测试，新建交商同保-保单录入-制单错误「保存」-批改商业险-退保交强险")
    @DisplayName("Scene01:新建交商同保-保单录入-制单错误「保存」-批改商业险-退保交强险")
    void Scene01(){

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




}
