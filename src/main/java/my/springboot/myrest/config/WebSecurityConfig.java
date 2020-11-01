package my.springboot.myrest.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // application.properties에 있는 정보를 인스턴스로 받아온다.
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //어떤 보안설정을 할것인지 정한다.
                .authorizeRequests()
                    //css 경로 추가
                    .antMatchers("/","/css/**").permitAll() // permitAll을 통해 누구나 접근 할 수 있다고 설정
                    .anyRequest().authenticated() // home이 아닌 요청은 모두 authenticate(로그인)가 있어야만 볼 수 있도록
                    .and()//이어서
                .formLogin()//로그인설정
                    .loginPage("/account/login")//로그인 폼 클릭시 자동으로 redirect 되어 login 폼으로 이동
                    .permitAll() // 로그인 되지 않은 사용자이므로 모두 접근 가능하도록
                    .and()//이어서
                .logout()//로그아웃
                    .permitAll();
    }


    /**
     * 참고
     * Authentication : 로그인의 관한 설정
     * Authroization : 권한의 관한 설정
     */
    @Autowired
    //만든 User테이블을 AuthenticationManagerBuilder 설정을 통해 스프링이 알아서 인증처리
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) //스프링이 해당 dataSource를 사용하여 인증처리
                .passwordEncoder(passwordEncoder()) // 스프링에서 제공하는 passwordEncoder 적용하여 알아서 pw 암호화
                .usersByUsernameQuery("select username, password, enabled "
                        + "from user "
                        + "where username = ?") // 파라미터에 알아서 username이 들어간다.
                .authoritiesByUsernameQuery("select username, name "
                        + "from user as u, role as r " );
//                        + "where username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
