package com.ctmill.service;

import com.ctmill.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zyaire
 * @since 2023-04-18
 */
public interface IUserService extends IService<User> {
    /**
     * 登录
     * @param user
     * @return
     */
    Map<String, Object> login(User user);

    /**
     * 添加用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 登录根据token获取用户信息
     * @param token
     * @return
     */
    Map<String, Object> getUserInfo(String token);

    /**
     * 分页查询用户数据
     * @param username
     * @param phone
     * @param pageNum
     * @param pageSize
     * @return
     */
    Map<String,Object> getPageUserList(String username,String phone,Long pageNum,Long pageSize);
    /**
     * 注销登录
     * @param token
     */
    void logout(String token);

    void addUserAndRole(User user);

    User getUserById(Integer id);

    void updateUserById(User user);

    void deleteUserById(Integer id);
}
