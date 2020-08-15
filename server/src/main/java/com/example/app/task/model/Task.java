package com.example.app.task.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Task {

	@Id private String id;
	@Indexed public String title;
	
	public Task() {}
	
	public Task(String title) {
		setTitle(title);
	}
	
	public String getId() { return id; }
	public String getTitle() { return title; }
	public void setId(String id) { this.id = id; }
	public void setTitle(String title) { this.title = title; }
	
}
