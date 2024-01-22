package org.choongang.reservation.controllers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestMainReservation {

    private String mode = "add";

    private Long bookCode;

    @NotBlank
    private String name;

    @NotBlank
    private String donnerTel;
    private String bookType;
    private String cCode; // 센터 코드
}
