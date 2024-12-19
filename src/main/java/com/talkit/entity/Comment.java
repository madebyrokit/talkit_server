package com.talkit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String selectOption;

    @Column
    private Date createdAt;

    public Comment(String content, Post post, Member member, String selectOption, Date createdAt) {
        this.content = content;
        this.post = post;
        this.member = member;
        this.selectOption = selectOption;
        this.createdAt = createdAt;
    }
}
