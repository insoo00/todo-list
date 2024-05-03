package spring.toy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.toy.dto.oauth2.CustomOAuth2User;
import spring.toy.dto.task.CreateTaskDto;
import spring.toy.entity.Member;
import spring.toy.entity.Task;
import spring.toy.repository.MemberRepository;
import spring.toy.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final MemberRepository memberRepository;

    @GetMapping()
    public String getTasks(Model model,
                           @AuthenticationPrincipal CustomOAuth2User member) {

        List<Task> tasks = taskService.getTasks(member);

        model.addAttribute("member", member);
        model.addAttribute("tasks", tasks);

        return "tasks/tasks";
    }

    @GetMapping("/{taskId}")
    public String getTask(Model model,
                          @AuthenticationPrincipal CustomOAuth2User member,
                          @PathVariable("taskId") Long taskId) {
        Task task = taskService.getTask(member, taskId);

        model.addAttribute("member", member);
        model.addAttribute("task", task);

        return "tasks/task";
    }

    @GetMapping("/create")
    public String createTaskPage(Model model) {
        model.addAttribute("task", new Task());

        return "tasks/createTask";
    }

    @PostMapping("/create")
    public String createTask(Model model,
                             @AuthenticationPrincipal CustomOAuth2User member,
                             @ModelAttribute("task") CreateTaskDto createTaskDto,
                             RedirectAttributes redirectAttributes) {
        Task task = taskService.createTask(member, createTaskDto);

        redirectAttributes.addAttribute("taskId", task.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/tasks/{taskId}";
    }
}
