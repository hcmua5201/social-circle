package com.hello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;



//使用图床则此控制器无效
@Controller
@RequestMapping("/api/upload")
@CrossOrigin
public class FileUploadController {

    @Value("${upload.path}") // 设置文件上传目录的路径，可在application.properties或application.yml中配置
    private String uploadDirectory;

    @PostMapping("/image")
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(444,"Please select a file to upload.",null);
        }

        try {
            // 获取文件的原始名称
            String originalFileName = file.getOriginalFilename();

            // 构建文件在服务器端的存储路径
            String filePath = uploadDirectory + File.separator + originalFileName;

            System.out.println("存储路径"+filePath);
            // 将文件保存到服务器
            file.transferTo(new File(filePath));

            // 返回文件在服务器端的存储路径，你也可以返回其他信息
            return new Result(222,"File uploaded successfully" , filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(444,"File upload failed.",null);
        }
    }
}

