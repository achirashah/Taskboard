package edu.neu.shah.taskboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskboardApplication {
	private static final Logger LOGGER = LogManager.getLogger(TaskboardApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting Taskboard Application!");
		SpringApplication.run(TaskboardApplication.class, args);
	}

}
