import React, { useState, useEffect } from 'react';
import TodoService from '../services/TodoService';
import Todo from './Todo'
import './App.css';


export default function App() {

  const [todos, setTodos] = useState([]);
  const [searchStr, setSearchStr] = useState("");

  let addRef = React.createRef();
  let searchRef = React.createRef();

  
  async function addTodo() {
    const todoText = addRef.current.value;
    addRef.current.value = "";
    addRef.current.focus();

    const todo = await TodoService.addTodo(todoText);
    setTodos(todos => todos.concat(todo));
  }

  function removeTodo(todo) {
    TodoService.removeTodo(todo.id);
    setTodos(todos => todos.filter(t => t.id !== todo.id))
  }

  function editTodo(id, title) {
    TodoService.editTodo(id, title);
    setTodos(todos => todos.map(t => {
      return (t.id === id) ? { ...t, title } : t;
    }) );
  }

  function componentDidMount() {
    TodoService.fetchAll()
      .then(todos => {
        setTodos(todos || []);
      });
  }

  useEffect(() => { componentDidMount() }, []);
  return (
    <div className="App">

      <div className="actions">
        <input className="add" type="text" ref={ addRef } 
          onKeyDown={ (ev) => (ev.key === "Enter") && addTodo() } 
          placeholder="Add your todos here" />
        <button className="add" onClick={ addTodo }>Add</button>
      </div>

      <div className="list">
        <input className="search" type="text" placeholder="Search" ref={ searchRef }></input>
        <button className="search" onClick={ () => setSearchStr(searchRef.current.value) }>Search</button>
        <ul>{
          todos.filter(t => t.title.search(searchStr || "") > -1)
            .map(t => <Todo key={t.id} todo={t} removeTodo={ removeTodo } editTodo={ editTodo }></Todo>)
        }</ul>
      </div>
    </div>
  );
}
