package models.ADT;

import java.util.List;
import java.util.Vector;

public class MyList<T> implements MyIList<T>{
    List<T> list = new Vector<>();


    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public void add(T value) {
        list.add(value);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        if (list.size() > 0)
            return list.toString();
        return "The out list is empty";
    }
}
