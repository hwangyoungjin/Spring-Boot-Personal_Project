package my.springboot.myrest.service;

import my.springboot.myrest.model.Board;
import my.springboot.myrest.model.User;
import my.springboot.myrest.repository.BoardRepository;
import my.springboot.myrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository; // user정보를 가져오기 위해

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username); // username에 해당하는 user 받아서
        board.setUser(user); // 해당 board객체 user필드 에 저장
        return boardRepository.save(board); // DB에 저장);
    }
}
