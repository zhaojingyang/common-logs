package com.anchor.common.logs;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anchor on 2015/8/16.
 */
public class LogBetterTest {

    public final static Logger logger = LoggerFactory.getLogger(LogBetterTest.class);

    @Test
    public void TestLog() {
        LogBetter.instance(logger)
                .setMsg("最简单的测试日志，仅仅一行输出")
                .log();

        String traceId = String.valueOf((new Date()).getTime());

        System.out.println("traceId: " + traceId);

        List<TestParameter> paramList = new ArrayList<TestParameter>();
        TestParameter param = new TestParameter();
        param.setId(1L);
        param.setName("name");
        param.setDescription("description");

        paramList.add(param);

        for (int i = 0; i < 5; i++) {
            LogBetter.instance(logger)
                    .setErrorMsg("错误日志信息")
                    .setParams(paramList)
                    .log();

            LogBetter.instance(logger)
                    .setMsg("简单日志信息")
                    .setParams(paramList)
                    .log();

            LogBetter.instance(logger)
                    .setMsg("可以配置参数Key的日志")
                    .addParam("参数1", 1L)
                    .addParam("参数2", 2L)
                    .log();
        }
    }

    @Test
    public void TestException() {
        try {
            int i = 1 / 0;
            int k = 1 + i;
        } catch (Exception ex) {
            LogBetter.instance(logger)
                    .setErrorMsg("异常日志")
                    .setException(ex)
                    .log();
        }
    }
}
