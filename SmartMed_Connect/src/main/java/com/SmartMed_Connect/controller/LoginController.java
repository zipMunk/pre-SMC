package com.SmartMed_Connect.controller;

import cn.hutool.core.util.StrUtil;
import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.User;
import com.SmartMed_Connect.service.ApiService;
import com.SmartMed_Connect.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * LoginController 类用于处理用户登录、注册和发送邮箱验证码的请求。
 * 继承自 BaseController<User>，可以对 User 类型的数据进行 CRUD 操作。
 */
@RestController
@RequestMapping(value = "login")
public class LoginController extends BaseController<User> {


    @Autowired
    public UserController userController;

    /**
     * 注册用户
     *
     * @param user 用户对象，包含用户信息
     * @param code 邮箱验证码
     * @return 注册结果，成功则返回注册的用户对象，失败则返回相应的错误信息
     */
    @PostMapping("/register")
    public RespResult register(User user, String code) {
        String email = user.getUserEmail();
        // 验证邮箱是否为空
        if (Assert.isEmpty(email)) {
            return RespResult.fail("邮箱不能为空");
        }
        // 获取之前发送的验证码信息
        Map<String, Object> codeData = (Map<String, Object>) session.getAttribute("EMAIL_CODE" + email);
        // 检查是否发送过验证码
        if (codeData == null) {
            return RespResult.fail("尚未发送验证码");
        }
        // 获取发送的验证码和发送时间
        String sentCode = (String) codeData.get("code");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) codeData.get("time"));
        // 验证验证码是否超时（5分钟有效期）
        calendar.add(Calendar.MINUTE, 5);
        if (System.currentTimeMillis() > calendar.getTime().getTime()) {
            session.removeAttribute("EMAIL_CODE" + email);
            return RespResult.fail("验证码已经超时");
        }
        // 验证验证码是否正确
        if (!sentCode.equals(code)) {
            return RespResult.fail("验证码错误");
        }
        // 检查用户账户是否已经存在
        List<User> query = userService.query(User.builder().userAccount(user.getUserAccount()).build());
        if (Assert.notEmpty(query)) {
            return RespResult.fail("账户已被注册");
        }
        // 设置用户默认角色和头像路径，并保存用户信息
        user.setRoleStatus(0);
        user.setImgPath("https://moti-cloud-v2.oss-cn-beijing.aliyuncs.com/Snipaste_2022-05-01_15-37-01.png");
        user = userService.save(user);
        // 注册成功，将用户信息存入 session，并返回成功信息
        userController.loginUser=user;
        System.out.println(user);
        session.setAttribute("loginUser", user);
        return RespResult.success("注册成功", user);
    }

    /**
     * 用户登录
     *
     * @param user 包含用户账号和密码的用户对象
     * @return 登录结果，成功则返回成功信息，失败则返回相应的错误信息
     */
    @PostMapping("/login")
    public RespResult login(User user) {
        // 查询数据库中是否存在匹配的用户记录
        List<User> users = userService.query(user);
        // 如果找到匹配的用户记录，则表示登录成功
        if (Assert.notEmpty(users)) {
            session.setAttribute("loginUser", users.get(0));
            userController.loginUser=users.get(0);
            System.out.println(users.get(0));
            return RespResult.success("登录成功");
        }
        // 如果账户不存在，返回账户尚未注册的提示信息
        if (Assert.isEmpty(userService.query(User.builder().userAccount(user.getUserAccount()).build()))) {
            return RespResult.fail("账户尚未注册");
        }
        // 否则，返回密码错误的提示信息
        return RespResult.fail("密码错误");
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 目标邮箱地址
     * @param map   存储邮箱验证码和发送时间的 Map 对象
     * @return 发送结果，成功则返回成功信息，失败则返回相应的错误信息
     */
    @PostMapping("/sendEmailCode")
    public RespResult sendEmailCode(String email, Map<String, Object> map) {
        // 验证邮箱是否为空
        if (StrUtil.isEmpty(email)) {
            return RespResult.fail("邮箱不可为空");
        }
        // 发送邮箱验证码，并将验证码和发送时间存入 session
        String verifyCode = emailClient.sendEmailCode(email);
        map.put("email", email);
        map.put("code", verifyCode);
        map.put("time", new Date());
        session.setAttribute("EMAIL_CODE" + email, map);
        return RespResult.success("发送成功");
    }
}
