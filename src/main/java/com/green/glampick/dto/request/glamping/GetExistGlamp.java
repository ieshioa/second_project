package com.green.glampick.dto.request.glamping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class GetExistGlamp {
    private String region;
    private String searchWord;
}
