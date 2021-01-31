package my.springboot.myrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //mappedBy는 User 클래스의 선언한 @ManyToMany의 roles 설정내용을 동일하게 적용하겠다는 의미
    // => 양방향 맵핑으로 Role테이블을 조회하면 조인된 상대방 테이블(User)도 같이 조회한다.
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
