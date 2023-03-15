package models.ADT;

import Exceptions.ADTException;

import java.util.Map;

public interface MyILatchTable<T, Y> {

    void put(T key, Y value);

    void update(T key, Y value);


    Y lookup(T key) throws ADTException;

    boolean isDefined(T key);

    Map<T, Y> getContent();
    int getFreeSpace();

}
