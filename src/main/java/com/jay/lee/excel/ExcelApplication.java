package com.jay.lee.excel;

import com.jay.lee.excel.entity.User;
import com.jay.lee.excel.util.ExcelUtils;
import com.jay.lee.excel.util.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
@Api(tags = "测试excel")
@EnableSwagger2
public class ExcelApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelApplication.class, args);
    }

    @ApiOperation("导出")
    @GetMapping("/export")
    public void testExport(HttpServletResponse response){
        List<User> list = new ArrayList<>();
//        User jay = User.builder()
//                .age(18)
//                .birthDay(LocalDateTime.now())
//                .id(1l)
//                .name("JAY")
//                .sex(true)
//                .state(0)
//                .build();
//        list.add(jay);
        ExcelUtils.export(list,response,User.class,"测试");
    }

    @PostMapping("/upload")
    @ApiOperation("上传")
    public String upload(@RequestParam("file")MultipartFile file){
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        List<User> users = ExcelUtils.readExcel(inputStream, User.class);
        System.out.println(JsonUtils.writeValueAsString(users));
        return JsonUtils.writeValueAsString(users);
    }

}
