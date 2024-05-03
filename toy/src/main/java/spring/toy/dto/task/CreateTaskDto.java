package spring.toy.dto.task;

import lombok.Data;
import spring.toy.entity.constant.Priority;

import java.time.LocalDate;

@Data
public class CreateTaskDto {

    private String title;
    private Priority priority;
    private String description;
    private LocalDate date;

}
