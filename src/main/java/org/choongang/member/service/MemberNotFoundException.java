package org.choongang.member.service;

import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends CommonException {
    //메시지와 응답코드 고정(404)
    public MemberNotFoundException() {
        super("등록된 회원이 아닙니다.", HttpStatus.NOT_FOUND);
    }
}
