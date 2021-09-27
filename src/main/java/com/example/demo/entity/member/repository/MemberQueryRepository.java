package com.example.demo.entity.member.repository;

import com.example.demo.entity.member.model.MemberMyfair;
import com.example.demo.entity.team.model.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.demo.entity.member.model.QMemberMyfair.memberMyfair;
import static com.example.demo.entity.team.model.QTeam.team;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberMyfair findByName(String name) {
        return jpaQueryFactory.selectFrom(memberMyfair)
                .innerJoin(memberMyfair.team, team).fetchJoin()
                .where(memberMyfair.name.eq(name))
                .fetchOne();
    }
}
