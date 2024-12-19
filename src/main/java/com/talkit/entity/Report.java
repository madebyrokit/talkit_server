package com.talkit.entity;

import jakarta.persistence.*;


@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
