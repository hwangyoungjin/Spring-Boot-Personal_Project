package my.springboot.myrest.controller;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.repository.BoardRepository;
import my.springboot.myrest.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll(); // DB 모든데이터 다 가져오기
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){ // 파라미터 필수아니므로 false
        if(id == null){
            model.addAttribute("board",new Board());
        }
        else{
            //파라미터로 받은 id값 조회(JPA에서 지원) , orElse를 통해 못찾는경우 null반환
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String create(@Valid Board board,
                         BindingResult bindingResult){

        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()){ //바인딩에러 있는 경우 에러메세지 출력
            System.out.println("=========================");
            bindingResult.getAllErrors().forEach(c->{
                System.out.println(c.toString());
            });
            return "board/form";
        }
        System.out.println(board.getTitle()+" , "+ board.getContent());
        boardRepository.save(board); // form에서 받은 board 해당 객체의 key값(id)를 통해 DB 저장
        return "redirect:/board/list"; // GET(board/list)으로 위임
    }
}