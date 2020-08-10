package com.g7s.ics.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.security.SignatureException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;


/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 16:20 2020-07-24
 */
public class FakerUtils {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(com.g7s.ics.utils.FakerUtils.class);
    public static HashMap<String, Object> headers;
    public static HashMap<String, Object> queryParams;

    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static ResourceBundle bundle = ResourceBundle.getBundle("Evn/Evn");


    public static String getbasehost(String basePath) {


        //return  bundle.getString("testHost")+basePath;
        //ResourceBundle bundle= ResourceBundle.getBundle("Evn/Evn");
        return bundle.getString("VUE_APP_VEGA_BASEURL_Test") + "/v1/ics-service" + basePath;


    }


    //public static HashMap<String, Object> headers;
    public static HashMap getheader() {
        headers = new HashMap<String, Object>();
        headers.put("globalToken", 1);
        headers.put("user", 10);
        headers.put("x-g7-language", "zh_CN");
        return headers;
    }


    public static HashMap<String, Object> getQueryParams(String Uri) {
        queryParams = new HashMap<String, Object>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        queryParams.put("accessid", bundle.getString("VUE_APP_VEGA_ICS_SERVICE_ACCESS_ID_Test"));
        String sign = calSign(bundle.getString("VUE_APP_VEGA_ICS_SERVICE_SECRET_KEY_Test"), "POST", timestamp, Uri);
        queryParams.put("sign", sign);
        queryParams.put("g7timestamp", timestamp);

        return queryParams;
    }

    public static HashMap<String, Object> getQueryParamsInward(String Uri) {
        queryParams = new HashMap<String, Object>();
        String timestamp = String.valueOf(System.currentTimeMillis());
        queryParams.put("accessid", bundle.getString("accessId_Inward"));
        String sign = calSign(bundle.getString("secretKey_Inward"), "POST", timestamp, Uri);
        queryParams.put("sign", sign);
        queryParams.put("g7timestamp", timestamp);

        return queryParams;
    }


    //生成vega签名
    @Test

    private static String calSign(String secret, String httpVerb, String timestamp, String uri) {
        String newuri = "/v1/ics-service" + uri;
        String stringToSign = httpVerb + "\n" + timestamp + "\n" + newuri;
        String sign;
        try {
            sign = HMACSHA1EncoderUtil.calculateRFC2104HMAC(secret, stringToSign);
        } catch (SignatureException e) {
            throw new RuntimeException("sign failed");
        }
        return sign;
    }


    // 清理数据库,通过输入sql来执行
    public static void cleanMysqlData(String sql) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://rm-2zeptxy191dhjjdjdjd06/ics?serverTimezhdddjterEncoding=utf-8&usejdjjdll";
        String username = "dhhdhd";
        String password = "%2hhdhdh#g";
        Connection conn = null;
        Statement st = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }





}
