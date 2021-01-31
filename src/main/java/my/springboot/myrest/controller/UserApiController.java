package my.springboot.myrest.controller;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.model.User;
import my.springboot.myrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserRepository repository;

    //Aggregate root

    @GetMapping("/users")
    List<User> all() {
            return repository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        //http://localhost:8080/api/Users/19 으로 요청시 id 19에 해당하는 데이터 json으로 반환
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> { //User 테이블에서 조회한 user에 대해
//                    user.setBoards(newUser.getBoards());//json으로 받은 newUser의 board 정보 저장
                      user.getBoards().clear();
                      user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()){//해당 board 정보에 대해
                        board.setUser(user); // 다시 user에 저장
                    }
                    return repository.save(user); // DB에 user저장 -> cascade로 board에도 저장된다.
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}