package com.example.app.task;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.app.task.db.TaskRepository;
import com.example.app.task.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@AutoConfigureDataMongo
@ActiveProfiles("test")
class TaskControllerTests {

//	@MockBean BooksRepository repository;
	@Autowired TaskRepository repository;
	@Autowired private MockMvc mockMvc;
	
	
	@BeforeEach
	public void setupEach() {
		repository.save(new Task("init task"));
	}
	
	@AfterEach
	public void cleanUpEach() {
		repository.deleteAll();
	}
	
	
	@Test @DisplayName("verify create task API")
	public void insertOneBook() throws Exception {
		int numTasks = repository.findAll().size();
		
		String taskTitle = "a new task";
		Task task = new Task(taskTitle);
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders
		  .post("/tasks")
	      .content(asJsonString(task) )
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(req)
//			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value(taskTitle) )
			.andReturn();
		
		// verify we added one book
		this.mockMvc.perform(fetchAllBooks() )
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(numTasks + 1)) );
	}
	
	@Test @DisplayName("verify find task by ID API")
	public void findTaskById() throws Exception {
		List<Task> tasks = repository.findAll();
		assert(tasks.size() > 0);
		Task task = tasks.get(0);
		String title = task.getTitle(), id = task.getId();
		
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders
			.get("/tasks/" + id)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(req)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isMap())
			.andExpect(jsonPath("$.title").value(title))
			.andReturn();
	}
	
	@Test @DisplayName("verify find task by ID API with invalid id")
	public void findTaskByIdInvalid() throws Exception {
		List<Task> tasks = repository.findAll();
		assert(tasks.size() > 0);
		
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders
			.get("/tasks/invalidblahblah")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);
		
		this.mockMvc.perform(req)
			.andExpect(status().isNoContent())
			.andReturn();
	}
	
	@Test @DisplayName("verify update task API")
	public void updateExistingTask() throws Exception {
		final String updateTitle = "another title";
		List<Task> tasks = repository.findAll();
		assert(tasks.size() > 0);
		Task task = tasks.get(0);
		// make sure title is different so we can verify title actually changed
		assertNotEquals(task.getTitle(), updateTitle);
		
		Task b2 = new Task();
		b2.setTitle(updateTitle);
		String bookId = task.getId();
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders
		  .put("/tasks/" + task.getId())
	      .content(asJsonString(b2) )
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON);
		
		// verify title changed
		this.mockMvc.perform(req)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(bookId))
			.andExpect(jsonPath("$.title").value("another title")); 
		
		// make sure nothing got added
		this.mockMvc.perform(fetchAllBooks())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(tasks.size())) );
	}
	
	@Test @DisplayName("verify delete task API")
	public void deleteExistingBook() throws Exception {
		// given: there's a book that already exists
		List<Task> tasks = repository.findAll();
		assert(tasks.size() > 0);
		
		Task task = tasks.get(0);
		MockHttpServletRequestBuilder req = MockMvcRequestBuilders
			.delete("/tasks/" + task.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(req)
			.andExpect(status().isOk());

		// make sure books are gone
		this.mockMvc.perform(fetchAllBooks())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$", hasSize(tasks.size() - 1)) );
	}
	
	
	/** Helper methods */
	
	public MockHttpServletRequestBuilder fetchAllBooks() {
		return MockMvcRequestBuilders
		  .get("/tasks")
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON);
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
}

