package org.example.team;

import org.example.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private long id;

    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    private Team(String teamName) {
        this.teamName = teamName;
    }

    public static Team of (String teamName){
        return new Team(teamName);
    }

    public void addMember(Member member){
        members.add(member);
        member.setTeam(this);
    }

    public long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Member> getMembers() {
        return members;
    }
}
