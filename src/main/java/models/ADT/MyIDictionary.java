package models.ADT;

import Exceptions.ADTException;

import java.util.Collection;
import java.util.Enumeration;

public interface MyIDictionary<T, Y> {
    void put(T key, Y value);

    Y lookup(T key) throws ADTException;

    boolean isDefined(T key);

    int getSize();

    void deleteKey(T key);

    Enumeration<T> getKeys();

    MyDictionary<T, Y> deepCopy();

    Collection<Y> getValues();

}
