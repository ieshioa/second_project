package com.green.glampick.service.implement;

import com.green.glampick.dto.object.main.MountainViewGlampingItem;
import com.green.glampick.dto.object.main.PetFriendlyGlampingItem;
import com.green.glampick.dto.object.main.PopularGlampingItem;
import com.green.glampick.dto.response.glamping.GetSearchGlampingListResponseDto;
import com.green.glampick.dto.response.main.GetMainGlampingListResponseDto;
import com.green.glampick.mapper.MainMapper;
import com.green.glampick.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final MainMapper mapper;

    @Override
    @Transactional
    public ResponseEntity<? super GetMainGlampingListResponseDto> mainGlampingList() {

        List<PopularGlampingItem> popular = mapper.popular();
        List<PetFriendlyGlampingItem> petFriendly = mapper.petFriendly();
        List<MountainViewGlampingItem> mountainView = mapper.mountainView();

        return GetMainGlampingListResponseDto.success(popular, petFriendly, mountainView);

    }

}