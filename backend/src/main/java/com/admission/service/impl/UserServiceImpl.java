package com.admission.service.impl;

import com.admission.entity.User;
import com.admission.mapper.UserMapper;
import com.admission.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder; // 使用接口类型，不是具体实现类

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return this.getOne(wrapper);
    }

    @Override
    @Transactional
    public boolean register(User user) {
        try {
            // 检查用户名是否已存在
            if (findByUsername(user.getUsername()) != null) {
                throw new RuntimeException("用户名已存在");
            }

            // 密码加密
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 设置创建时间和更新时间
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());

            // 默认角色为普通用户
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER");
            }

            // 保存用户
            boolean result = this.save(user);
            if (!result) {
                throw new RuntimeException("用户注册失败");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 重新抛出异常，让事务回滚
        }
    }

    @Override
    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}