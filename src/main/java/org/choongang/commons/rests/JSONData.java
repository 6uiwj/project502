package org.choongang.commons.rests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

//많이 쓰는 부분은 값을 고정
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONData<T> {
    private HttpStatus status = HttpStatus.OK;
    private boolean success = true; //성공여부
    private T data; //성공시데이터
    private String message;
}
