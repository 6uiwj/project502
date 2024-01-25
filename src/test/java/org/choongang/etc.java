package org.choongang;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class etc {
    public static void main(String[] args) {
        /* 배열의 중앙값 구하기

        int[] array = {1, 2, 2, 2, 9};
        array = Arrays.stream(array).sorted().toArray();
        System.out.println("배열의 중앙값" + array[(int)(array.length/2)]);
         */


/*
    public int[] solution(int n, int[] numlist) {
        int[] answer;
        answer = Arrays.stream(array).filter(value -> value%num ==0).toArray();
        return answer;

    }

 */

    int[] array = {1, 2, 3, 3, 3, 4};
    int answer =0;
    for(int i = 0; i< (Arrays.stream(array).max().getAsInt()); i++) {
        for(int j =0; j<array.length; j++) {
            if(array[j]==i) {

            }
        }
    }


    }

    }

