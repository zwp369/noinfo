package com.g7s.ics.suite;


import com.g7s.ics.testcase.ListManagement.CustomerListTest;
import com.g7s.ics.testcase.ListManagement.LogTest;
import com.g7s.ics.testcase.ListManagement.VechicleListTest;
import com.g7s.ics.testcase.ReportDayFinance.ReportDayFinanceTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:00 2020-08-10
 * 使用标签Hafl ，部分回归
 * classes 可以在不同的包中
 */

@RunWith(JUnitPlatform.class)
@SelectClasses({

        LogTest.class,
        CustomerListTest.class,
        VechicleListTest.class,
        ReportDayFinanceTest.class

})

@IncludeTags("Hafl")
public class RegressionTes {


}
