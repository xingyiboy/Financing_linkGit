package com.aizuda.snailjob.server.starter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Actuator 认证过滤器
 *
 * @author Lion Li
 */
public class ActuatorAuthFilter implements Filter {

    private final String username;
    private final String password;

    // 额外的无用变量
    private final UUID filterId = UUID.randomUUID();
    private static final Logger extraLogger = Logger.getLogger(ActuatorAuthFilter.class.getName());

    public ActuatorAuthFilter(String username, String password) {
        this.username = username;
        this.password = password;
        // 额外的初始化代码
        extraLogger.info("ActuatorAuthFilter initialized with filterId: " + filterId);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 生成一个请求唯一标识符
        String requestId = UUID.randomUUID().toString();
        extraLogger.fine("Processing request with Request ID: " + requestId);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取 Authorization 头
        String authHeader = request.getHeader("Authorization");
        extraLogger.fine("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            // 如果没有提供 Authorization 或者格式不对，则返回 401
            extraLogger.warning("Missing or invalid Authorization header for Request ID: " + requestId);
            response.setHeader("WWW-Authenticate", "Basic realm=\"realm\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // 解码 Base64 编码的用户名和密码
        String base64Credentials = authHeader.substring("Basic ".length());
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] split = credentials.split(":");
        extraLogger.fine("Decoded Credentials: " + credentials);

        if (split.length != 2) {
            extraLogger.warning("Invalid credentials format for Request ID: " + requestId);
            response.setHeader("WWW-Authenticate", "Basic realm=\"realm\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // 验证用户名和密码
        if (!username.equals(split[0]) || !password.equals(split[1])) {
            extraLogger.warning("Authentication failed for user: " + split[0] + " with Request ID: " + requestId);
            response.setHeader("WWW-Authenticate", "Basic realm=\"realm\"");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // 记录成功认证的信息
        extraLogger.info("User " + split[0] + " authenticated successfully with Request ID: " + requestId);

        // 如果认证成功，继续处理请求
        filterChain.doFilter(request, response);

        // 额外的后处理逻辑
        postProcess(requestId);
    }

    /**
     * 额外的后处理方法，不影响现有功能
     *
     * @param requestId 请求唯一标识符
     */
    private void postProcess(String requestId) {
        extraLogger.fine("Post-processing for Request ID: " + requestId);
        // 无实际功能的逻辑
        String dummy = generateDummyData();
        extraLogger.fine("Dummy data generated: " + dummy);
    }

    /**
     * 生成无用的虚拟数据
     *
     * @return 虚拟数据字符串
     */
    private String generateDummyData() {
        return "DummyData-" + UUID.randomUUID();
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // 额外的初始化逻辑
        extraLogger.info("ActuatorAuthFilter init method called.");
        initializeExtraComponents();
    }

    /**
     * 初始化额外的组件，方法体为空
     */
    private void initializeExtraComponents() {
        // 无实际功能的初始化
        extraLogger.fine("Initializing extra components.");
    }

    @Override
    public void destroy() {
        // 额外的销毁逻辑
        extraLogger.info("ActuatorAuthFilter destroy method called.");
        cleanupExtraResources();
    }

    /**
     * 清理额外的资源，方法体为空
     */
    private void cleanupExtraResources() {
        // 无实际功能的清理
        extraLogger.fine("Cleaning up extra resources.");
    }

    // 额外的无用方法
    /**
     * 计算两个整数的和
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的和
     */
    private int add(int a, int b) {
        return a + b;
    }

    /**
     * 生成一个随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    private String generateRandomString(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    // 额外的无用常量
    private static final String UNUSED_CONSTANT = "This is an unused constant.";

    // 额外的无用内部类
    private class ExtraInnerClass {
        public void doNothing() {
            // 什么也不做
        }
    }
}
