package com.g7s.ics.suite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:42 2020-08-10
 * 指定所有的包名即可
 */


@RunWith(JUnitPlatform.class)
@SelectPackages({
        "com.g7s.ics.testcase.ListManagement",
        "com.g7s.ics.testcase.ReportDayFinance",
        "com.g7s.ics.testcase.OrderManagement",
        "com.g7s.ics.testcase.OrderManagementScene",
        

})

public class AllRegressionTest {

}

