package com.umc10th.umc10th.domain.term.entity;

import com.umc10th.umc10th.domain.term.enums.TermName;
import com.umc10th.umc10th.domain.user.entity.mapping.UserTerms;
import com.umc10th.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TermName termName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isOptional = false;

    @Builder.Default
    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<UserTerms> userTermsList = new ArrayList<>();
}
