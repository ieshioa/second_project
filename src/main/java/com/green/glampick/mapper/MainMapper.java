package com.green.glampick.mapper;

import com.green.glampick.dto.object.main.MountainViewGlampingItem;
import com.green.glampick.dto.object.main.PetFriendlyGlampingItem;
import com.green.glampick.dto.object.main.PopularGlampingItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    List<PopularGlampingItem> popular();
    List<PetFriendlyGlampingItem> petFriendly();
    List<MountainViewGlampingItem> mountainView();
}
