package org.choongang.calendar;

import org.choongang.commons.Utils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
@Lazy
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
            if(_year==null && _month==null) { //연도와 월 입력값이 없으면 현재 년도, 월로 고정
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
            //달력 시작위치(1일 말고 달력 첫번쨰 줄 첫번째 칸) <- 1일부터 왼쪽으로 -1, -2 -3 -4...
            int start = sYoil * -1; //-5

            int cellNum = sYoil + edate.getDayOfMonth() > 35 ? 42: 35;
            //출력될달력이 5줄인지 6줄인지 =  총 일수 + 지난달 출력되는 일 수가 35이상인가?

            Map<String, Object> data = new HashMap<>();
            List<Integer> days = new ArrayList<>(); //날짜 1, 2, 3
            List<String> dates = new ArrayList<>(); //날짜 문자열 2024-01-12
            List<Integer> yoils = new ArrayList<>(); //요일 정보
            //start: 달력 1번째줄 1번째칸 : -N
            //달력 블록안의 숫자의 개수만큼
            for ( int i = start; i < cellNum + start ; i++) {
                System.out.println(i);
                LocalDate date = sdate.plusDays(i); //블록안의 모든 날짜 출력

                int yoil = date.getDayOfWeek().getValue(); //모든 날짜의 요일 출력
                yoil = yoil == 7 ? 0 : yoil; //0~6(일~토)

                days.add(date.getDayOfMonth()); //일(1, 2, ...) days 리스트에 담기
                dates.add(date.toString()); //날짜(2024-01-13) dates 리스트에 담기
                yoils.add(yoil); //요일(일=0, 월=1..) yoils 리스트에 담기

                data.put("days", days); //map에 넣기
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

                //요일 제목(getYoilㅁ ㅔ서드 실행 (리스트형태로 월~일을 담고 있음 )
                data.put("yoilTitles", getYoils());

                return data;
    }

    /**
     * 매개변수가 없는 데이터는 현재 일자 기준의 년도, 월로 달력 데이터 생성
     *
     * @return
     */
    public Map<String, Object> getData() {
        return getData(null, null);
    }

    public List<String> getYoils() {

        return Arrays.asList(
                Utils.getMessage("일", "commons"),
                Utils.getMessage("월", "commons"),
                Utils.getMessage("화", "commons"),
                Utils.getMessage("수", "commons"),
                Utils.getMessage("목", "commons"),
                Utils.getMessage("금", "commons"),
                Utils.getMessage("토", "commons")
        );
    }
}
