package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.User;
import com.SmartMed_Connect.service.ApiService;
import com.SmartMed_Connect.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * 处理用户相关操作，如修改资料和修改密码
 */
@RestController//相当于 @Controller 和 @ResponseBody 的结合，表示这个类的所有方法返回的数据直接写入 HTTP 响应体中
@RequestMapping(value = "user")//用于映射 HTTP 请求的 URL 路径到类或方法上。
public class UserController extends BaseController<User> {

    /**
     * 修改资料
     * @param user 要保存的用户对象
     * @return 返回操作结果，成功或失败信息
     */
    @PostMapping("/saveProfile")
    public RespResult saveProfile(User user) {
        // 检查用户对象是否为空
        if (Assert.isEmpty(user)) {
            return RespResult.fail("保存对象不能为空");
        }
        // 保存用户信息到数据库
        user = userService.save(user);
        // 更新当前登录用户的信息
        session.setAttribute("loginUser", user);
        // 返回保存成功的结果
        return RespResult.success("保存成功");
    }

    /**
     * 修改密码
     * @param oldPass 旧密码
     * @param newPass 新密码
     * @return 返回操作结果，成功或失败信息
     */
    @PostMapping("/savePassword")
    public RespResult savePassword(String oldPass, String newPass) {
        if (!loginUser.getUserPwd().equals(oldPass)) {
            return RespResult.fail("旧密码错误");
        }
        // 更新用户的新密码
        loginUser.setUserPwd(newPass);
        loginUser = userService.save(loginUser);
        // 更新当前登录用户的信息
        session.setAttribute("loginUser", loginUser);
        // 返回保存成功的结果
        return RespResult.success("保存成功");
    }
}
