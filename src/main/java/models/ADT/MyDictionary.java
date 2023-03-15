package models.ADT;

import Exceptions.ADTException;

import java.util.*;

public class MyDictionary<T, Y>  implements MyIDictionary<T, Y> {

    Dictionary<T, Y> dic;

    public MyDictionary() {
        this.dic = new Hashtable<>();
    }


    @Override
    public void put(T key, Y value) {
        dic.put(key, value);
    }

    @Override
    public Y lookup(T key) throws ADTException {
        if (dic.get(key) == null) throw new ADTException("Not in table");
        else return dic.get(key);
    }

    @Override
    public boolean isDefined(T key) {
        return dic.get(key) != null;
    }

    @Override
    public int getSize() {
        return dic.size();
    }

    @Override
    public void deleteKey(T key) {
        dic.remove(key);
    }

    @Override
    public Enumeration<T> getKeys() {
        return dic.keys();
    }

    @Override
    public MyDictionary<T, Y> deepCopy() {
        MyDictionary<T, Y> newDic = new MyDictionary<>();

        Enumeration<T> e = dic.keys();
        while(e.hasMoreElements()) {
            T k = e.nextElement();
            newDic.put(k, dic.get(k));
        }

        return newDic;
    }

    @Override
    public Collection<Y> getValues() {
        List<Y> tmp = new ArrayList<>();

        Enumeration<T> e = dic.keys();
        while(e.hasMoreElements()) {
            T k = e.nextElement();
            tmp.add(dic.get(k));
        }

        return tmp;
    }


    @Override
    public String toString() {
        if (dic.size() > 0)
            return dic.toString();
        return "The table is empty";
    }
}
