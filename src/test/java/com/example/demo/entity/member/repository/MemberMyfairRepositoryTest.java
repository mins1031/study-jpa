package com.example.demo.entity.member.repository;

import com.example.demo.entity.address.Address;
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

    @DisplayName("Embedded 테스트")
    @Test
    void embedded() {
        String name = "myFair";
        String state = "강남구";
        String zipCode = "01234";
        Address address = new Address(state, zipCode);

        MemberMyfair member = new MemberMyfair(name, address);
        member = memberRepository.save(member);

        MemberMyfair savedMember = memberRepository.findById(member.getId()).get();

        Assertions.assertThat(savedMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(savedMember.getAddress().getState()).isEqualTo(state);
        Assertions.assertThat(savedMember.getAddress().getZipCode()).isEqualTo(zipCode);

        MemberMyfair savedMember2 = memberRepository.findByState(state).get(0);

        Assertions.assertThat(savedMember2.getId()).isEqualTo(member.getId());
        Assertions.assertThat(savedMember2.getAddress().getState()).isEqualTo(state);
        Assertions.assertThat(savedMember2.getAddress().getZipCode()).isEqualTo(zipCode);
    }
}