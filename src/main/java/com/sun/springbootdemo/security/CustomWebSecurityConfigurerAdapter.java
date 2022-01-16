package com.sun.springbootdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p> 定制spring - security </p>
 *
 * @author Sundz
 * @date 2021/4/11 11:06
 * @field 认证  --> 你是谁？你能做什么？
 * @field 授权  -- > 你可以做什么？有什么权限？
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 拦截@PreAuthorize
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private SecurityFilter filter;


    /**
     * @field 认证  --> 你是谁？你能做什么？
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 授权供给者
        auth.userDetailsService(customUserDetailsService);
       /* // 基于内存
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 角色
        authorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
        UserDetails userDetails = new User("guest", passwordEncoder().encode("123456"), authorities);
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                // 账号对应的角色
                .roles("ADMIN", "NORMAL")
                .and()
                .withUser(userDetails);*/


    }


    /**
     * @field 授权  -->你可以做什么？有什么权限？
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 默认可以访问没有设置权限的接口
        http.authorizeRequests()
                // 根路径都可以看到所有
                .antMatchers("/noLogin/**", "/userOp/**").permitAll()
                // admin路径下能admin用户能看到的
                //.antMatchers("**/admin/**")
                //.hasRole("admin")
                // normal路劲下能normal用户能看到的
                //.antMatchers("**/normal/**")
                //.hasRole("normal")
                // 任何请求都需要认证
                .anyRequest()
                .authenticated()
                .and().formLogin()/*.and().addFilterAfter(filter, BasicAuthenticationFilter.class)*/
                // RESTful技术与CSRF(Cross-site request forgery跨站请求伪造)的冲突造成的，CSRF默认支持的方法： GET|HEAD|TRACE|OPTIONS，不支持POST
                // 可以在security在配置中禁用掉它
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
