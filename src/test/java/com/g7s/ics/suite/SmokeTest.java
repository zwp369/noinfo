package com.g7s.ics.suite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

/**
 * @Author: zhangwenping
 * @Description:
 * @Date: Create in 14:12 2020-08-11
 * 使用标签Smoke ，冒烟测试使用
 */
@RunWith(JUnitPlatform.class)
@SelectPackages({
        "com.g7s.ics.testcase.ListManagement",
        "com.g7s.ics.testcase.ReportDayFinance",
})
@IncludeTags("Smoke")
public class SmokeTest {
}
