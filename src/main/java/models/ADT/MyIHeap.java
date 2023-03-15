package models.ADT;

import Exceptions.ADTException;

import java.util.List;
import java.util.Map;

public interface MyIHeap<T, Y>{
    void put(T key, Y value);

    void update(T key, Y value);

    Y lookup(T key) throws ADTException;

    boolean isDefined(T key);

    int getSize();

    void deleteKey(T key);

    int getFreeSpace();

    Map<T, Y> getContent();

    List<Y> getValues();

    void setContent(Map<T, Y> map);
}
