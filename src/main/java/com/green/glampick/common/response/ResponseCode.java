package com.green.glampick.common.response;

public interface ResponseCode {

    // HTTP Status 200
    String SUCCESS = "SU";  // "Success"
    String RESULT_IS_NULL = "RN";  // "결과가 없습니다."

    // HTTP Status 400
    String VALIDATION_FAILED = "VF";  // "모든 정보를 입력해주세요."
    String EMPTY_EMAIL = "EE";  // "이메일이 비어 있습니다."
    String INVALID_EMAIL = "IE";  // "이메일 형식이 올바르지 않습니다."
    String INVALID_PASSWORD = "IP";  // "비밀번호 형식이 올바르지 않습니다."
    String INVALID_NICKNAME = "IN";  // "닉네임 형식이 올바르지 않습니다."
    String INVALID_PHONE = "IPH";  // "전화번호 형식이 올바르지 않습니다."
    String EXPIRED_CODE = "EF";  // "인증코드의 유효시간이 지났습니다."
    String INVALID_CODE = "IC";  // "인증코드가 올바르지 않습니다."
    String NOT_MATCH_PASSWORD = "NMP";  // "비밀번호가 일치하지 않습니다."
    String DUPLICATE_EMAIL = "DE";  // "중복된 이메일 입니다."
    String DUPLICATE_TEL_NUMBER = "DT";  // "중복된 전화번호 입니다."
    String DUPLICATE_NICK_NAME = "DN";  // "중복된 닉네임 입니다."
    String DUPLICATE_BOOK = "DB";  // "중복된 예약입니다."
    String NOT_EXISTED_USER = "NU";  // "존재하지 않는 유저 입니다."
    String NOT_EXISTED_BOOK = "NB";  // "존재하지 않는 예약내역 입니다."
    String NOT_EXISTED_REVIEW = "NR";  // "존재하지 않는 후기 입니다."
    String NOT_EXISTED_GLAMP = "NG";  // "존재하지 않는 글랭핑 입니다."
    String NOT_EXISTED_PAYMENT = "NP";  // "존재하지 않는 결제정보 입니다."

    String WRONG_PERSONNEL = "WP";  // "인원 정보가 잘못 입력되었습니다."
    String WRONG_DATE = "WD";  // "날짜 정보가 잘못 입력되었습니다."

    String RESERVATION_ID_ERROR = "RIE";  // "이용 완료한 예약정보를 불러오지 못했습니다."
    String VALIDATION_STAR_POINT = "VSP";  // "별점 입력이 잘못되었습니다."
    String CANT_FIND_USER = "CU";  // "로그인된 유저 정보를 불러오지 못했습니다."
    String FILE_UPLOAD_ERROR = "FE";  // "파일 업로드 과정에서 오류가 생겼습니다."

    // HTTP Status 401
    String SIGN_IN_FAILED = "SF";  // "로그인에 실패하였습니다."
    String AUTHORIZATION_FAILED = "AF";  // "인증에 실패하였습니다."

    // HTTP Status 403
    String NOT_PERMISSION = "NP";  // "권한이 없습니다."

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";  // "서버 오류 입니다."

}