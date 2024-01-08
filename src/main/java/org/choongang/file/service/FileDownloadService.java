package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {
    private final FileInfoService infoService;
    private final HttpServletResponse response;

    //파일번호를 가지고 다운로드
    public void download(Long seq) {
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();

        //파일명 -> 2 바이트 인코딩으로 변경 (윈도우즈 시스템에서 한글 깨짐 방지)
        String fileName = null;
        //ISO8859_1 : 2byte형태의 한글인코딩
        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
        }

        File file = new File(filePath);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream out = response.getOutputStream(); //응답 Body에 출력

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName); //출력방향 -> 파일로
            response.setHeader("Content-Type", "application/octet-stream"); //컨텐츠타입을 바이너리코드형태로(범용적) ->이미지일지 뭐일지 몰라서 범용적으로 설정
            response.setIntHeader("Expires",0); //만료시간X(파일 다운로드가 오래걸릴 때 다운 받다가 만료되면 안됨)
            //다운받은 파일은 캐싱이 되어있어서 기존파일을 바꾸지 않는다. 파일이름이 같아도 다운받을 수 있도록 캐시 다시 갱신
            response.setHeader("Cache-Control", "must-revalidate");
            response.setHeader("Pragma", "public");
            //파일 용량에 관해
            response.setHeader("Content-Length", String.valueOf(file.length()));

            //바디 쪽에 파일 데이터 출력 ?
            while(bis.available() > 0) {
                out.write(bis.read());
            }
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();


        }

    }
}
