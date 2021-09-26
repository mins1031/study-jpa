package com.example.demo.entity.team.repository;

import com.example.demo.entity.team.model.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Override
    @EntityGraph(attributePaths = {"members"})
    Optional<Team> findById(Long id);
}
