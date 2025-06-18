package com.talkit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -389044581L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QAvatar avatar;

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LikeComment, QLikeComment> likeCommentList = this.<LikeComment, QLikeComment>createList("likeCommentList", LikeComment.class, QLikeComment.class, PathInits.DIRECT2);

    public final ListPath<LikePost, QLikePost> likePostList = this.<LikePost, QLikePost>createList("likePostList", LikePost.class, QLikePost.class, PathInits.DIRECT2);

    public final StringPath mbtitype = createString("mbtitype");

    public final StringPath oAuth = createString("oAuth");

    public final StringPath password = createString("password");

    public final ListPath<Post, QPost> postList = this.<Post, QPost>createList("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.avatar = inits.isInitialized("avatar") ? new QAvatar(forProperty("avatar"), inits.get("avatar")) : null;
    }

}

