package com.g7s.ics;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 17:57 2020-07-24
 */
public class RequestClasse {
//
//    public static Response Request(Object data, Map getheader, Map sign, String baseURL){
//        System.out.println(baseURL);
//        RestAssured.useRelaxedHTTPSValidation();
//        return  given().log().all()
//                .queryParams(sign)
//                .headers(getheader)
//                .contentType("application/json")
//                .when().body(data)
//                .post(baseURL)
//                .then().log().all()
//                .statusCode(200)
//                .extract().response();
//    }

    /**
     *
     * @param data  body入参
     * @param getheader 请求需要的header
     * @param sign  queryparams 中如参，网关需要
     * @param baseURL 请求地址
     * @return 返回请求的的respone body
     *
     * 暂时只支持post请求， get请求和不需要传入header的需要在封装
     */
        public static Response Request(Object data, HashMap getheader, HashMap sign, String baseURL) {
            System.out.println(baseURL);
             Response response =null;
             // 将每次发起请求的日志写入文件中，只记录一次，最新覆盖上一次，最后通过addHttpLogToAllure，将日志写入到报告中，实现每个请求一个bogy
            try (FileWriter fileWriter = new FileWriter("src/main/resources/test.log");
                 PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true)) {
                 RestAssured.useRelaxedHTTPSValidation();
                RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));


                response= given().log().all()
                        .queryParams(sign)
                        .headers(getheader)
                        .contentType("application/json")
                        .when().body(data)
                        .post(baseURL)
                        .then().log().all()
                        .statusCode(200).extract().response();
            } catch (IOException e) {
                e.printStackTrace();
            }
            addHttpLogToAllure();
            return response;

        }

    public static Response RequestGet(Map data, Map getheader, Map sign, String baseURL) {
        System.out.println(baseURL);
        Response response =null;
        // 将每次发起请求的日志写入文件中，只记录一次，最新覆盖上一次，最后通过addHttpLogToAllure，将日志写入到报告中，实现每个请求一个bogy
        try (FileWriter fileWriter = new FileWriter("src/main/resources/test.log");
             PrintStream printStream = new PrintStream(new WriterOutputStream(fileWriter), true)) {
            RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(printStream));


            response= given().log().all()
                    .queryParams(sign)
                    .queryParams(data)
                    .headers(getheader)
                    .contentType("application/json")
                    .when()
                    .get(baseURL)
                    .then().log().all()
                    .statusCode(200).extract().response();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addHttpLogToAllure();
        return response;

    }

    /**
     * 用于每次调用后allure中增加附件，可以记录每次访问的请求和返回
     */

    public static void addHttpLogToAllure() {
        try {
            Allure.addAttachment("接口请求响应日志",
                    new FileInputStream("src/main/resources/test.log"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }










}
