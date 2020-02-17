package com.spring.security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Cavan.Liu
 * @date: 2019-11-10 14:06:17
 * @description: 继承WebSecurityConfigurerAdapter，并覆盖一些方法来设置Web安全配置的一些细节
 */
@Configuration
@EnableWebSecurity      // 启用Spring Security的Web安全支持，并提供Spring MVC集成
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Http security config
     * 定义需要被保护、可直接访问的URL路径
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http.authorizeRequests()
            // 授权访问 [/]与[/home] 目录
                .antMatchers("/", "/home")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            // 登录表单
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            // 登出
            .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置后用户无法正常登录
        //super.configure(auth);

        // 密码编码器
        PasswordEncoder pwdEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("user")
                .password(pwdEncoder.encode("111111"))
                .roles("USER");
    }
}
