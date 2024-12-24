package com.talkit.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.talkit.entity.Post;
import com.talkit.entity.QComment;
import com.talkit.entity.QLikePost;
import com.talkit.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostListOrderByLikeCountDesc(Pageable pageable) {

        QPost qPost = QPost.post;
        QLikePost qLikePost = QLikePost.likePost;
        // 쿼리 DSL을 사용하여 COUNT와 GROUP BY, ORDER BY를 처리합니다.
        return jpaQueryFactory
                .select(qPost)
                .from(qPost)
                .leftJoin(qLikePost)
                .on(qPost.id.eq(qLikePost.post.id)) // post.id와 like_post.post_id를 매칭
                .groupBy(qPost.id) // post별로 그룹화
                .orderBy(qLikePost.id.count().desc()) // like_post.id의 개수를 내림차순으로 정렬
                .limit(pageable.getPageSize()) // 페이징 처리
                .offset(pageable.getOffset()) // 페이징 처리
                .fetch(); // 결과 반환
    }

    @Override
    public List<Post> getPostListOrderByViewDesc(Pageable pageable) {
        QPost qPost = QPost.post;
        return jpaQueryFactory
                .select(qPost)
                .from(qPost)
                .orderBy(qPost.view.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public List<Post> getPostListOrderByCommentDesc(Pageable pageable) {
        QPost qPost = QPost.post;
        QComment qComment = QComment.comment;
        // 쿼리 DSL을 사용하여 COUNT와 GROUP BY, ORDER BY를 처리합니다.
        return jpaQueryFactory
                .select(qPost)
                .from(qPost)
                .leftJoin(qComment)
                .on(qPost.id.eq(qComment.post.id)) // post.id와 like_post.post_id를 매칭
                .groupBy(qPost.id) // post별로 그룹화
                .orderBy(qComment.id.count().desc()) // like_post.id의 개수를 내림차순으로 정렬
                .limit(pageable.getPageSize()) // 페이징 처리
                .offset(pageable.getOffset()) // 페이징 처리
                .fetch(); // 결과 반환
    }

    @Override
    public List<Post> getPostListOrderByIdDesc(Pageable pageable) {
        QPost qPost = QPost.post;
        return jpaQueryFactory
                .select(qPost)
                .from(qPost)
                .orderBy(qPost.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public Post getRamdomPost() {
        QPost qPost = QPost.post;

        List<Post> recentPosts = jpaQueryFactory
                .selectFrom(qPost)
                .orderBy(qPost.createdAt.desc()) // 최신순으로 정렬
                .limit(10) // 최근 10개만 가져오기
                .fetch();

        if (recentPosts.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * recentPosts.size()); // 랜덤 인덱스
        return recentPosts.get(randomIndex);
    }

    @Override
    public List<Post> getPostByKeyword(String keyword) {
        QPost qPost = QPost.post;

        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        return jpaQueryFactory
                .select(qPost)
                .from(qPost)
                .where(qPost.title.containsIgnoreCase(keyword))
                .orderBy(qPost.id.desc())
                .fetch();
    }
}
