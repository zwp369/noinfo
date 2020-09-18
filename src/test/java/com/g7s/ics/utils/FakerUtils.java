package com.g7s.ics.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.StringWriter;
import java.io.Writer;
import java.security.SignatureException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 16:20 2020-07-24
 */
public class FakerUtils {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(com.g7s.ics.utils.FakerUtils.class);
    public static HashMap<String, Object> headers;
    public static HashMap<String, Object> H5Headers;
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



    public static HashMap getheader() {
        headers = new HashMap<String, Object>();
        headers.put("X-ICS-USERID", 273);
        headers.put("X-ICS-USERNAME", "xiaozhangzhang");
        headers.put("x-g7-language", "zh_CN");
        return headers;
    }
    public static HashMap getH5Header() {
        H5Headers = new HashMap<String, Object>();
        H5Headers.put("x-g7-language", "zh_CN");
        H5Headers.put("X-CRM-USER-ID", "8072557f-cbc7-e911-8100-b63d6384c1b5");
        H5Headers.put("X-CRM-TOKEN","1");
        H5Headers.put("X-Requested-With","com.huitong.uat");
        H5Headers.put("Accept-Language","zh-CN,en-US;q=0.8");
        return H5Headers;
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
        String url = "jdbc:mysql://rm-2zeptxy191r28lft7.mysql.rds.aliyuncs.com:3306/ics?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=true&zeroDateTimeBehavior=convertToNull";
        String username = "ics_2020_rw";
        String password = "%R2346dD$gv6Uc4t#g";
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

    /**
     *
     * @param time
     * @param isTime true  是时间 false 是日期
     *
     * @return
     */
// 将时间戳转换成为日期格式
    public static String getDateToString(String time,boolean isTime) {
        SimpleDateFormat sdf ;
        String sd ;
      if(isTime==true){
          sdf=new SimpleDateFormat("yyyy-MM-dd");
          sd = sdf.format(new Date(Long.parseLong(time)));
      }
      else {
          sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          sd = sdf.format(new Date(Long.parseLong(time)));
      }
        return sd;

    }


    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 模版替换参数
     * @param path 模版的存储位置
     * @param data 需要替换的数据
     * @return
     */
    public static String template(String path,HashMap<String, Object> data){
        Writer writer = new StringWriter();
        new DefaultMustacheFactory()
                .compile(path)
                .execute(writer,data);
        return writer.toString();
        //System.out.println(writer.toString());
    }

    /**
     * 简单验证结果
     * @param responseData 需要验证的respone
     */
    public static void veriyData(Response responseData) {
        assertAll(
                () -> assertEquals("成功", responseData.path("sub_msg")),
                () -> assertEquals("0", responseData.path("code").toString()),
                () -> assertNotEquals("null", responseData.path("data").toString())


        );
    }



}


