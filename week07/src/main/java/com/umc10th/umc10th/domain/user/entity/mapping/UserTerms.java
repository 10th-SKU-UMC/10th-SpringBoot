package com.umc10th.umc10th.domain.user.entity.mapping;

import com.umc10th.umc10th.domain.term.entity.Term;
import com.umc10th.umc10th.domain.user.entity.User;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserTerms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_terms_id")
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isAgreed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id")
    private Term term;

}
