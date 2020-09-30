package my.springboot.myrest.repository;

import my.springboot.myrest.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    //JPA 규칙에 따라 인터페이스만 정의하면 JPA가 알아서 조회해준다.

    //title과 일치하는 데이터 반환
    List<Board> findBytitle(String title);

    // title이나 content둘중 하나만 일치해도 데이터 반환
    List<Board> findByTitleOrContent(String title, String content);
}
