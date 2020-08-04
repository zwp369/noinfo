package com.g7s.ics;

import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 17:57 2020-07-24
 */
public class RequestClasse {
    public static Response Request(HashMap data,HashMap getheader,HashMap sign, String baseURL){
        System.out.println(baseURL);

        return  given().log().all()
                .queryParams(sign)
                .headers(getheader)
                .contentType("application/json")
                .when().body(data)
                .post(baseURL)
                .then().log().all()
                .statusCode(200)
                .log().all().extract().response();
    }








}
