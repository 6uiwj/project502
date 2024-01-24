package org.choongang.admin.reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ReservationValidator implements Validator {
    private final ReservationRepository reservationRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestReservation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    RequestReservation form = (RequestReservation) target;
        Long bookCode = form.getBookCode();
        //커맨드객체에서 전화번호 가져오기
        String donorTel = form.getDonorTel();


        if (StringUtils.hasText(donorTel)) {
            donorTel = donorTel.replaceAll("\\D", "");
            if (!donorTel.matches("01[016]\\d{3,4}\\d{4}")) {
                errors.rejectValue("donorTel", "MOBILE");
            }
        }

        // String donorTel = String.join("",donorTels);
        String mode = form.getMode();
        /*
        if(mode.equals("add") && StringUtils.hasText(String.valueOf(bookCode)) && reservationRepository.existsById(bookCode)) {
            errors.rejectValue("bookCode", "");
        }

         */
        /*
        for(int i = 0; i < donorTels.length; i++) {
            if (mode.equals("add") && StringUtils.hasText(donorTels[i]) && reservationRepository.existsById(bookCode)) {
                errors.rejectValue("donorTel", "NotBlank");
            }
        } */
    }
}
