package com.green.glampick.dto.request.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFavoriteGlampingRequestDto {

    private long glampId;

}