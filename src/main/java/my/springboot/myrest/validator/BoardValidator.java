package my.springboot.myrest.validator;

import my.springboot.myrest.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {

    //supports(Class): 매개변수로 전달된 클래스를 검증할 수 있는지 여부를 반환
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    //validate(Object, org.springframework.validation.Errors)
    // : 매개변수로 전달된 객체를 검증하고 실패하면 Errors 객체에 에러를 등록한다.
    public void validate(Object target, Errors errors) {
        Board board = (Board)target;
        if(StringUtils.isEmpty(board.getContent())){ //content가 비었을 경우
            //키값이 없는 경우는 defalut값 사용
            errors.rejectValue("content","key","내용을 입력하세요");
        }
    }
}
