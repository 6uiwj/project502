package org.choongang.commons;

import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface ExceptionRestProcessor {
    @ExceptionHandler(Exception.class) //이컨트롤러에서 발생하는 모든 예외를 여기로 유입
    default ResponseEntity<JSONData<Object>> errorHandler(Exception e) {
        // 공통 응답코드 = 500
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //500
        if(e instanceof CommonException) {
            CommonException commonException = (CommonException)e;
            status = commonException.getStatus();

        }

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(false); //실패
        data.setStatus(status); //태코드가 변경됨
        data.setMessage(e.getMessage());

        e.printStackTrace();

        return ResponseEntity.status(status).body(data);


    }
}
