package com.example.demo.entity.team.repository;

import com.example.demo.entity.member.model.MemberMyfair;
import com.example.demo.entity.member.repository.MemberRepository;
import com.example.demo.entity.team.model.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Transient;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @DisplayName("Team 저장 테스트")
    @Test
    void saveTest() {
        String teamName = "Tottenham";
        Team tottenham = new Team(teamName);

        Team savedTottenham = teamRepository.save(tottenham);

        Assertions.assertThat(savedTottenham.getTeamName()).isEqualTo("Tottenham");
        Assertions.assertThat(savedTottenham.getId()).isNotNull();
    }

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void createTest() {
        String name1 = "Kimilm";
        MemberMyfair member1 = new MemberMyfair(name1);

        String name2 = "Tony";
        MemberMyfair member2 = new MemberMyfair(name2);

        String teamName = "dev";
        Team team = new Team(teamName);

        member1.addTeam(team);
        member2.addTeam(team);

        Team savedTeam = teamRepository.save(team);
        MemberMyfair savedMember1 = memberRepository.save(member1);
        memberRepository.save(member2);

        Team findTeam = teamRepository.findById(savedTeam.getId()).get();
        MemberMyfair findMember = memberRepository.findById(savedMember1.getId()).get();

        Assertions.assertThat(findMember.getTeam().getTeamName()).isEqualTo(teamName);
        Assertions.assertThat(findTeam.getMembers().get(0).getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(findTeam.getMembers()).hasSize(2);
    }
}