package com.g7s.ics.suit;

import com.g7s.ics.testcase.ListManagement.CustomerListTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 19:54 2020-08-07
 */
@RunWith(JUnitPlatform.class)
@SelectClasses({
      CustomerListTest.class

})
public class RunwithTest {

}
