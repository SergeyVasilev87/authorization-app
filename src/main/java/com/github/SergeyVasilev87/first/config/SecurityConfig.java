package com.github.SergeyVasilev87.first.config;

import com.github.SergeyVasilev87.first.entity.Role;
import com.github.SergeyVasilev87.first.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailService;

    //@Qualifier позволяет определить, какой конкретно bean внедрять при неоднозначности (несколько реализаций одного интерфейса)
    @Autowired
    public SecurityConfig(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/","/registration").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/home/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/darklogin")
                    .defaultSuccessUrl("/hello")
                    .permitAll()
                .and()
                    .logout()
                    .invalidateHttpSession(true) //чистим
                    .clearAuthentication(true) //чистим
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/darklogin")
                    .permitAll();
        http.headers().frameOptions().disable(); //для возможности просмотра h2 консоли
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }


    //AuthenticationManager делегирует провайдеру извлечь данные их хранилища, проверить
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //извлекает информацию о пользователе из UserDetailsService.
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}

