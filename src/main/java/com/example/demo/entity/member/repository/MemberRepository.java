package com.example.demo.entity.member.repository;

import com.example.demo.entity.member.model.MemberMyfair;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberMyfair, Long> {

    @Override
    @EntityGraph(attributePaths = {"team"})
    Optional<MemberMyfair> findById(Long aLong);

    List<MemberMyfair> findByState(String state);
}
