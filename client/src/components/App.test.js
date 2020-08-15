import React from 'react';
import Adapter from 'enzyme-adapter-react-16';
import App from './App';
import { configure, shallow } from 'enzyme';

configure({adapter: new Adapter()});

jest.mock('../services/TodoService');
jest.mock('./Todo');

describe('render app', () => {
  const comp = shallow(<App />)

  it('should have add button', () => {
    expect(comp.find('button.add')).toHaveLength(1);
  })

  it('should have add input', () => {
    expect(comp.find('input.add')).toHaveLength(1);
  })

  it('should have search button', () => {
    expect(comp.find('button.search')).toHaveLength(1);
  })

  it('should have search input', () => {
    expect(comp.find('input.search')).toHaveLength(1);
  })
});

describe('functionalities', () => {
  const comp = shallow(<App />);

  const setState = jest.fn();
  const useStateSpy = jest.spyOn(React, 'useState')
  useStateSpy.mockImplementation(init => [init, setState]);

  it('should add todo', () => {
    const input = comp.find('input.add');
    expect(input).toHaveLength(1);
    const newTitle = "todoTask";

    input.simulate('focus');
    input.simulate('change', { target: { value: newTitle }} );
    // input.props().onKeyDown({ key: "Enter" });
    // expect(setState).toHaveBeenCalledWith(1);
  })
})