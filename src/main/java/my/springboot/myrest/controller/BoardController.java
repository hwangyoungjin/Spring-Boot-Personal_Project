package my.springboot.myrest.controller;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.repository.BoardRepository;
import my.springboot.myrest.service.BoardService;
import my.springboot.myrest.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable ,
                       @RequestParam(required = false, defaultValue = "") String searchText){  // searchText 파라미터 필수아니므로 false
        // DB 모든데이터 page로 다 가져오기
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);

        //boards.getPageable().getPageNumber() : 페이지의 개수를 받아온다
        //boards.getTotalPages() : 페이지의 전체 개수를 받아온다.
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){ // id 파라미터 필수아니므로 false
        if(id == null){ //null인경우 새로운 글을 작성하는것이므로 객체 넣어준다.
            model.addAttribute("board",new Board());
        }
        else{
            //파라미터로 받은 id값 조회( JPA에서 지원) , orElse를 통해 못찾는경우 null반환
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String create(@Valid Board board,
                         BindingResult bindingResult,
                         Authentication authentication){

        boardValidator.validate(board, bindingResult);

        if(bindingResult.hasErrors()){ //바인딩에러 있는 경우 에러메세지 출력
            System.out.println("=========================");
            bindingResult.getAllErrors().forEach(c->{
                System.out.println(c.toString());
            });
            return "board/form";
        }

        String username = authentication.getName(); // 사용자의 유저네임을 받아온다.
        System.out.println(board.getTitle()+" , "+ board.getContent());
        boardService.save(username,board);
//        boardRepository.save(board); // form에서 받은 board 해당 객체의 key값(id)를 통해 DB 저장
        return "redirect:/board/list"; // GET(board/list)으로 위임
    }
}
