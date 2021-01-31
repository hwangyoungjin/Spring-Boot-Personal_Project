package my.springboot.myrest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role", // ManyToMany 조인된 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // 해당테이블의 컬럼이름
            inverseJoinColumns = @JoinColumn(name = "role_id")) // 조인될 상대테이블(User가아닌)의 컬럼
    //user_role테이블은 User에겐 role_id가 추가된 형태
    private List<Role> roles = new ArrayList<>();

    //양방향 설정
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true) //many 쪽인 Board의 User객체 필드명
    private List<Board> boards = new ArrayList<>();


    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
