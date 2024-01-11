package org.choongang.admin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    //메뉴는 바뀌지 않을 걸....
    private final static Map<String, List<MenuDetail>> menus;
    static {
        menus = new HashMap<>();
        //주 메뉴 코드 입력(회원) - 서브메뉴 (회원목록, 회원권한 등...) 맵으로 한 이유...- 킷값:주메뉴코드
        menus.put("member", Arrays.asList(
                new MenuDetail("list", "회원목록", "/admin/member"),
                new MenuDetail("authority", "회원권한", "/admin/member/authority")
        ));
        //"board" : 주메뉴 / Arrays...( 보조메뉴)
        menus.put("board", Arrays.asList(
                new MenuDetail("list", "게시판목록", "/admin/board"),
                new MenuDetail("add", "게시판등록", "/admin/board/add"),
                new MenuDetail("posts", "게시글관리", "/admin/board/posts")
        ));

        //예약현황, 지점 목록, 지점등록, 휴무일 관리
        menus.put("reservation", Arrays.asList(
                new MenuDetail("list", "예약 현황", "/admin/reservation"),
                new MenuDetail("branch", "지점 목록", "/admin/reservation/branch"),
                new MenuDetail("add_branch", "지점 등록", "/admin/reservation/add_branch"),
                new MenuDetail("holiday", "휴무일 관리", "/admin/reservation/holiday")
        ));
    }
    //code: 주메뉴코드
    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}
