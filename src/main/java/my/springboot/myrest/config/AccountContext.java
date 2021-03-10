package my.springboot.myrest.config;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class AccountContext extends User {
    /**
     * 나중의 User상속받을 수 있도록
     */
    private final my.springboot.myrest.model.User user;

    public AccountContext(my.springboot.myrest.model.User myuser,
                          Collection<? extends GrantedAuthority> authorities) {
        super(myuser.getUsername(),myuser.getPassword(), authorities);
        this.user = myuser;
    }
}
