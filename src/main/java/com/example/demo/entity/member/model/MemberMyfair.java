package com.example.demo.entity.member.model;

import com.example.demo.entity.team.model.Team;

import javax.persistence.*;

@Entity
public class MemberMyfair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public MemberMyfair() {
    }

    public MemberMyfair(String name) {
        this.name = name;
    }

    public void addTeam(Team team) {
        this.team = team;
//        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }
}
