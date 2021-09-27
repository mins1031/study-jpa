package com.example.demo.entity.member.repository;

import com.example.demo.entity.address.Address;
import com.example.demo.entity.member.model.MemberMyfair;
import com.example.demo.entity.team.model.Team;
import com.example.demo.entity.team.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberMyfairRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

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

        MemberMyfair savedMember2 = memberRepository.findByAddressState(state).get(0);

        Assertions.assertThat(savedMember2.getId()).isEqualTo(member.getId());
        Assertions.assertThat(savedMember2.getAddress().getState()).isEqualTo(state);
        Assertions.assertThat(savedMember2.getAddress().getZipCode()).isEqualTo(zipCode);
    }

    @Autowired
    TeamRepository teamRepository;

    @DisplayName("QueryDsl 테스트")
    @Test
    void queryDSL() {
        String name = "myFair";
        String state = "강남구";
        String zipCode = "01234";
        Address address = new Address(state, zipCode);

        Team team = new Team("행복빌딩");
        team = teamRepository.save(team);

        MemberMyfair member = new MemberMyfair(name, address);
        member.addTeam(team);
        member = memberRepository.save(member);

        MemberMyfair savedMember = memberQueryRepository.findByName(name);

        Assertions.assertThat(savedMember.getTeam().getTeamName()).isEqualTo(team.getTeamName());

    }
}