package my.springboot.myrest.controller;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll(); // DB 모든데이터 다 가져오기
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("board",new Board());
        return "board/form";
    }

    @PostMapping("/form")
    public String create(@ModelAttribute Board board,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){ //바인딩에러 있는 경우 에러메세지 출력
            System.out.println("=========================");
            bindingResult.getAllErrors().forEach(c->{
                System.out.println(c.toString());
            });
        }
        System.out.println(board.getTitle()+" , "+ board.getContent());
        boardRepository.save(board); // form에서 받은 board DB 저장
        return "redirect:/board/list"; // GET(board/list)으로 위임
    }
}
