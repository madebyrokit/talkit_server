package com.talkit.repository.Comments;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkit.entity.Comment;
import com.talkit.entity.QComment;
import com.talkit.entity.QLikeComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getCommentList(Pageable pageable, Long postId) {
        QComment qComment = QComment.comment;
        return jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .where(qComment.post.id.eq(postId))
                .orderBy(qComment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Comment findTopCommentA(Long postId) {
        QComment qComment = QComment.comment;
        QLikeComment qLikeComment = QLikeComment.likeComment;

        return jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .leftJoin(qLikeComment)
                .on(qComment.id.eq(qLikeComment.comment.id)) // post.id와 like_post.post_id를 매칭
                .where(qComment.opinion.eq("A").and(qComment.post.id.eq(postId)))
                .groupBy(qComment.id)
                .having(qLikeComment.id.count().gt(0))
                .orderBy(qLikeComment.id.count().desc()) // like_post.id의 개수를 내림차순으로 정렬
                .limit(1)
                .fetchOne(); // 결과 반환
    }

    @Override
    public Comment findTopCommentB(Long postId) {
        QComment qComment = QComment.comment;
        QLikeComment qLikeComment = QLikeComment.likeComment;

        return jpaQueryFactory
                .select(qComment)
                .from(qComment)
                .leftJoin(qLikeComment)
                .on(qComment.id.eq(qLikeComment.comment.id)) // post.id와 like_post.post_id를 매칭
                .where(qComment.opinion.eq("B").and(qComment.post.id.eq(postId)))
                .groupBy(qComment.id)
                .having(qLikeComment.id.count().gt(0))
                .orderBy(qLikeComment.id.count().desc()) // like_post.id의 개수를 내림차순으로 정렬
                .limit(1)
                .fetchOne(); // 결과 반환
    }
}
