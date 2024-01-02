package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
     //null값을 처리할 때 Optional이 좋다?
     Optional<Member> findByEmail(String email);
     Optional<Member> findByUserId(String userId);

}
