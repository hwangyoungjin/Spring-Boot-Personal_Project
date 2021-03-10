package my.springboot.myrest.service;

import my.springboot.myrest.config.AccountContext;
import my.springboot.myrest.config.WebSecurityConfig;
import my.springboot.myrest.model.Role;
import my.springboot.myrest.model.User;
import my.springboot.myrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public User save(User user){ // service에서 패스워드인코더 와 enable값 넣고 저장
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //패스워드 인코더
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();// Role테이블의 ROLE_USER을 검색해서 가져올 수 있지만 그냥 간편하게 하드코딩
        role.setId(Long.valueOf(1));
        user.getRoles().add(role); //해당 user를 save하면 user_role 테이블에 해당 user_id와 role_id가 저장된다
        return userRepository.save(user);
    }

    /**
     * 로그인시 인증처리
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsernameWithRole(s);

        User user = users.get(0);

        //존재하지않는경우
        if(user == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }


        //존재하는경우 권한 설정
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(role ->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());


        //UserDetails 타입 객체 만들어서 반환
        AccountContext accountContext = new AccountContext(user,roles);
        return accountContext;
    }
}
