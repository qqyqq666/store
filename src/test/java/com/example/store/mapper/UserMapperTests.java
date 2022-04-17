package com.example.store.mapper;

import com.example.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author JlX
 * @create 2022-04-08 16:25
 */
@SpringBootTest //标注当前的类是一个测试类，不会打包，打包的时候自动过滤

//表示启动这个单元测试类(单元测试类是不能够运行的)，需要传递一个参数，必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {

    //idea有检测的功能，接口是不能够直接创建Bean的（动态代理技术来解决>|,所以给Autowired加一个requied=false
    @Autowired(required = false)
    UserMapper userMapper;
    /**
     * 单元测试方法:就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被GTest;注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);

    }
    @Test
    public void findUser(){

        User tim = userMapper.findByUsername("tim");
        System.out.println(tim);

    }

    @Test
    public void updatePasswordByUid(){
        Integer result = userMapper.updatePasswordByUid(6, "321", "自己改的", new Date());

        System.out.println(result);

    }
    @Test
    public void findByUid(){

        User tim = userMapper.findByUid(6);
        System.out.println(tim);

    }
    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(10);
        user.setUsername("test02");
        user.setPhone("99999999");
        user.setEmail("5555@qq.com");
        user.setGender(1);
        Integer result = userMapper.updateInfoByUid(user);
        System.out.println(result);

    }

    @Test
    public void updateAvatarByUid(){

       userMapper.updateAvatarByUid(11,
               "/updata/user", "系统管理员", new Date());

    }
}
