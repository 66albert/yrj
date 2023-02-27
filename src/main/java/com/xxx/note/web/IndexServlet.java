package com.xxx.note.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：刘彬
 * @date ：Created in 2023/2/26 22:21
 * @description：
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页动态包含的页面值
        request.setAttribute("changePage", "note/list.jsp");
        // 请求转发跳转到index.jsp
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
