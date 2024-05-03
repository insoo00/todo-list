package spring.toy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.toy.dto.oauth2.CustomOAuth2User;
import spring.toy.dto.task.CreateTaskDto;
import spring.toy.entity.Member;
import spring.toy.entity.Task;
import spring.toy.repository.MemberRepository;
import spring.toy.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    public List<Task> getTasks(CustomOAuth2User member) {

        List<Task> tasks = taskRepository.findByMemberUsername(member.getUsername());

        return tasks;
    }

    public Task getTask(CustomOAuth2User member, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(NullPointerException::new);

        return task;
    }

    @Transactional
    public Task createTask(CustomOAuth2User member, CreateTaskDto createTaskDto) {
        Member findMember = memberRepository.findByUsername(member.getUsername());

        Task task = new Task();
        task.setTitle(createTaskDto.getTitle());
        task.setPriority(createTaskDto.getPriority());
        task.setDate(createTaskDto.getDate());
        task.setDescription(createTaskDto.getDescription());
        task.setMember(findMember);

        Task savedTask = taskRepository.save(task);

        return savedTask;
    }


}
