package models.ADT;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{

    Stack<T> stack = new Stack<>();

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int getLen() {
        return stack.size();
    }

    @Override
    public T get(int index) {
        return stack.get(index);
    }

    @Override
    public String toString() {
        if (stack.isEmpty())
            return "The stack is empty";
        return stack.toString();
    }
}
