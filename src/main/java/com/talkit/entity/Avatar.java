package com.talkit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storeFileName;

    @OneToOne
    private Member member;

    public Avatar(String storeFileName, Member member) {
        this.storeFileName = storeFileName;
        this.member = member;
    }
}


