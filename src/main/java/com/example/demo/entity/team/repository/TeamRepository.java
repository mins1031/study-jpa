package com.example.demo.entity.team.repository;

import com.example.demo.entity.team.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
