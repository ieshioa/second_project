package com.green.glampick.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_coupon")
@Table(name = "user_coupon")
public class UserCouponEntity {
    //유저 쿠폰 테이블
    @Id
    private long user_id;//유저 ID
    private long coupon_id;//쿠폰 ID
    private long is_active;//사용 유무

}
