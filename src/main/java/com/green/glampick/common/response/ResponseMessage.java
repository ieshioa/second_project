package com.green.glampick.common.response;

public interface ResponseMessage {

    // HTTP Status 200
    String SUCCESS = "Success";  // "SU"
    String RESULT_IS_NULL = "결과가 없습니다.";  // "RN"

    // HTTP Status 400 BAD_REQUEST
    String VALIDATION_FAILED = "모든 정보를 입력해주세요.";  // "VF"
    String EMPTY_EMAIL = "이메일이 비어 있습니다.";  // "EE"
    String EXPIRED_CODE = "인증코드의 유효시간이 지났습니다.";  // "EF"
    String INVALID_CODE = "인증코드가 올바르지 않습니다.";  // "IC"
    String DUPLICATE_EMAIL = "중복된 이메일 입니다.";  // "DE"
    String DUPLICATE_TEL_NUMBER = "중복된 전화번호 입니다.";  // "DT"
    String DUPLICATE_NICK_NAME = "중복된 닉네임 입니다.";  // "DN"
    String DUPLICATE_BOOK = "중복된 예약입니다.";  // "DB"
    String NOT_EXISTED_USER = "존재하지 않는 유저 입니다.";  // "NU"
    String NOT_EXISTED_BOOK = "존재하지 않는 예약내역 입니다.";  // "NB"
    String NOT_EXISTED_REVIEW = "존재하지 않는 후기 입니다.";  // "NR"
    String NOT_EXISTED_GLAMP = "존재하지 않는 글랭핑 입니다.";  // "NG"
    String NOT_EXISTED_PAYMENT = "존재하지 않는 결제정보 입니다.";  // "NP"

    String WRONG_PERSONNEL = "인원 정보가 잘못 입력되었습니다.";  // "WP"
    String WRONG_DATE = "날짜 정보가 잘못 입력되었습니다.";  // "WD"

    String CANT_FIND_USER = "로그인된 유저 정보를 불러오지 못했습니다.";  // "CU"
    String FILE_UPLOAD_ERROR = "파일 업로드 과정에서 오류가 생겼습니다.";  // "FE"

    // HTTP Status 401 UNAUTHORIZED
    String SIGN_IN_FAILED = "로그인에 실패하였습니다.";  // "SF"
    String AUTHORIZATION_FAILED = "인증에 실패하였습니다.";  // "AF"

    // HTTP Status 403 FORBIDDEN
    String NOT_PERMISSION = "권한이 없습니다.";  // "NP"

    // HTTP Status 500
    String DATABASE_ERROR = "서버 오류 입니다.";  // "DBE"

}