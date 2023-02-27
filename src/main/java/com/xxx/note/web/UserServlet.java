package com.xxx.note.web;

import cn.hutool.core.io.FileUtil;
import com.mysql.cj.PreparedQuery;
import com.xxx.note.po.User;
import com.xxx.note.service.UserService;
import com.xxx.note.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 17:18
 * @description：
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    // 获取service对象
    UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航高亮
        request.setAttribute("menu_page", "user");

        /**
         * 用户行为：
         *         用户登录                actionName="login"
         *         进入个人中心            actionName="userCenter"
         *         加载头像                actionName="userHead"
         *         验证名称的唯一性        actionName='checkNick"
         *         修改用户信息            actionName="updateUser"
         *         用户退出               actionName="loginout"
         */
        // 接收用户行为
        String actionName = request.getParameter("actionName");
        // 判断用户行为,调用对应方法
        if ("login".equals(actionName)) {
            // 用户登录
            userLogin(request,response);
        } else if ("logout".equals(actionName)) {
            // 用户退出
            userLogout(request,response);
        } else if ("userCenter".equals(actionName)) {
            // 进入个人中心
            userCenter(request,response);
        } else if ("userHead".equals(actionName)) {
            // 加载头像
            userHead(request,response);
        } else if ("checkName".equals(actionName)) {
            // 验证昵称的唯一性
            checkNick(request, response);
        }
    }

    /**
     * 1.获取参数（昵称）
     * 2.从session作用域中获取用户对象，得到用户ID
     * 3.调用service层方法，得到返回结果
     * 4.通过字符输出流将结果响应给前台的Ajax的回调函数
     * 5.关闭资源
     * @param request
     * @param response
     */
    private void checkNick(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.获取参数（昵称）
        String nick = request.getParameter("nick");
        // 2.从session作用域中获取用户对象，得到用户ID
        User user = (User) request.getSession().getAttribute("user");
        // 3.调用service层方法，得到返回结果
        Integer code = userService.checkNick(nick, user.getUserId());
        // 4.通过字符输出流将结果响应给前台的Ajax的回调函数
        response.getWriter().write(code + "");
        // 5.关闭资源
        response.getWriter().close();
    }

    /**
     * 加载头像
     * 1.获取参数（图片名称）
     * 2.得到图片的存放路径（request.getServletContext().getRealPath("/")
     * 3.通过图片的完整路径，得到一个file对象
     * 4.通过截取得到图片的后缀
     * 5.通过不同的图片后缀，设置不同的响应类型
     * 6.利用FileUtils的copyFile()方法，将图片拷贝给浏览器
     * @param request
     * @param response
     */
    private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.获取参数（图片名称）
        String head = request.getParameter("imageName");
        // 2.得到图片的存放路径（得到项目的真实路径request.getServletContext().getRealPath("/")
        String realPath = request.getServletContext().getRealPath("/WEB-INF/upload");
        // 3.通过图片的完整路径，得到一个file对象
        File file = new File(realPath + "/" + head);
        // 4.通过截取得到图片的后缀
        String pic = head.substring(head.lastIndexOf(".") + 1);
        // 5.通过不同的图片后缀，设置不同的响应类型
        if ("png".equalsIgnoreCase(pic)) {
            response.setContentType("image/png");
        } else if ("jpg".equalsIgnoreCase(pic) || "jpeg".equalsIgnoreCase(pic)) {
            response.setContentType("image/jpeg");
        } else if ("gif".equalsIgnoreCase(pic)) {
            response.setContentType("image/gif");
        }
        // 6.利用FileUtils的copyFile()方法，将图片拷贝给浏览器
        FileUtils.copyFile(file, response.getOutputStream());
    }

    /**
     * 进入个人中心
     * 1、设置首页动态包含的页面值
     * 2.请求转发跳转到index
     * @param request
     * @param response
     */
    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、设置首页动态包含的页面值
        request.setAttribute("changePage", "user/info.jsp");
        // 2.请求转发跳转到index
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }


    /**
     * 1.销毁session对象
     * 2.删除cookie对象
     * 3.重定向跳转到登录页面
     * @param request
     * @param response
     */
    private void userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.销毁session对象
        request.getSession().invalidate();
        // 2.删除cookie对象(cookie没有删除方法，所以设置过期时间为0来删除)
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // 3.重定向跳转到登录页面
        response.sendRedirect("login.jsp");
    }

    /**
     * 用户行为：用户登录
     * 1.获取参数（姓名，密码）
     * 2.调用service层方法，返回ResultInfo对象
     * 3.判断是否登录成功
     *     如果失败
     *         将ResultInfo对象设置到request作用域中
     *         请求转发跳转到登录页面
     *     如果成功
     *         判断用户是否选择记住密码（rem的值是1）
     *              如果是，将用户的姓名与密码存在cookie中，设置失效时间，并响应给客户端
     *              如果否，清空原有的cookie对象
     *              重定向跳转到首页
     * @param request
     * @param response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取参数（姓名，密码）
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        // 2.调用service层方法，返回ResultInfo对象
        ResultInfo<User> resultInfo = userService.userLogin(userName, userPwd);

        // 3.判断是否登录成功
        if (resultInfo.getCode() == 1) {
            // 如果成功 将ResultInfo对象设置到request作用域中
            request.getSession().setAttribute("user", resultInfo.getResult());
            // 判断用户是否选择记住密码（rem的值是1）
            String rem = request.getParameter("rem");
            // 如果是，将用户的姓名与密码存在cookie中，设置失效时间，并响应给客户端
            if ("1".equals(rem)) {
                // 得到cookie对象
                Cookie cookie = new Cookie("user", userName + "-" + userPwd);
                // 设置失效时间
                cookie.setMaxAge(3*24*60*60);
                // 响应给客户端
                response.addCookie(cookie);
            } else {
                // 如果否，清空原有的cookie对象
                Cookie cookie = new Cookie("user", null);
                // 删除cookie，设置maxage为0
                cookie.setMaxAge(0);
                // 响应给客户端
                response.addCookie(cookie);
            }
            // 重定向跳转到首页
            response.sendRedirect("index.jsp");
        } else {
            // 如果失败
            // 将ResultInfo对象设置到request作用域中
            request.setAttribute("resultInfo", resultInfo);
            // 请求转发跳转到登录页面
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}
