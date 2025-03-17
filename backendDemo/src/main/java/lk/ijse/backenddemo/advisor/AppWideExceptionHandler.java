package lk.ijse.backenddemo.advisor;

import lk.ijse.backenddemo.util.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseUtil exceptionHandler(Exception ex){
        ex.printStackTrace();
        return new ResponseUtil(500, "Internal Server Error", ex.getMessage());
    }

}
