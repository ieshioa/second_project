package com.green.glampick.common.response;

public interface ResponseMessage {

    // HTTP Status 200
    String SUCCESS = "Success";
    String RESULT_IS_NULL = "결과가 없습니다.";

    // HTTP Status 400
    String VALIDATION_FAILED = "모든 정보를 입력해주세요.";
    String NOT_EMPTY_EMAIL = "이메일이 비어 있습니다.";
    String EXPIRED_CODE = "인증코드의 유효시간이 지났습니다.";
    String INVALID_CODE = "인증코드가 올바르지 않습니다.";
    String DUPLICATE_EMAIL = "중복된 이메일 입니다.";
    String DUPLICATE_TEL_NUMBER = "중복된 전화번호 입니다.";
    String DUPLICATE_NICK_NAME = "중복된 닉네임 입니다.";
    String NOT_EXISTED_USER = "존재하지 않는 유저 입니다.";
    String NOT_EXISTED_BOOK = "존재하지 않는 예약내역 입니다.";
    String NOT_EXISTED_REVIEW = "존재하지 않는 후기 입니다.";
    String NOT_EXISTED_GLAMP = "존재하지 않는 글랭핑 입니다.";

    // HTTP Status 401
    String SIGN_IN_FAILED = "로그인에 실패하였습니다.";
    String AUTHORIZATION_FAILED = "인증에 실패하였습니다.";

    // HTTP Status 403
    String NOT_PERMISSION = "권한이 없습니다.";

    // HTTP Status 500
    String DATABASE_ERROR = "서버 오류 입니다.";

}