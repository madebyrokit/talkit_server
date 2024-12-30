package com.talkit.entity;

import jakarta.persistence.*;

import java.util.Date;


@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Date createdAt;
}
