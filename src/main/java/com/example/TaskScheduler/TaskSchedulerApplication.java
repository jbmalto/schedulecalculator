package com.example.TaskScheduler;

import com.example.TaskScheduler.service.ProjectScheduleCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskSchedulerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class, args);
	}

	@Autowired
	private ProjectScheduleCalculator projectScheduleCalculator;

	@Override
	public void run(String... args) throws Exception {

		projectScheduleCalculator.generateSchedule();
	}
}
