class TodoService {

	url = "http://localhost:8080/api/v1/tasks";
	header = {
		'Accept': 'application/json, text/plain, */*',
		'Content-Type': 'application/json'
	}

	addTodo(todoText) {
		return fetch(this.url, {
			method: "post",
			headers: this.header,
			body: JSON.stringify({ title: todoText })
		}).then(resp => {
			return resp.json()
		});
	}

	removeTodo(id) {
		return fetch(`${this.url}/${id}`, {
			method: "delete",
			headers: this.header,
		}).then(resp => resp.json());
	}

	editTodo(id, title) {
		return fetch(`${this.url}/${id}`, {
			method: "put",
			headers: this.header,
			body: JSON.stringify({ title: title })
		}).then(resp => resp.json());
	}
	
	fetchAll() {
		return fetch(this.url)
			.then(resp => resp.json());
	}
	
}

export default new TodoService();