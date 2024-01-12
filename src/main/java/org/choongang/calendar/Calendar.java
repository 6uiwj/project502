package org.choongang.calendar;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
//@Lazy //매번객체를 만들 필요없이 처음 사용할 때만 객체를 만듦.
public class Calendar {



    /**
     * 달력 데이터
     * 7*6 =42칸 , 7*5= 35칸
     * @param year
     * @param month
     */
    public Map<String, Object> getData(Integer _year, Integer _month) {
        int year, month = 0;
        if(_year==null && _month==null) { //연도와 월 값이 없으면 현재 년도, 월로 고정
            LocalDate today = LocalDate.now();
            year = today.getYear();
            month = today.getMonthValue();
        } else {
            year = _year.intValue();
            month = _month.intValue();
        }

        LocalDate sdate = LocalDate.of(year, month, 1);
        LocalDate edate = sdate.plusMonths(1L).minusDays(1);
        int sYoil = sdate.getDayOfWeek().getValue(); //매월 1일 시작 요일

        sYoil = sYoil == 7 ? 0 : sYoil; //일요일부터 시작이니까 일요일을 0으로
        //달력 시작위치(1일 말고 달력 첫번쨰 줄 첫번째 칸)
        int start = sYoil * -1; //-5

        int cellNum = sYoil + edate.getDayOfMonth() > 35 ? 42: 35;
        //총 일수 + 지난달 출력되는 일 수

        Map<String, Object> data = new HashMap<>();

        List<Integer> days = new ArrayList<>(); //날짜 1, 2, 3
        List<String> dates = new ArrayList<>(); //날짜 문자열 2024-01-12
        List<Integer> yoils = new ArrayList<>(); //요일 정보

        for ( int i = start; i < cellNum + start ; i++) {
            LocalDate date = sdate.plusDays(i);

            int yoil = date.getDayOfWeek().getValue();
            yoil = yoil == 7 ? 0 : yoil; //0~6(일~토)

            days.add(date.getDayOfMonth());
            dates.add(date.toString());
            yoils.add(yoil);

            data.put("days", days);
            data.put("dates", dates);
            data.put("yoils", yoils);
            }

            //이전달 년도, 월
            LocalDate prevMonthDate = sdate.minusMonths(1L);
            data.put("prevYear", prevMonthDate.getYear()); //이전달 년도
            data.put("prevMonth", prevMonthDate.getMonthValue()); //이전달 월

            //다음달 년도, 월
            LocalDate nextMonthDate = sdate.plusMonths(1L);
            data.put("nextYear", nextMonthDate.getYear());//다음달 년도
            data.put("nextMonth", nextMonthDate.getMonthValue()); //다음달 월

            //현재 년도, 월
            data.put("year", year);
            data.put("month", month);

            //요일 제목
            data.put("yoilTitles", getYoils());

            return data;
    }

    //매개변수가 없을 때에는 현재 날짜와 연도로 고정
    public Map<String, Object> getData() {

        //return getData(today.getYear(), today.getMonthValue());
        return getData(0,0);
    }

    public List<String> getYoils() {

        return Arrays.asList(
          "일", "월", "화", "수", "목", "금", "토"
        );
    }
}
