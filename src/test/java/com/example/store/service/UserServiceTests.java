package com.example.store.service;

import com.example.store.entity.User;
import com.example.store.mapper.UserMapper;
import com.example.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author JlX
 * @create 2022-04-08 16:25
 */
@SpringBootTest //标注当前的类是一个测试类，不会打包，打包的时候自动过滤

//表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {

    //idea有检测的功能，接口是不能够直接创建Bean的（动态代理技术来解决>|,所以给Autowired加一个requied=false
    @Autowired(required = false)
    IUserService userService;
    /**
     * 单元测试方法:就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被GTest;注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
        } catch (ServiceException e) {
            //获取异常的描述信息
            System.out.println(e.getMessage());
            //获取类的对象在获取类的名称
            System.out.println(e.getClass().getSimpleName());
        }
        System.out.println("用户插入成功");

    }
    @Test
    public void login(){
        User user= userService.login("test01", "1234");
        System.out.println(user);
    }
    @Test
    public void changePassword(){
        userService.changePassword(10,"test02","123","321");
    }
    @Test
    public void getByUid(){
        System.err.println(userService.getByUid(10));//err 输出为红字
    }
    @Test
    public void changeInfo(){
        User user = new User();
        user.setGender(1);
        user.setEmail("619@qq.com");
        user.setPhone("100000000");
        userService.changeInfo(8, "管理员", user);
    }

    @Test
    public void  changeAvatar(){

        userService.changeAvatar(11, "xiaoming", "/upload/test.png");
    }

}
