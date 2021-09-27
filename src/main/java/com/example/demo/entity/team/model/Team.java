package com.example.demo.entity.team.model;

import com.example.demo.entity.member.model.MemberMyfair;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;



    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MemberMyfair> members = new ArrayList<>();

    public Team() {
    }

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<MemberMyfair> getMembers() {
        return members;
    }
}
