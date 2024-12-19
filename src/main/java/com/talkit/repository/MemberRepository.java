package com.talkit.repository;

import com.talkit.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findAllByEmail(String username);
    Optional<Member> findByUsername(String username);
}
