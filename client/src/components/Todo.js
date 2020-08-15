import React, { useState } from 'react';

export default function Todo({ todo, removeTodo, editTodo }) {

  const [ editMode, setEditMode ] = useState(false);
  let textFieldRef = React.createRef();
  let { id, title } = todo;

  function toggleEditMode() {
    setEditMode(editMode => !editMode);
    if (editMode) textFieldRef.current.focus();
  }

  function edit() {
    let newTitle = textFieldRef.current.value || title; // default to old title if empty
    editTodo(id, newTitle); 
    toggleEditMode();
  }

  return (
    <li>
      {
        editMode
          ? <>
              <input type="text" ref={ textFieldRef } defaultValue={ title }></input>
              <button className="edit-btn" onClick={ edit }>Edit</button>
              <button className="cancel-btn" onClick={ toggleEditMode }>Cancel</button>
            </>
          : <span className="todo-title" onDoubleClick={ toggleEditMode }>{ title }</span>
      }
      <span role="img" className="icon delete" aria-label="delete" 
        onClick={ () => removeTodo(todo) }> ❌</span>
    </li>
  )
}