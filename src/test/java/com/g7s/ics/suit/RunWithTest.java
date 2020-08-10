package com.g7s.ics.suit;


import com.g7s.ics.demo.ATest;
import com.g7s.ics.testcase.BTest;
import com.g7s.ics.testcase.ListManagement.LogTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:00 2020-08-10
 */

@RunWith(JUnitPlatform.class)
@SelectClasses({
        ATest.class,
        BTest.class,
        LogTest.class



})
public class RunWithTest {

}
