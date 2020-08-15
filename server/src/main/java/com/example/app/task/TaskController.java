package com.example.app.task;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.app.task.db.TaskRepository;
import com.example.app.task.model.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired private TaskRepository repository;
	Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Task save(@RequestBody Task task) {
		logger.info("Creating task: " + task.title);
		task.setId(null); // make sure ids are auto-generated
		return repository.save(task);
	}
	
	@GetMapping("") // need an empty value or else test can't map
	public List<Task> find(@RequestParam(name="title", required=false) String title) {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Task findById(@PathVariable String id, HttpServletResponse resp) {
		return repository.findById(id)
			.orElseGet(() -> {
				resp.setStatus(HttpStatus.NO_CONTENT.value() );
				return null;
			});
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Task edit(@RequestBody Task task, @PathVariable String id) {
		task.setId(id);
		repository.save(task);
		return task;
	}
	
	@DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        repository.deleteById(id);
        return id;
    }
    
}
