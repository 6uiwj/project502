package org.choongang.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestJoin {
    //없을 때는 기본값을 넣어주자
    private String gid = UUID.randomUUID().toString();

    @NotBlank @Email //이메일 형식인지 검증
    private String email;

    @NotBlank
    @Size(min=6)
    private String userId;
    @NotBlank
    @Size(min=8)
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String name;
    @AssertTrue //참인지 확인
    private boolean agree;
}
