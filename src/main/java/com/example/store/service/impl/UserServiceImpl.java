package com.example.store.service.impl;

import com.example.store.entity.User;
import com.example.store.mapper.UserMapper;
import com.example.store.service.IUserService;
import com.example.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author JlX
 * @create 2022-04-08 18:45
 */
/** 处理用户数据的业务层实现类 */
@Service// @service注解:将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 补全数据：加密后的密码
        //（串 +password +串) ---- md5算法进行加密，连续加载三次
        // 盐值+password+盐值—---盐值就是一个随机的字符串
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将密码和盐值作为一个整体进行加密处理
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1  等于1 说明插入成功啊
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new InsertException("添加用户数据出现未知错误，请联系系统管理员");
        }

    }

    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户的数据是否存在，如果不在则抛出异常
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }

        // 判断查询结果中的isDelete是否为1
        if (result.getIsDelete() == 1) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }
        //检测用户密码是否匹配
        //1.先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户的传递过来的密码进行比较
        // 2.1先获取盐值:上一次在注册时所自动生成的盐值
        String salt = result.getSalt();
        //2.2将用户的密码按照相同的md5算法的规则进行加密
        String newPassword = getMd5Password(password, salt);
        //3．将密码进行比较
        if(!newPassword.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());//getAvatar头像字段
        // 返回新的user对象
        return user;

    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        //检查查询结果是否为null或者被删除
        if(result==null & result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中的密码进行比较
        String oldMd5password = getMd5Password(oldPassword, result.getSalt());
        if(!result.getPassword().equals(oldMd5password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMd5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(result.getUid(), newMd5Password, username, new Date());
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }


    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 创建新的User对象
        User user = new User();
        // 将以上查询结果中的username/phone/email/gender封装到新User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        // 返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete()==1 ) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在");
        }
        // 向参数user中补全数据：uid
        user.setUid(uid);
        // 向参数user中补全数据：modifiedUser(username)
        user.setModifiedUser(username);
        // 向参数user中补全数据：modifiedTime(new Date())
        user.setModifiedTime(new Date());
        // 调用userMapper的updateInfoByUid(User user)方法执行修改，并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        // 判断以上返回的受影响行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误，请联系系统管理员");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        // 调用userMapper的findByUid()方法，根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 创建当前时间对象
        Date now = new Date();
        // 调用userMapper的updateAvatarByUid()方法执行更新，并获取返回值
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, now);
        if (rows != 1) {
            throw new UpdateException("用户头像更新异常");
        }
    }

    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            //md5加密算法方法的调用(进行三次加密)
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //将密码和盐值作为一个整体进行加密处理，忽略原有密码强度提升了数据的安全松
        return password;
    }
}
