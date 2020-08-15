import React from 'react';
import { render } from '@testing-library/react';
import Adapter from 'enzyme-adapter-react-16';
import Todo from './Todo';
import { configure, shallow } from 'enzyme';

configure({ adapter: new Adapter() });

describe('render app', () => {
  let todo = { id: 1, title: "sample text" }
  let removeTodo = jest.fn();
  let editTodo = jest.fn();

  let comp = shallow(<Todo todo={todo} removeTodo={ removeTodo } 
    editTodo={ editTodo }></Todo>);

  it('should have remove button', () => {
    expect(comp.find('span.icon.delete')).toHaveLength(1);
  });

  it('should have todo item', () => {
    expect(comp.find('.todo-title').text()).toEqual(todo.title);
  });

  it ('should toggle to edit mode when dbl clicked', () => {
    comp.find('.todo-title').simulate('doubleClick');
    expect(comp.find('input')).toHaveLength(1);
  })

  it ('test edit', () => {
    const input = comp.find('input');
    expect(input).toHaveLength(1);
    const newTitle = "new title";

    input.simulate('focus');
    input.simulate('change', { target: { value: newTitle } });
    // comp.find('.edit-btn').simulate('click'); // enzyme having issue with react refs

    // expect(comp.find('.todo-title').text()).toEqual(newTitle);
  })

});