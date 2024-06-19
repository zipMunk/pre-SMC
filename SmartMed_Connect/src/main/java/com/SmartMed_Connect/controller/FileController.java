package com.SmartMed_Connect.controller;

import com.SmartMed_Connect.dto.RespResult;
import com.SmartMed_Connect.entity.User;
import com.SmartMed_Connect.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.SmartMed_Connect.component.OssClient;

import java.io.IOException;

/**
 * FileController 类用于处理文件上传的请求
 */
@RestController
//标记类为控制器：声明该类是一个 Spring MVC 控制器，使其能够处理 HTTP 请求。
//自动将返回值转换为 JSON/XML：所有方法返回的对象会自动序列化为 JSON 或 XML，并作为 HTTP 响应返回给客户端。无需在每个方法上添加 @ResponseBody 注解。
@RequestMapping("/file")
//设置基路径：将类中的所有处理方法的请求路径前缀设置为 /file。例如，如果类中的某个方法映射为 /upload，实际的请求路径将是 /file/upload。
//请求路径映射：定义该类中的方法处理特定路径的 HTTP 请求。
public class FileController extends BaseController<User> {

    @Autowired
    private OssClient ossClient;

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 响应结果，包含文件的 URL 或错误信息
     * @throws IOException 文件上传过程中可能抛出的 IO 异常
     */
    @PostMapping("/upload")
    public RespResult upload(@RequestParam("file") MultipartFile file) throws IOException {
        //        @RequestParam("file"):
        //        @RequestParam 注解用于从请求中提取参数。
        //        "file" 表示请求中参数的名称。在这个例子中，前端表单中的文件字段应命名为 file。
        //        该注解确保 HTTP 请求中的 file 参数值被绑定到 upload 方法的 file 参数上。
        //        MultipartFile file:
        //        MultipartFile 是 Spring 框架提供的接口，用于处理文件上传。
        //        file 是方法参数名，用于接收上传的文件。
        //        RespResult 通常是一个用于封装 API 响应结果的类。它可以包含响应状态、消息、数据等信息，方便后端向前端返回一致的响应格式。这个类可以用于统一管理成功和失败的响应

        // 使用 OssClient 上传文件，并获取文件的 URL
        String url = ossClient.upload(file, String.valueOf(loginUser.getId()));
        // 检查 URL 是否为空
        if (Assert.isEmpty(url)) {
            // 如果为空，返回上传失败的响应结果
            return RespResult.fail("上传失败", url);
        }
        // 如果不为空，返回上传成功的响应结果
        return RespResult.success("上传成功", url);
    }
}
