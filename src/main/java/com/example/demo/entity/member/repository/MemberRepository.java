package com.example.demo.entity.member.repository;

import com.example.demo.entity.member.model.MemberMyfair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberMyfair, Long> {
}
