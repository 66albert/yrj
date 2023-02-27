package com.xxx.note;

import cn.hutool.crypto.digest.DigestUtil;
import com.xxx.note.dao.BaseDao;
import com.xxx.note.dao.UserDao;
import com.xxx.note.po.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:41
 * @description：
 */
public class TestUser {
    @Test
    public void testQueryUserByName() {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }

    @Test
    public void testAdd() {
        String sql = "insert into tb_user (uname, upwd, nick, head, mood) values(?, ?, ?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add("lisi");
        params.add(DigestUtil.md5Hex("123321"));
        params.add("lisi");
        params.add("404.jpg");
        params.add("hello");

        int rows = BaseDao.executeUpdate(sql, params);
        System.out.println("rows = " + rows);
    }
}
