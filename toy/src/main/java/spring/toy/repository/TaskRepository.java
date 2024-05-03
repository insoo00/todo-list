package spring.toy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.toy.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByMemberId(Long id);

    List<Task> findByMemberUsername(String username);


}
