package com.green.glampick.common.response;

public interface ResponseCode {

    // HTTP Status 200
    String SUCCESS = "SU";
    String RESULT_IS_NULL = "RN";

    // HTTP Status 400
    String VALIDATION_FAILED = "VF";
    String NOT_EMPTY_EMAIL = "NE";
    String EXPIRED_CODE = "EF";
    String INVALID_CODE = "IC";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_TEL_NUMBER = "DT";
    String DUPLICATE_NICK_NAME = "DN";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOOK = "NB";
    String NOT_EXISTED_REVIEW = "NR";
    String NOT_EXISTED_GLAMP = "NG";

    // HTTP Status 401
    String SIGN_IN_FAILED = "SF";
    String AUTHORIZATION_FAILED = "AF";

    // HTTP Status 403
    String NOT_PERMISSION = "NP";

    // HTTP Status 500
    String DATABASE_ERROR = "DBE";

}