
package com.hyc.www.util;

import com.hyc.www.controller.constant.Methods;
import com.hyc.www.controller.constant.Pages;
import com.hyc.www.vo.PagesVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制层工具类
 */
public class ControllerUtils {


    /**
     * 解析请求的参数，返回对应的枚举项
     */
    public static Methods getMethod(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String m = req.getParameter("method");
        Methods method = null;

        try {
            method = Methods.valueOf(m);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("非法请求：重定向到首页");
            return Methods.INDEX_VIEW;
        }
        return method;
    }


    /**
     * 封装控制层的转发，设置参数等行为
     */
    public static void forward(HttpServletRequest req, HttpServletResponse resp, PagesVo data, String message, Pages pages) throws ServletException, IOException {
        req.setAttribute("data", data);
        req.setAttribute("message", message);
        req.getRequestDispatcher(pages.toString()).forward(req, resp);
    }




    /**
     * 封装控制层的转发，设置参数等行为
     */
    public static void forward(HttpServletRequest req, HttpServletResponse resp, PagesVo data, String message, String path) throws ServletException, IOException {
        req.setAttribute("data", data);
        req.setAttribute("message", message);
        req.getRequestDispatcher(path).forward(req, resp);
    }


    public static void redirect(HttpServletResponse resp, String path) throws ServletException, IOException {
        resp.sendRedirect(path);
    }



}
