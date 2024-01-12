package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.commons.exceptions.CommonException;
import org.choongang.file.service.FileDeleteService;
import org.choongang.file.service.FileDownloadService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements ExceptionProcessor {

    private final FileDeleteService deleteService;
    private final FileDownloadService downloadService;


    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        deleteService.delete(seq);
        //함수가 정의되어있으면 실행되도록?
        String script = String.format("if (typeof parent.callbackFileDelete == 'function') parent.callbackFileDelete(%d);", seq);
        model.addAttribute("script", script);

        return "common/_execute_script";
    }

    //브라우저에 출력하는 것을
    //바디에 출력되던 데이터를 화면이 아니라 파일로 바꿈
    @ResponseBody
    @RequestMapping("/download/{seq}")
    public void download(@PathVariable("sdq") Long seq) {

        //바디에 출력데이터가 화면이 아닌 filename=test.txt 여기로 바뀜??
        //출력방향 바꾸기
        //response.setHeader("Content-Disposition", "attachment; filename=test.txt");
        //출력방향이 화면이 아니라 파일로
        //PrintWriter out = response.getWriter();
        // out.println("test1");
        //out.println("test2");
        try {
            downloadService.download(seq);
        } catch (CommonException e) {
            //자바스크립트 형태로 출력됨
            throw new AlertBackException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
