package com.example.store.mapper;

/**
 * @author JlX
 * @create 2022-04-08 15:19
 */

import com.example.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
/** 处理用户数据操作的持久层接口 */
public interface UserMapper {
    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数(( 增 、 删 、 改 ， 都受影响的行数作为返回值 ， 可以根据返回值来判断是否执行成功)
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(String username);

    /**
     * 根据uid更新用户的密码
     * @param uid 用户的id
     * @param password 新密码
     * @param modifiedUser 最后修改执行人
     * @param modifiedTime 最后修改时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
    /**
     * 根据用户id查询用户数据
     * @param uid 用户id
     * @return 匹配的用户数据，如果没有匹配的用户数据，则返回null
     */
    User findByUid(Integer uid);

    /**
     * 根据uid更新用户资料
     * @param user 封装了用户id和新个人资料的对象
     * @return 受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid更新用户的头像
     * @param uid 用户的id
     * @param avatar 新头像的路径
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     *
     * @Param("SOL映射文件中#f占位符的变量名"):解决的问题，当SQL语句的占位符和映射的接口方法参数名不一致时，
     * 需要将某个参数强行注入到某个占位符变量上时,可以使用@Param这个注解来标注映射的关系
     *
     *
     * @Param("uid") (xml文件中的属性注射到参数上
     *)
     */
    Integer updateAvatarByUid(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
}
