package org.choongang.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class CalendarTest {
    @Test
    void test1() {
        Calendar cal = new Calendar();
        //int[] days = cal.getDays(2024,2);
        // System.out.println(Arrays.toString(days));
    }

    @Test
    void test2() {
        Calendar cal = new Calendar();
        Map<String, Object> data = cal.getData(2024, 1);

        System.out.println(data);
    }

    @Test
    void test3() {
        Calendar cal = new Calendar();
        Map<String, Object> map = new HashMap<>();
        System.out.println(cal.getData(3, 5).values());
    }

    @Test
    void test4() {
       // LocalDate sdate = LocalDate.of(2024, 3, 1);
        //LocalDate edate = sdate.plusMonths(1L).minusDays(1);
        //int sYoil = sdate.getDayOfWeek().getValue(); //매월 1일 시작 요일

       // sYoil = sYoil == 7 ? 0 : sYoil; //일요일부터 시작이니까 일요일을 0으로
        //달력 시작위치(1일 말고 달력 첫번쨰 줄 첫번째 칸) <- 1일부터 왼쪽으로 -1, -2 -3 -4...

//        int start = sYoil * -1; //-5
//
//        int cellNum = sYoil + edate.getDayOfMonth() > 35 ? 42 : 35;
//
//        for (int i = start; i < cellNum + start; i++) {
//            System.out.println(i);
        LocalDate date = LocalDate.now().plusDays(-3);
        System.out.println(date);
    }

    }

