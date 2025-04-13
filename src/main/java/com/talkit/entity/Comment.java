package com.talkit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Member member;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<LikeComment> likeComment;

    @Column
    private String opinion;

    @Column
    private Date created_at;

    public Comment(String content, Post post, Member member, String opinion, Date created_at) {
        this.content = content;
        this.post = post;
        this.member = member;
        this.opinion = opinion;
        this.created_at = created_at;
    }
}



