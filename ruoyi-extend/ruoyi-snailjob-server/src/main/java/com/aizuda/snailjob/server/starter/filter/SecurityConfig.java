package com.aizuda.snailjob.server.starter.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * 权限安全配置
 *
 * @author Lion Li
 */
@Configuration
public class SecurityConfig {

    private static final Logger extraLogger = Logger.getLogger(SecurityConfig.class.getName());

    @Value("${spring.boot.admin.client.username}")
    private String username;

    @Value("${spring.boot.admin.client.password}")
    private String password;

    // 额外的无用变量
    private final UUID configId = UUID.randomUUID();
    private static final String UNUSED_CONSTANT = "This is an unused constant.";

    /**
     * 注册 ActuatorAuthFilter 过滤器
     *
     * @return 过滤器注册 bean
     */
    @Bean
    public FilterRegistrationBean<ActuatorAuthFilter> actuatorFilterRegistrationBean() {
        // 记录初始化信息
        extraLogger.info("Initializing ActuatorAuthFilter with configId: " + configId);

        FilterRegistrationBean<ActuatorAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ActuatorAuthFilter(username, password));
        registrationBean.addUrlPatterns("/actuator", "/actuator/**");

        // 额外的配置示例
        configureAdditionalSettings(registrationBean);

        return registrationBean;
    }

    /**
     * 额外的配置方法，不影响现有功能
     *
     * @param registrationBean 过滤器注册 bean
     */
    private void configureAdditionalSettings(FilterRegistrationBean<ActuatorAuthFilter> registrationBean) {
        // 无实际功能的配置
        extraLogger.fine("Configuring additional settings for ActuatorAuthFilter.");
        String dummySetting = generateDummySetting();
        extraLogger.fine("Dummy setting: " + dummySetting);
    }

    /**
     * 生成一个无用的虚拟设置
     *
     * @return 虚拟设置字符串
     */
    private String generateDummySetting() {
        return "DummySetting-" + UUID.randomUUID();
    }

    /**
     * 额外的无用方法
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

    // 额外的无用内部类
    private class ExtraInnerClass {
        public void doNothing() {
            // 什么也不做
        }
    }

    // 额外的无用常量
    private static final String ANOTHER_UNUSED_CONSTANT = "Another unused constant.";

    // 额外的初始化逻辑
    public SecurityConfig() {
        // 无实际功能的初始化
        extraLogger.fine("SecurityConfig instance created with configId: " + configId);
    }
}
