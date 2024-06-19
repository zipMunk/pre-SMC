package com.SmartMed_Connect.component;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * 阿里云 OSS 客户端组件，用于文件上传
 */
@Component
public class OssClient {
    // 从配置文件中读取 OSS 的 bucket 名称
    @Value("${oss.bucket-name}")
    private String bucketName;
    // 从配置文件中读取 OSS 的 endpoint
    @Value("${oss.end-point}")
    private String endPoint;
    // 从配置文件中读取 OSS 的访问键 ID
    @Value("${oss.access-key}")
    private String accessKeyId;
    // 从配置文件中读取 OSS 的访问密钥
    @Value("${oss.access-secret}")
    private String accessKeySecret;

    /**
     * 上传文件到 OSS
     *
     * @param file 要上传的文件
     * @param path 文件上传到 OSS 中的路径
     * @return 上传文件的 URL 地址
     * @throws IOException 可能抛出的 IO 异常
     */
    public String upload(MultipartFile file, String path) throws IOException {
        // 检查文件和路径是否为空
        if (file == null || path == null) {
            return null;
        }
        // 创建 OSS 客户端实例
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        // 检查 bucket 是否存在，如果不存在则创建
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
        // 获取文件的扩展名
        String extension = OssClient.getFileExtension(file);
        // 生成文件路径，使用 UUID 保证文件名唯一
        String fileUrl = path + "/" + IdUtil.simpleUUID() + extension;
        // 生成文件的完整 URL
        String url = "https://" + bucketName + "." + endPoint + "/" + fileUrl;
        // 上传文件到 OSS
        PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file.getInputStream()));
        //上传文件
        // 设置 bucket 的访问权限为公共读
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        // 返回文件的 URL 地址
        return url;
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 要获取扩展名的文件
     * @return 文件的扩展名
     */
    public static String getFileExtension(MultipartFile file) {
        // 获取文件的原始名称
        String filename = file.getOriginalFilename();
        // 确保文件名不为空
        assert filename != null;
        // 返回文件的扩展名（包含点号）
        return filename.substring(filename.lastIndexOf("."));
    }
}
