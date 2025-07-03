package com.talkit.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Avatar avatar;

    @Column(unique = true, nullable = false)
    private String username;

    @Column (nullable = false)
    private String mbtitype;

    @Column
    private String oAuth;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> postList;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LikePost> likePostList;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> commentList;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<LikeComment> likeCommentList;

    public Member(String email, String password, String username, String mbtitype, String oAuth) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mbtitype = mbtitype;
        this.oAuth = oAuth;
    }
}
