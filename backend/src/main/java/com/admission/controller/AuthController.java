package com.admission.controller;

import com.admission.entity.User;
import com.admission.service.UserService;
import com.admission.util.JwtUtil;
import com.admission.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                return Result.error("用户名和密码不能为空");
            }

            // 验证用户
            User user = userService.login(username, password);
            if (user != null) {
                // 生成JWT token
                String token = jwtUtil.generateToken(username);

                // 构建返回数据（不包含密码）
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("username", user.getUsername());
                data.put("realName", user.getRealName());
                data.put("role", user.getRole());

                return Result.success(data);
            }

            return Result.error("用户名或密码错误");
        } catch (Exception e) {
            logger.error("登录失败: {}", e.getMessage(), e);
            return Result.error("登录失败，请重试");
        }
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        try {
            logger.info("开始注册用户: {}", user.getUsername());

            // 基本参数验证
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                return Result.error("用户名不能为空");
            }

            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return Result.error("密码不能为空");
            }

            if (user.getRealName() == null || user.getRealName().trim().isEmpty()) {
                return Result.error("真实姓名不能为空");
            }

            // 验证用户名长度
            if (user.getUsername().length() < 3 || user.getUsername().length() > 20) {
                return Result.error("用户名长度必须在3-20个字符之间");
            }

            // 验证密码长度
            if (user.getPassword().length() < 6 || user.getPassword().length() > 30) {
                return Result.error("密码长度必须在6-30个字符之间");
            }

            // 注册用户
            boolean success = userService.register(user);
            if (success) {
                logger.info("用户注册成功: {}", user.getUsername());
                return Result.success("注册成功");
            }

            return Result.error("用户名已存在，请选择其他用户名");
        } catch (Exception e) {
            logger.error("注册失败: {}", e.getMessage(), e);
            return Result.error("注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success("退出登录成功");
    }

    @GetMapping("/check-username/{username}")
    public Result<?> checkUsername(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            boolean available = user == null;
            Map<String, Object> data = new HashMap<>();
            data.put("available", available);
            data.put("message", available ? "用户名可用" : "用户名已存在");
            return Result.success(data);
        } catch (Exception e) {
            logger.error("检查用户名失败: {}", e.getMessage(), e);
            return Result.error("检查失败");
        }
    }
}