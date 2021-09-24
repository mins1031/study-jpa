package com.example.demo.entity.member.model;

import com.example.demo.entity.team.model.Team;

import javax.persistence.*;

@Entity
public class MemberMyfair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Team team;

    public MemberMyfair() {
    }

    public MemberMyfair(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberMyfair(String name) {
        this.name = name;
    }

    public MemberMyfair(String name, Team team) {
        this.name = name;
        this.team = team;
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
