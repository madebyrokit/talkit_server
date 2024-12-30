package com.talkit.repository;

import com.talkit.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT * FROM chat ORDER BY id DESC LIMIT 30", nativeQuery = true)
    List<Chat> findTop20Chat();
}
