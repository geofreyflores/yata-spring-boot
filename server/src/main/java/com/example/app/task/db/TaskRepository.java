package com.example.app.task.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.app.task.model.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
	

}