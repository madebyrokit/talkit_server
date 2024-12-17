package backend.mbti.repository;


import backend.mbti.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM post ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Post findRandomPost();
}