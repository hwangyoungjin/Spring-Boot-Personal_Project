package my.springboot.myrest.service;

import my.springboot.myrest.config.WebSecurityConfig;
import my.springboot.myrest.model.Role;
import my.springboot.myrest.model.User;
import my.springboot.myrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User save(User user){ // service에서 패스워드인코더 와 enable값 넣고 저장
        String encodedPassword = passwordEncoder.encode(user.getPassword()); //패스워드 인코더
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();// Role테이블의 ROLE_USER을 검색해서 가져올 수 있지만 그냥 간편하게 하드코딩
        role.setId(Long.valueOf(11));
        user.getRoles().add(role); //해당 user를 save하면 user_role 테이블에 해당 user_id와 role_id가 저장된다
        return userRepository.save(user);
    }
}
