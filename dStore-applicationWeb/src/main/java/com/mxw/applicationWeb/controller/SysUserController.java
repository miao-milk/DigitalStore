package com.mxw.applicationWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.mxw.common.model.dto.UserDTO;
import com.mxw.common.model.dto.UserInfoDTO;
import com.mxw.common.model.entity.UserInfoDO;
import com.mxw.common.utils.Result;
import com.mxw.common.utils.UpPhotoNameUtils;
import com.mxw.user.api.UserService;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Api(value = "系统用户管理", tags = "系统用户管理", description = "系统用户管理")
@RestController
public class SysUserController {

    @Reference
    UserService userService;


    @PostMapping("/register")
    @ApiOperation("注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserDTO", value = "用户信息", dataType = "UserDTO", paramType = "body"),
    })
    public Result register(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return Result.ok("注册成功");
    }


    String  UPLOAD_PATH="D:\\image\\";

    /**
     * 上传图片接口
     * */
    @PostMapping("/upload")
    public Result singleFileUpload(MultipartFile file,
                                           HttpServletRequest request) {
        try {
            byte[] bytes = file.getBytes();
            String imageFileName = file.getOriginalFilename();
            String fileName = UpPhotoNameUtils.getPhotoName("img",imageFileName);
            Path path = Paths.get(UPLOAD_PATH + fileName);
            Files.write(path, bytes);
            //获得上传文件的名称
//            String originalFilename = file.getOriginalFilename();
//            file.transferTo(new File("C:\\upload\\"+originalFilename));
            System.out.println(fileName+"\n");
            return Result.ok("上传成功").put("fileName",fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("上传失败");
    }


    //使用流将图片输出
    @GetMapping("/getImage/{name}")
    public void getImage(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
        response.setContentType("image/jpeg;charset=utf-8");
        response.setHeader("Content-Disposition", "inline; filename=girls.png");
        ServletOutputStream outputStream = response.getOutputStream();
        if (!name.contains(".jpg")){
            name=name+".jpg";
        }
        outputStream.write(Files.readAllBytes(Paths.get(UPLOAD_PATH).resolve(name)));
        outputStream.flush();
        outputStream.close();
    }


    @PostMapping("/saveUserInfo")
    @ApiOperation("保存用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserInfoDO", value = "用户详情信息", dataType = "UserDTO", paramType = "body"),
    })
    public Result saveUserInfo(@RequestBody @ApiParam(name="用户对象",value="传入json格式",required=true) String params){
        String sellerId="2";
        UserInfoDTO userInfoDTO = JSONObject.parseObject(params, UserInfoDTO.class);
        userService.saveUserInfo(userInfoDTO,sellerId);
        return Result.ok("修改成功");
    }


    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    public Result getUserInfo(){
        String sellerId="2";
        UserInfoDTO userInfoDTO= userService.getUserInfo(sellerId);
        return Result.ok("获取用户详情").put("data",userInfoDTO);
    }
}
