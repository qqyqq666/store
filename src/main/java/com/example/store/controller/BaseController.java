package com.example.store.controller;

/**
 * @author JlX
 * @create 2022-04-08 19:55
 */

import com.example.store.controller.ex.*;
import com.example.store.service.ex.*;
import com.example.store.utils.JsonResult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/** 控制器类的基类 */
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK = 200;
    /**
     * 从HttpSession对象中获取uid
     * @param session HttpSession对象
     * @return 当前登录的用户的id
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 从HttpSession对象中获取用户名
     * @param session HttpSession对象
     * @return 当前登录的用户名
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }


    /** @ExceptionHandler用于统一处理方法抛出的异常 */
    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    //′当前项目中产生了异常，被统一拦截到此方法中，这个方法此时就冲当的是请求处理方法，方法的返回值直接给到前端
    //下面两个异常类抛出异常，都会被拦截到此方法中
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<Void>(e);
        if (e instanceof UsernameDuplicateException) {
            result.setState(4000);
        }else if (e instanceof UserNotFoundException) {
            result.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
        }
        else if (e instanceof AddressCountLimitException) {
                result.setState(4003);
        }
        else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
        }
        else if (e instanceof AccessDeniedException1) {
            result.setState(4005);
        }
        else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
        }
         else if (e instanceof InsertException) {
            result.setState(5000);
        }
        else if (e instanceof DeleteException) {
            result.setState(5002);
        }else if (e instanceof UpdateException) {
            result.setState(5003);
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }
}
