package com.toy.refrigerator.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    //칸 최대 갯수 제한
    SECTOR_IS_FULL(409,"10칸 넘게 생성할 수 없습니다"),
    //찾을 수 없는 칸
    SECTOR_NOT_FOUND(404,"칸 정보를 찾을 수 없습니다."),
    //찾을 수 없는 음식
    FOOD_NOT_FOUND(404,"음식 정보를 찾을 수 없습니다."),
    //권한 없음
    NO_AUTHORIZATION(401,"You don't have authority"),
    //찾을 수 없는 파일
    ;


    private int status;
    private String error;

    ExceptionCode(int status, String error) {
        this.status = status;
        this.error = error;
    }
}
