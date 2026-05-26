package com.umc10th.umc10th.domain.user.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Address {

    private String address;
    private String specAddress;
    private String zipcode;
}
