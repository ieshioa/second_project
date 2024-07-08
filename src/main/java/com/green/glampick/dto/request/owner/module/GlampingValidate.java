package com.green.glampick.dto.request.owner.module;

import com.green.glampick.dto.request.owner.GlampingPostRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


public class GlampingValidate {

    // 글램핑을 이미 가지고 있는가?
    public static void hasGlamping(Long glampId) {
        if (glampId != null) {
            throw new RuntimeException("이미 회원님의 계정에 등록된 글램핑 정보가 있습니다.");
        }
    }

    // 이미지가 들어있는가?
    public static void imgExist(MultipartFile img) {
        if (img == null || img.isEmpty()) {
            throw new RuntimeException("사진을 업로드해주세요.");
        }
    }

    // 글램핑 위치가 중복되는가?
    public static void existingLocation(Long glampId) {
        if (glampId != null) {
            throw new RuntimeException("이미 같은 위치에 등록된 글램핑장이 존재합니다.");
        }
    }
    // 글램핑 위치가 중복되는가? (위치정보 수정할 때)
    public static void locationUpdate(Long existingGlampId, long glampId) {
        if (existingGlampId != null && existingGlampId != glampId) {
            throw new RuntimeException("이미 같은 위치에 등록된 글램핑장이 존재합니다.");
        }
    }

    // 필요한 데이터가 모두 입력되었는가?
    public static void isNull(GlampingPostRequestDto req){
        isNull(req.getGlampName());
        isNull(req.getGlampLocation());
        isNull(req.getRegion());
        isNull(req.getIntro());
        isNull(req.getBasic());
        isNull(req.getParking());
        isNull(req.getNotice());
    }
    public static void isNull(String text) {
        if (text == null || text.isEmpty()) {
            throw new RuntimeException("필수 입력 데이터를 모두 입력해주세요.");
        }
    }
    public static void isNull(int value) {
        if (value <= 0) {
            throw new RuntimeException("필수 입력 데이터를 모두 입력해주세요.");
        }
    }
    public static void isNull(long value) {
        if (value <= 0) {
            throw new RuntimeException("필수 입력 데이터를 모두 입력해주세요.");
        }
    }

}
