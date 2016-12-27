/**
 * Company
 * Copyright (C) 2004-2016 All Rights Reserved.
 */
package com.chinaredstar.Controller;

import com.chinaredstar.api.IUserService;
import com.chinaredstar.api.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.HttpCookie;

/**
 * @author Littlebear1996
 * @version $Id UserControler.java, v 0.1 2016-12-21 下午1:55 Littlebear1996 Exp $$
 */
@Controller
public class UserControler {
    private static Logger logger = LoggerFactory.getLogger(UserControler.class);
    @Autowired
    private IUserService userService;

    /**
     * 跳转链接，跳转到主页(重定向到index页面)
     *
     * @param response
     * @return
     */
    @RequestMapping("")
    public String index(HttpServletResponse response) {
        return response.encodeRedirectURL("/login");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String toRegister(User user) {
        return "register";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public String register(Model model,
                           @Valid @ModelAttribute(value = "user") User user,
                           HttpServletResponse response,
                           BindingResult bindingResult) {
        System.out.println("sss");
        System.out.println(bindingResult.hasErrors());
        if(!bindingResult.hasErrors()) {
            String flag = userService.insertUser(user);
            StringBuffer result = new StringBuffer("");
            if (flag.equals("failure")) {
                logger.info("注册失败");
                result.append("注册失败,您所注册的用户名已存在,请重新注册");
                model.addAttribute("result", result);
                return "register";
            } else {
                logger.info("注册成功");
                result.append("注册成功,请您点击登录开始登陆");
                model.addAttribute("result", result);
                return response.encodeRedirectURL("/login");
            }
        }else {
            logger.info("注册失败");
            return response.encodeRedirectURL("/index");
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String login(Model model,
                        @ModelAttribute(value = "user") User user,
                        HttpServletResponse response
    ) {
        String flag = userService.findUserByName(user);
        StringBuffer result = new StringBuffer("");
        if (flag.equals("success")) {
            logger.info("login success");
            //result.append("登陆成功,欢迎您:" + user.getUserName());
            HttpCookie cookie = new HttpCookie("userName", user.getUserName());
            //session.setAttribute("userName", user.getUserName());
            //model.addAttribute("result", result);
            return response.encodeRedirectURL("/success");
        } else if (flag.equals("failure")) {
            logger.info("login failure");
            result.append("登录失败,密码错误,请重新登录");
            model.addAttribute("result", result);
            return "login";
        } else {
            logger.info("login failure");
            result.append("登录失败,您所输入的用户名不存在,请检查后再登录");
            model.addAttribute("result", result);
            return "login";
        }
    }

    @RequestMapping("/toShow")
    public String show(Model model,
                       HttpServletResponse response,
                       HttpSession session
    ) {
        //User user = (User) session.getAttribute("user");
        String userName = (String)session.getAttribute("userName");
        model.addAttribute("user", userService.getUser(userName));
        return response.encodeRedirectURL("/show");
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Model model, HttpSession session) {
        model.addAttribute("user", userService.getUser((String)session.getAttribute("userName")));
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model,
                         @ModelAttribute(value = "user") User user,
                         HttpServletResponse response) {
        userService.updateUserById(user);
        model.addAttribute("user", userService.getUser(user.getUserName()));
        return response.encodeRedirectURL("/show");
    }
    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession session) {
        //从session中删除user属性，用户退出登录
        session.removeAttribute("user");
        return "login";
    }
}