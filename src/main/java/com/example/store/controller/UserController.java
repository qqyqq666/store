package com.example.store.controller;

/**
 * @author JlX
 * @create 2022-04-08 19:41
 */

import com.example.store.controller.ex.*;
import com.example.store.entity.Address;
import com.example.store.entity.User;
import com.example.store.service.IUserService;
import com.example.store.service.ex.InsertException;
import com.example.store.service.ex.UsernameDuplicateException;
import com.example.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** 处理用户相关请求的控制器类 */
@RestController   //RestController  = Controller+Responsebody
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    IUserService userService;

   /*  没继承父类之前这么做  处理异常繁琐
   //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给到前端
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        //创建相应结果对象
        JsonResult<Void> result = new JsonResult<>();
        // 调用业务对象执行注册
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicateException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");

        }catch (InsertException e) {
            result.setState(5000);
            result.setMessage("在注册是产生未知的异常");

        }
        // 返回
        return result;
    }*/

    /**
     * 1.接收数据方式:请求处理方法的参数列表设置为pojo类型来接收前端的数据
     * SpringBoot会将前端的url地址中的参数名和pojo类的属性名进行比较，如果这两个名称相同，
     * 则将值注入到pojo类中对应的属性上
     * @param user
     * @return
     */
   @RequestMapping("reg")
   public JsonResult<Void> reg(User user) {
       // 调用业务对象执行注册
       userService.reg(user);
       // 返回
       return new JsonResult<Void>(OK);
   }

    /**
     * 2.接收数据方式:请求处理方法的参数列表设置为非pojo类型(String,int.....)
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较，如果名称
     *相同则自动完成值的依赖注入
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        // 调用业务对象的方法执行登录，并获取返回值
       User data = userService.login(username, password);

        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        // 将以上返回值和状态码OK封装到响应结果中并返回
       return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
        // 调用session.getAttribute("")获取uid和username
        Integer uid = getUidFromSession(session);//在父类封装了此方法
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改密码
        userService.changePassword(uid, username, oldPassword, newPassword);
        // 返回成功
        return new JsonResult<Void>(OK);
    }
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession httpSession){
        User data = userService.getByUid(getUidFromSession(httpSession));
        return new JsonResult<>(OK,data);
    }
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        // 从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);
        // 响应成功
        return new JsonResult<Void>(OK);
    }

    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<String>();
    //给集合做初始化可以用静态代码块来执行
    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    /**
     *MultipartFile接口是SpringMVC提供一个接口，这个接口为我们包装了获取文件类型的数据(任何类型的file都可以接收)，SpringBoot它有整合了SpringMVC，
     * 只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，
     * 然后SpringBoot自动将传递给服务的文件数据赋值赋值给这个参数
     *
     *
     * @param file   @RequestParam：解决表单请求参数和参数名不一致的情况   还有一个注解是pathvariable
     * @param session MultipartFile：SpringMvc为我们提供的接口
     * @return
     */
    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file,
                                           HttpSession session){
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new FileEmptyException("上传的头像文件不允许为空");
        }
        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE){// getSize()：返回文件的大小，以字节为单位
            throw new FileSizeException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }
        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        // boolean contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false
        if (!AVATAR_TYPES.contains(contentType)) {
            throw new FileTypeException("不支持使用该类型的文件作为头像，允许的文件类型：" + AVATAR_TYPES);
        }
        // 获取当前项目的绝对磁盘路径
        String parent = session.getServletContext().getRealPath("upload");
        System.out.println(parent);

        // File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {//不存在
            dir.mkdirs();//创建当前目录
        }
        // 保存的头像文件的文件名
        String suffix = "";
        //获取到这个文件名称，UUID工具来将生成一个新的字符串作为文件名
        //例如: avatar01.png
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);//先保存后缀
        }
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        // 创建文件对象(空的)，表示保存的头像文件
        File dest = new File(dir, filename);
        // 执行保存头像文件
        try {
            file.transferTo(dest);//将参数file中的数据写到dest文件中
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new FileStateException("文件状态异常，可能文件已被移动或删除");
        } catch (IOException e) {
            // 抛出异常
            throw new FileUploadIOException("上传文件时读写错误，请稍后重新尝试");
        }

        // 返回头像路径（相对路径即可）
        String avatar = "/upload/" + filename;
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        // 将头像写入到数据库中
        userService.changeAvatar(uid, username, avatar);

        // 返回成功头像路径
        return new JsonResult<String>(OK, avatar);
    }

}
