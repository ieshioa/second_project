package com.green.glampick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "coupon")
@Table(name = "coupon")
public class CouponEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long couponId;  // 쿠폰 ID
    private String couponName;  //쿠폰명
    private String discount;  //쿠폰 할인율
    private String issueDate;  //쿠폰 발급일
    private String deadLine;  //쿠폰 마감일
}
