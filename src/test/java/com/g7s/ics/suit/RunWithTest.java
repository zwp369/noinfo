package com.g7s.ics.suit;


import com.g7s.ics.testcase.ListManagement.CustomerListTest;
import com.g7s.ics.testcase.ListManagement.LogTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 20:00 2020-08-10
 */

@RunWith(JUnitPlatform.class)
@SelectClasses({
        //ATest.class,
        //BTest.class,
        LogTest.class,
        CustomerListTest.class

})
@IncludeTags("1")

public class RunWithTest {


}
