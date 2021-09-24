package com.example.demo.entity.member.repository;

import com.example.demo.entity.member.model.MemberMyfair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberMyfairRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("저장 테스트")
    @Test
    void save() {
        String name = "eddy";
        MemberMyfair eddy = new MemberMyfair(name);

        MemberMyfair savedMember = memberRepository.save(eddy);

        Assertions.assertThat(savedMember.getId()).isNotNull();
        Assertions.assertThat(savedMember.getName()).isEqualTo(name);
    }
}