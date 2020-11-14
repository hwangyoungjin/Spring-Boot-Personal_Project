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
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
