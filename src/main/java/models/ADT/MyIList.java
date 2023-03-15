package models.ADT;

public interface MyIList<T>{
    T get(int index);
    void add(T value);
    int size();
    boolean isEmpty();
}
