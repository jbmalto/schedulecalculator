package com.example.TaskScheduler.service;

import com.example.TaskScheduler.models.Project;
import com.example.TaskScheduler.models.SubTask;
import com.example.TaskScheduler.models.Task;
import com.example.TaskScheduler.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProjectScheduleCheckerCalculatorImpl implements ProjectScheduleCalculator {

    @Autowired
    private Mapper<String> stringMapper;
    @Autowired
    private Mapper<Project> projectMapper;
    @Autowired
    private Mapper<String> objToJson;

    @Override
    public void generateSchedule() {
        String projectDetailsToString = stringMapper.convertFileToString("src/main/java/com/example/taskScheduler/Project.json");

        Project project = projectMapper.stringToObj(projectDetailsToString, Project.class);

        this.getEndDate(project);

        String output = objToJson.objToJson(project);

        System.out.println(output);
    }

    private void getEndDate(Project project) {
        project.getTasks()
                .forEach(task-> {
                    LocalDate localDate =
                            calculateMainTaskEndDate(task).isEmpty()?
                                    // Adds 1 day on the result of calculating end date of all subtasks
                                    LocalDate.now().plusDays(1) :
                                    calculateMainTaskEndDate(task).get().plusDays(1);
                    task.setStartDate(localDate);
                    task.setEndDate(localDate.plusDays(task.getTaskDuration()));
                });
    }

    private Optional<LocalDate> calculateMainTaskEndDate(Task task) {
        return task.getDependencies()
                .stream()
                .map(SubTask::getEndDate)
                .max(LocalDate::compareTo);
    }
}
