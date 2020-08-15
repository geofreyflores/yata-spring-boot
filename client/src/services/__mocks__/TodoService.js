class TodoService {

	stub = [];
	counter = 0;

	addTodo(todoText) {
		let todo = { id: counter++, title: todoText };
		this.stub.push(todo);
		return Promise.resolve(todo);
	}

	removeTodo(id) {
		let i = this.stub.findIndex(s => s.id === id);
		this.stub.splice(i, 1);
	}

	editTodo(id, title) {
		let todo = this.stub.find(s => s.id === id);
		todo.title = title;
		Promise.resolve(todo);
	}
	
	fetchAll() {
		return Promise.resolve(this.stub);
	}
	
}

export default new TodoService();