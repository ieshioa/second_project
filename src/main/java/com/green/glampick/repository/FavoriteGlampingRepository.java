package com.green.glampick.repository;

import com.green.glampick.dto.response.user.GetFavoriteGlampingListResponseDto;
import com.green.glampick.entity.GlampFavoriteEntity;
import com.green.glampick.entity.PaymentEntity;
import com.green.glampick.repository.resultset.GetFavoriteGlampingResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGlampingRepository extends JpaRepository<GlampFavoriteEntity, Long> {


    @Query(
//            value =
//SELECT A.glamp_image
//, A.glamp_location
//, A.glamp_name
//, A.star_point_avg
//, B.room_price
//, count(C.review_content) AS '댓글'
//, A.glamp_id
//FROM glamping A
//JOIN room B
//ON A.glamp_id = B.glamp_id
//JOIN review C
//ON A.glamp_id = C.glamp_id
//GROUP BY A.glamp_id
//HAVING MIN(B.room_price)
//AND A.glamp_id = 4
//            nativeQuery = true
    )
            List<GetFavoriteGlampingResultSet>getFavoriteGlamping(Long glampId);
}
