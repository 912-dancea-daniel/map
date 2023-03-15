package models.ADT;

public interface MyIStack<T>{
    T pop();
    void push(T v);

    boolean isEmpty();

    int getLen();

    T get(int index);
}
