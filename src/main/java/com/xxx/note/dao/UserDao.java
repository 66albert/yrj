package com.xxx.note.dao;

import com.xxx.note.po.User;
import com.xxx.note.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:17
 * @description：
 */
public class UserDao {
    /**
     * 通过用户名查询用户对象，返回用户对象
     *     1.获取数据库链接
     *     2.定义SQL语句
     *     3.预编译
     *     4.设置参数
     *     5.执行查询，返回结果集
     *     6.判断并分析结果集
     *     7.关闭资源
     * @param userName
     * @return
     */
    public User queryUserByName(String userName) {

        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 1.获取数据库链接
            connection = DBUtil.getConection();
            // 2.定义SQL语句
            String sql = "select * from tb_user where uname = ?";
            // 3.预编译
            preparedStatement = connection.prepareStatement(sql);
            // 4.设置参数
            preparedStatement.setString(1, userName);
            // 5.执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();
            // 6.判断并分析结果集
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(userName);
                user.setUpwd(resultSet.getString("upwd"));
                user.setNick(resultSet.getString("nick"));
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.关闭资源
            DBUtil.close(resultSet,preparedStatement,connection);
        }
        return user;
    }

    /**
     * 测试通过用户名查询用户对象
     * 1.定义SQL语句
     * 2.设置参数集合
     * 3.调用BaseDao的查询方法
     * @param userName
     * @return
     */
    public User queryUserByName02(String userName) {
        User user = null;
        // 1.定义SQL语句
        String sql = "select * from tb_user where uname = ?";

        // 2.设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(userName);

        // 调用Basedao的查询方法
        user = (User) BaseDao.queryRow(sql, params, User.class);

        return user;
    }


    /**
     * 通过昵称与用户id查询用户对象
     * 1.定义SQL语句
     *    通过用户ID查询除了自己当前登录用户之外是否有其他用户占用了该昵称
     *        指定昵称    nick    （前台传递的参数）
     *        当前用户    userID  （session作用域中的user对象）
     *        String sql = "select * from tb_user where nick = ? and userId != ?"
     * 2.设置参数集合
     * 3.调用Basedao的查询方法
     * @param nick
     * @param userId
     * @return
     */
    public User queryUserByNickAndUserId(String nick, Integer userId) {
        // 1.定义SQL语句
        String sql = "select * from tb_user where nick = ? and userId != ?";
        // 2.设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(nick);
        params.add(userId);
        // 3.调用Basedao的查询方法
        User user = (User) BaseDao.queryRow(sql, params, User.class);
        return user;
    }
}
