package org.choongang.member.controllers;

import lombok.Data;

@Data
public class MemberSearch {
    private int page = 1;
    //한페이지당 몇개를 보일건지
    private int limit = 20;

}
