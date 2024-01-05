package org.choongang.commons.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

//많이 쓰는 부분은 값을 고정
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JSONData<T> {
    private HttpStatus status = HttpStatus.OK;
    private boolean success = true; //성공여부

    @NonNull //데이터는 상수가 아니라 바꿀 수 있기 때문에 nonnull을ㄴ ㅓㅎ는다구...?
    private T data; //성공시데이터
    private String message;
}
