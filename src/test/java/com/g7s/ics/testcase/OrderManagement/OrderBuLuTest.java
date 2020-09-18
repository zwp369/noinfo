package com.g7s.ics.testcase.OrderManagement;

import com.g7s.ics.utils.FakerUtils;

import java.util.ResourceBundle;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:42 2020-09-08
 */
public class OrderCreatTest {
    ResourceBundle bundle =ResourceBundle.getBundle("Interface/ListInterface");
    String orderCreatePath = bundle.getString("orderCreatePath");

    String vin ="CJH"+ FakerUtils.getTimeStamp();

}
