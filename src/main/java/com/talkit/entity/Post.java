package com.talkit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String optionA;

    @Column
    private String optionB;

    @ManyToOne
    private Member member;

    @Column
    private Date createdAt;

    @Column
    private Long view;

    public void incrementView() {
        this.view++;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<LikePost> likePost;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;
}
