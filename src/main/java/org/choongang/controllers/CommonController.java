package org.choongang.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.choongang.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice("org.choongang.controllers")
public class CommonController {
    //모든 예외를 여기에 넣어줄 것
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletResponse response, HttpServletRequest request, Model model) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        //commonException 형태만 가져와서 응답코드 체크
        if(e instanceof CommonException) {
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus();
        }

        response.setStatus(status.value());
        //콘솔에 에러 출력
        e.printStackTrace();

        model.addAttribute("status",status.value());
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("metod",request.getMethod());
        model.addAttribute("message",e.getMessage());

            return "error/common";
    }
}
