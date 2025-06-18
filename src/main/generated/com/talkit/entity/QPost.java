package com.talkit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -836058911L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final ListPath<Comment, QComment> commentList = this.<Comment, QComment>createList("commentList", Comment.class, QComment.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> created_at = createDateTime("created_at", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<LikePost, QLikePost> likePost = this.<LikePost, QLikePost>createList("likePost", LikePost.class, QLikePost.class, PathInits.DIRECT2);

    public final QMember member;

    public final StringPath option_a = createString("option_a");

    public final StringPath option_b = createString("option_b");

    public final StringPath title = createString("title");

    public final NumberPath<Long> total_view = createNumber("total_view", Long.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

