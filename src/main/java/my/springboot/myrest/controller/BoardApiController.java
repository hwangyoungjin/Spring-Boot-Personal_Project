package my.springboot.myrest.controller;

import java.util.List;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@RestController
@RequestMapping("/api")
public class BoardApiController {

    @Autowired
    private BoardRepository repository;

    //Aggregate root

    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false) String title,
                    @RequestParam(required = false) String content) {
        //required = false 이면 파라미터 안들어와도 에러x (true가 기본값 = 안들어오면 에러!)
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) { //둘다 전달이 안 된경우
            return repository.findAll();
        }
        else { //title 이나 content 둘중 하나만이라고 전달이 된 경우
            //http://localhost:8080/api/boards?title=Hello 으로 요청시 title이 Hello인 데이터 json으로 반환
            //http://localhost:8080/api/boards?content=young 으로 요청시 content가 young인 데이터 json으로 반환
            //http://localhost:8080/api/boards?title=Hello&content=123123 으로 요청시
            // title이 Hello이거나 content가 123123인 객체 반환
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    Board newEmployee(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item
    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        //http://localhost:8080/api/boards/19 으로 요청시 id 19에 해당하는 데이터 json으로 반환
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceEmployee(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}