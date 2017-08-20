package com.priorityqueue.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.priorityqueue.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SamplePriorityQueueApp {

	public static void main(String[] args) {
		SpringApplication.run(SamplePriorityQueueApp.class, args);
	}
}
