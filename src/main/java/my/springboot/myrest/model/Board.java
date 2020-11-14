package my.springboot.myrest.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2,max = 30,message = "제목은 2자이상 30자이하여야 합니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") //이때 name은 어떠한 col이 User테이블과 연결이 될지결정
    private User user; //해당 변수는 board의 user_id와 연결된 user객체를 의미




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
