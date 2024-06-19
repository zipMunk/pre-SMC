package com.SmartMed_Connect.component;

import cn.hutool.core.util.RandomUtil; // 导入随机工具类
import org.springframework.beans.factory.annotation.Autowired;// 导入自动装配注解
import org.springframework.beans.factory.annotation.Value;// 导入配置值注解
import org.springframework.mail.javamail.JavaMailSenderImpl;// 导入邮件发送器类
import org.springframework.mail.javamail.MimeMessageHelper;// 导入邮件辅助类
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;// 导入邮件异常类
import javax.mail.internet.MimeMessage;// 导入邮件消息类

//用于发送邮件的 Java 组件，使用了 Spring Framework 的邮件发送功能。
@Component // 声明为 Spring 组件，交给 Spring 管理
public class EmailClient {

    @Autowired
    private JavaMailSenderImpl mailSender;// 自动注入邮件发送器对象

    /**
     * 发送方邮箱
     */
    @Value("${spring.mail.username}")
    private String email;

    /**
     * 有效时长
     */
    @Value("${spring.mail.valid}")
    private Integer valid;

    /**
     * 内容模版
     */
    @Value("${spring.mail.template}")
    private String template;

    /**
     * 标题
     */
    @Value("${spring.mail.title}")
    private String title;

    /**
     * 发送邮件验证码
     *
     * @param targetEmail 目标邮箱
     * @return 验证码
     */
    public String sendEmailCode(String targetEmail) {
        // 生成随机验证码
        String verifyCode = RandomUtil.randomNumbers(6);
        // 创建 MimeMessage 对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 使用 MimeMessageHelper 辅助类设置邮件内容
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            // 设置邮件标题
            helper.setSubject(title);
            // 设置邮件内容，使用模板填充验证码和有效时长
            helper.setText(String.format(template, verifyCode, valid), true);
            // 设置发件人邮箱
            helper.setFrom(email);
            // 设置收件人邮箱
            helper.setTo(targetEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送邮件
        mailSender.send(mimeMessage);
        // 返回生成的验证码
        return verifyCode;
    }

    /**
     * 发送邮箱
     *
     * @param targetEmail 目标邮箱
     * @param content     发送内容
     */
    public void sendEmail(String targetEmail, String title, String content) {
        // 创建 MimeMessage 对象
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 使用 MimeMessageHelper 辅助类设置邮件内容
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            // 设置邮件标题
            helper.setSubject(title);
            // 设置邮件内容
            helper.setText(content, true);
            // 设置发件人邮箱
            helper.setFrom(email);
            // 设置收件人邮箱
            helper.setTo(targetEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送邮件
        mailSender.send(mimeMessage);
    }
}
