package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {
     //null값을 처리할 때 Optional이 좋다?
     @EntityGraph(attributePaths = "authorities")
     Optional<Member> findByEmail(String email);
     @EntityGraph(attributePaths = "authorities")
     Optional<Member> findByUserId(String userId);

     //조건식이 많이 필요할 때는 querydslpredicateExecutor을 사용 (predicate가 매개변수인거..)
     default boolean existsByEmail(String email) {
          QMember member = QMember.member;
          return exists(member.email.eq(email));
     }

     default boolean existsByUserId(String userId) {
          QMember member = QMember.member;
          return exists(member.userId.eq(userId));
     }
}
