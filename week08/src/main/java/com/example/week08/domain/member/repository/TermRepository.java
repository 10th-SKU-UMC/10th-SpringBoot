package com.example.week08.domain.member.repository;

import com.example.week08.domain.member.entity.Term;
import com.example.week08.domain.member.enums.TermName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {

    // 약관 이름으로 Term 조회
    Optional<Term> findByName(TermName name);
}
