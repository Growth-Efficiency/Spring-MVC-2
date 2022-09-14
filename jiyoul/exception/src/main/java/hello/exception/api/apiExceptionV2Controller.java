package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Slf4j
@RestController
public class apiExceptionV2Controller {

    // 예외가 발생하면 ExceptionHandlerExceptionResolver 가 제일 먼저 실행되고 그안에서 이 @ExceptionHandler 가 가장 먼저 호출 됌.
    // 이 컨트롤러에서 발생한 IllegalArgumentException 예외는 잡아서 바로 @RestController 이기 때문에 바로 ErrorResult 를 json 으로 반환함.
    //@ResponseStatus(HttpStatus.BAD_REQUEST) // @ResponseStatus를 사용해서 400 에러로 만들어줄수도 있음. (에러를 잡아서 json으로 반환 했기때문에 정상 흐름으로 되어 200이 반환됨)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler // == @ExceptionHandler(UserException.class) 인자와 동일하다면 생략가능.
    public ResponseEntity<ErrorResult> userExHandler(UserException e) { //UserException 을 상속한 자식 예외까지 잡을 수 있음.
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) { // 위 두 @ExceptionHandler 에서 잡지 못하는 예외는 모두 요기서 잡을 수도 있음. (하지만 더 디테일 한 Exception 이 우선권을 가짐.)
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }

}
