package com.xxx.note.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.xxx.note.dao.UserDao;
import com.xxx.note.po.User;
import com.xxx.note.vo.ResultInfo;
import com.xxx.note.web.UserServlet;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:17
 * @description：
 */
public class UserService {
    // 获取UserDao对象
    private UserDao userDao = new UserDao();

    /**
     * 用户登录
     *1.判断参数是否为空
     *     如果为空
     *        设置ResultInfo对象的状态码和提示信息
     *        返回ResultInfo对象
     *2.如果不为空，通过用户名查询用户对象
     *3.判断用户对象是否为空
     *     如果为空
     *        设置ResultInfo对象的状态码和提示信息
     *        返回ResultInfo对象
     *4.如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码做比较（将密码加密后再比较）
     *     如果密码不正确
     *        设置ResultInfo对象的状态码和提示信息
     *        将ResultInfo对象设置到request作用域中
     *        请求转发跳转到登录页面
     *        return
     *5.如果密码正确
     *     设置ResultInfo对象的状态码和提示信息
     *6.返回ResultInfo对象
     * @param userName
     * @param userPwd
     * @return
     */
    public ResultInfo<User> userLogin(String userName, String userPwd) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        // 数据回显：当登录实现时，将登录信息返回给页面显示
        User u = new User();
        u.setUname(userName);
        u.setUpwd(userPwd);
        // 设置到resultInfo对象中
        resultInfo.setResult(u);

        // 1.判断参数是否为空   使用hutool工具集自带方法判断
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)) {
            // 如果为空: 设置ResultInfo对象的状态码和提示信息 返回ResultInfo对象
            resultInfo.setCode(0);
            resultInfo.setMsg("用户姓名或密码不能为空！");
            return resultInfo;
        }

        // 2.如果不为空，通过用户名查询用户对象
        User user = userDao.queryUserByName(userName);

        // 3.判断用户对象是否为空
        if (user == null) {
            // 如果为空: 设置ResultInfo对象的状态码和提示信息 返回ResultInfo对象
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
            return resultInfo;
        }

        // 4.如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码做比较（将密码加密后再比较）
        // 先将前台传递的密码使用MD5算法加密
        userPwd = DigestUtil.md5Hex(userPwd);
        // 判断加密后的密码是否与数据库中的一致
        if (!userPwd.equals(user.getUpwd())) {
            // 如果密码不正确
            // 设置ResultInfo对象的状态码和提示信息
            resultInfo.setCode(0);
            // 将ResultInfo对象设置到request作用域中
            resultInfo.setMsg("用户密码错误！");
            // 请求转发跳转到登录页面

            // return
            return resultInfo;
        }

        // 5.如果密码正确
        // 设置ResultInfo对象的状态码和提示信息
        resultInfo.setCode(1);
        resultInfo.setResult(user);
        // 返回ResultInfo对象
        return resultInfo;
    }

    /**
     * 验证昵称的唯一性
     * 1.判断昵称是否为空
     *     如果为空，返回'0'
     * 2.调用dao层，通过用户id和昵称查询用户对象
     * 3.判断用户对象是否存在
     *     存在，返回’0‘
     *     不存在，返回’1‘
     * @param nick
     * @param userId
     * @return
     */
    public Integer checkNick(String nick, Integer userId) {
        // 1.判断昵称是否为空
        if (StrUtil.isBlank(nick)) {
            // 如果为空，返回'0'
            return 0;
        }
        // 2.调用dao层，通过用户id和昵称查询用户对象
        User user = userDao.queryUserByNickAndUserId(nick, userId);
        // 3.判断用户对象是否存在
        if (user != null) {
            // 存在，返回’0‘
            return 0;
        }
        // 不存在，返回’1‘
        return 1;
    }
}
