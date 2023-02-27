package com.xxx.note;

import com.xxx.note.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 10:57
 * @description：测试类
 */
public class TestDB {

    /**
     * 单元测试方法
     * 1.方法的返回值，建议使用void，一般没有返回值
     * 2.参数列表，建议空参，一般没有参数
     * 3.方法上需要设置@Test注解
     * 4.每一个方法都能独立运行
     *
     * 判定结果：
     *      绿色：成功
     *      红色：失败
     */

    // 使用日志工厂类，记录日志
    private Logger logger = LoggerFactory.getLogger(TestDB.class);

    /**
     * 测试获取数据库连接
     */
    @Test
    public void TestDB() {
        System.out.println(DBUtil.getConection());
        // 使用日志
        logger.info("获取数据库连接： " + DBUtil.getConection());   // 字符串拼接
        logger.info("获取数据库连接： {}", DBUtil.getConection());  // 参数传递
    }
}
