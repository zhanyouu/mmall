package com.mmall.controller.backend;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/manager/user/")
public class UserManagerController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response=iUserService.login(username,password);
        if(response.isSUCCESS()){
            User user=response.getData();
            if(user.getRole()== Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }
            return ServerResponse.createByErrorMessage("不是管理员，无法登录");
        }
        return response;
    }
}
