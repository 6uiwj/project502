package org.choongang.board.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.board.Repositories.BoardDataRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardDataRepository boardDataRepository;

    @ResponseBody //void로 넣어서 응답내용은 없어도 됨
    @GetMapping("/test")
    public void test() {
      //  BoardData data = BoardDataRepository.findById(1L).orElse(null);
      //  data.setSubject("(수정)제목");

        /*
        BoardData data = new BoardData();
        data.setSubject("제목");
        data.setContent("내용");
        boardDataRepository.saveAndFlush(data);

         */
    }

    //메서드 단위의 인가에 대한 통제
    @ResponseBody
    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('ADMIN')")
    //@Secured({"ADMIN", "MANAGER"})
    public void test2() {
        System.out.println("test2!!!!");
    }

}
