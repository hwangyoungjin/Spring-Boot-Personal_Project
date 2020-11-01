package my.springboot.myrest.repository;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //JPA 규칙에 따라 인터페이스만 정의하면 JPA가 알아서 조회해준다.



}
