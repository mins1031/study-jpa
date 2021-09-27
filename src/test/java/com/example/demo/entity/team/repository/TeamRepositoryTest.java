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

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRepositoryTest {

    @Autowired
    EntityManager em;

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

    @Test
    @DisplayName("연관관계 주인을 통한 데이터 관리 테스트")
    void relationOwnerTest(){
        //Given
        Team team = new Team("team1");
        Team save = teamRepository.save(team);

        MemberMyfair tony = new MemberMyfair("tony");
        MemberMyfair eddy = new MemberMyfair("eddy");
        tony.addTeam(team);
        eddy.addTeam(team);

        //When
        MemberMyfair savedTony = memberRepository.save(tony);
        MemberMyfair savedEddy = memberRepository.save(eddy);

        Team team1 = teamRepository.findById(save.getId()).get();
        MemberMyfair findTony = memberRepository.findById(savedTony.getId()).get();
        MemberMyfair findEddy = memberRepository.findById(savedEddy.getId()).get();

        //Then
        Assertions.assertThat(findTony.getTeam().getId()).isEqualTo(team1.getId());
        Assertions.assertThat(findTony.getTeam().getId()).isEqualTo(team1.getId());
        Assertions.assertThat(findTony.getTeam().getTeamName()).isEqualTo(team1.getTeamName());
        Assertions.assertThat(findEddy.getTeam().getTeamName()).isEqualTo(team1.getTeamName());
        Assertions.assertThat(team1.getMembers()).hasSize(2);
        Assertions.assertThat(team1.getMembers().get(0).getId()).isEqualTo(findTony.getId());
        Assertions.assertThat(team1.getMembers().get(1).getId()).isEqualTo(findEddy.getId());
        Assertions.assertThat(team1.getMembers().get(0).getName()).isEqualTo(findTony.getName());
        Assertions.assertThat(team1.getMembers().get(1).getName()).isEqualTo(findEddy.getName());

    }

    @Test
    @DisplayName("연관관계 주인이 아닌 쪽에서 데이터 관리 테스트")
    void noRelationOwnerTest() {
        //Given
        Team team = new Team("team1");
        Team save = teamRepository.save(team);

        MemberMyfair tony = new MemberMyfair("tony");
        MemberMyfair eddy = new MemberMyfair("eddy");
        //연관관계 주인 아닌 쪽에서 insert작업
        team.getMembers().add(tony);
        team.getMembers().add(eddy);

        //When
        MemberMyfair savedTony = memberRepository.save(tony);
        MemberMyfair savedEddy = memberRepository.save(eddy);

        Team team1 = teamRepository.findById(save.getId()).get();
        MemberMyfair findTony = memberRepository.findById(savedTony.getId()).get();
        MemberMyfair findEddy = memberRepository.findById(savedEddy.getId()).get();

        //Then
        //연관관계 주인이 아닌쪽에서 주인에 대한 데이터를 insert했기에 team,member에 모두 데이터가 null인상황
        Assertions.assertThat(findTony.getTeam()).isNull();
        Assertions.assertThat(findEddy.getTeam()).isNull();
        Assertions.assertThat(team1.getMembers()).isEmpty();

    }

    @Test
    @Transactional
    public void 양방향_주인_확인() {
        Team team = new Team("team");
        teamRepository.save(team);
        //우선 단순히 save됐다고 해서 id가 생길수는 없음 이건 영속성 컨텍스트에 team객체가 들어가며 컨택스트가
        //id를 할당해줬다고 생각할 수 있을듯하다.
        MemberMyfair member1 = new MemberMyfair("name1");
        member1.addTeam(team);
        MemberMyfair member2 = new MemberMyfair("name2");
        member2.addTeam(team);

        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        Team findTeam = teamRepository.findById(team.getId()).get();
        Assertions.assertThat(findTeam.getMembers()).hasSize(2);
    }

    @Test
    @DisplayName("cascade insert 동작 테스트")
    void cascadeWorkTest() {
        //Given
        Team team = new Team("team1");

        MemberMyfair tony = new MemberMyfair("tony");
        MemberMyfair eddy = new MemberMyfair("eddy");
        tony.addTeam(team);
        eddy.addTeam(team);

        team.getMembers().add(tony);
        team.getMembers().add(eddy);

        Team save = teamRepository.save(team);

        //When
        Team findTeam = teamRepository.findById(save.getId()).get();
        MemberMyfair findTony = memberRepository.findById(tony.getId()).get();
        MemberMyfair findEddy = memberRepository.findById(eddy.getId()).get();

        //Then
        Assertions.assertThat(findTeam.getMembers().get(0).getName()).isEqualTo(tony.getName());
        Assertions.assertThat(findTeam.getMembers().get(1).getName()).isEqualTo(eddy.getName());

        Assertions.assertThat(findTony.getTeam().getId()).isEqualTo(findTeam.getId());
        Assertions.assertThat(findEddy.getTeam().getId()).isEqualTo(findTeam.getId());
        Assertions.assertThat(findTony.getTeam().getTeamName()).isEqualTo(tony.getTeam().getTeamName());
        Assertions.assertThat(findEddy.getTeam().getTeamName()).isEqualTo(eddy.getTeam().getTeamName());

    }

    @Test
    @DisplayName("cascade delete 동작 테스트")
    void cascadeDeleteWorkTest() {
        //Given
        Team team = new Team("team1");

        MemberMyfair tony = new MemberMyfair("tony");
        MemberMyfair eddy = new MemberMyfair("eddy");
        tony.addTeam(team);
        eddy.addTeam(team);

        team.getMembers().add(tony);
        team.getMembers().add(eddy);

        Team save = teamRepository.save(team);

        Team findTeam = teamRepository.findById(save.getId()).get();

        teamRepository.delete(findTeam);

        //When
        Optional<Team> findResultTeam = teamRepository.findById(save.getId());
        Optional<MemberMyfair> findTony = memberRepository.findById(tony.getId());
        Optional<MemberMyfair> findEddy = memberRepository.findById(eddy.getId());

        //Then
        Assertions.assertThat(findResultTeam.isPresent()).isFalse();
        Assertions.assertThat(findTony.isPresent()).isFalse();
        Assertions.assertThat(findEddy.isPresent()).isFalse();
    }

    @Test
    @DisplayName("고아객체 테스트")
    @Transactional
    void orphanRemovalTest() {
        //Given
        Team team = new Team("team1");

        MemberMyfair tony = new MemberMyfair("tony");
        MemberMyfair eddy = new MemberMyfair("eddy");
        tony.addTeam(team);
        eddy.addTeam(team);

        team.getMembers().add(tony);
        team.getMembers().add(eddy);

        Team save = teamRepository.save(team);

        em.flush();
        em.clear();
        //When
        Team findTeam = teamRepository.findById(save.getId()).get();
        findTeam.getMembers().remove(1);

        em.flush();
        em.clear();

        Optional<MemberMyfair> findEddy = memberRepository.findById(eddy.getId());
        List<MemberMyfair> allMembers = memberRepository.findAll();
        //Then
        Assertions.assertThat(findTeam.getMembers().size()).isEqualTo(1);
        Assertions.assertThat(findEddy.isPresent()).isFalse();
        Assertions.assertThat(allMembers.size()).isEqualTo(1);

    }
}