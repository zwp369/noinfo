package com.g7s.ics.suit;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:42 2020-08-10
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({
        "com.g7s.ics.testcase.ListManagement",
        //"com.g7s.ics.demo"
})

public class RunLogTest {

}

