package com.example.demo.entity.team.repository;

import com.example.demo.entity.team.model.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    void saveTest() {
        String teamName = "Tottenham";
        Team tottenham = new Team(teamName);

        Team savedTottenham = teamRepository.save(tottenham);

        Assertions.assertThat(savedTottenham.getTeamName()).isEqualTo("Tottenham");
        Assertions.assertThat(savedTottenham.getId()).isNotNull();
    }
}