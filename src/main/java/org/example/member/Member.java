package org.example.member;

import org.example.team.Team;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private long id;

    private String name;

    @Embedded
    private WorkPeriod workPeriod;

    public Member() {

    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String name, WorkPeriod workPeriod, Team team) {
        this.name = name;
        this.workPeriod = workPeriod;
        this.team = team;
    }

    public static Member of(String name, WorkPeriod workPeriod,Team team){
        return new Member(name,workPeriod,team);
    }

    public void setTeam(Team team) {
        this.team = team;
        team.addMember(this);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public WorkPeriod getWorkPeriod() {
        return workPeriod;
    }

    public Team getTeam() {
        return team;
    }
}
