package org.choongang.calendar;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
    void test2(){
        Calendar cal = new Calendar();
            Map<String, Object> data = cal.getData(2024,1);

        System.out.println(data);
        }

        @Test
    void test3() {
            Calendar cal = new Calendar();
            Map<String, Object> map = new HashMap<>();
            System.out.println(cal.getData(3,5).values());
        }

}
