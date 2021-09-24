package org.example;

import org.example.member.Member;
import org.example.member.WorkPeriod;
import org.example.team.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpaStudy");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        String teamName = "Tottenham";
        Team tottenHam = Team.of(teamName);

        String memberName1 = "son";
        String memberName2 = "kane";

        WorkPeriod workPeriod1 = new WorkPeriod(LocalDate.of(2015, 01, 01), LocalDate.of(2021, 01, 01));
        WorkPeriod workPeriod2 = new WorkPeriod(LocalDate.of(2013, 01, 01), LocalDate.of(2021, 01, 02));

        Member son = Member.of(memberName1, workPeriod1, tottenHam);
        Member kane = Member.of(memberName2, workPeriod2, tottenHam);

        tottenHam.getMembers().add(son);
        tottenHam.getMembers().add(kane);

        try {
            tx.begin();

            em.persist(tottenHam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        System.out.println("--cascade persist--");
        Team team = em.find(Team.class, tottenHam.getId());
        System.out.println(team.getTeamName());
        for(Member member : team.getMembers()) {
            System.out.println(member.getName() + " " + member.getWorkPeriod().getStartDate());
        }

        try {
            tx.begin();

            team.getMembers().remove(1);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }

        System.out.println("\n--orphanRemoval--");
        team = em.find(Team.class, tottenHam.getId());
        for(Member member : team.getMembers()) {
            System.out.println(member.getName());
        }

        em.close();
        emf.close();
    }
}
