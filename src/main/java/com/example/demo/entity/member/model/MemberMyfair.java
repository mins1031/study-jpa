package com.example.demo.entity.member.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MemberMyfair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public MemberMyfair() {
    }

    public MemberMyfair(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberMyfair(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
