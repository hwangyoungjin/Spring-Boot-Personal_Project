package my.springboot.myrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "제목은 2자이상 30자이하여야 합니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") //User테이블의 pk를 참조하는 Board 테이블의 FK 이름
    private User user; //user_id는 User테이블의 PK를 참조하게 된다.

}
