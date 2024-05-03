package spring.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.toy.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}
