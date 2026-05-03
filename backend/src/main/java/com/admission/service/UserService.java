package com.admission.service;

import com.admission.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(String username);

    /**
     * 注册用户
     * @param user 用户信息
     * @return 注册成功返回true，失败返回false
     */
    boolean register(User user);

    /**
     * 验证用户登录
     * @param username 用户名
     * @param password 密码
     * @return 验证成功返回用户对象，失败返回null
     */
    User login(String username, String password);
}