package models.ADT;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHeap<T, Y> implements MyIHeap<T, Y>{
    Map<T, Y> heap;

    static int freeSpace = 1;

    public static synchronized void setFreeSpace(){
        freeSpace++;
    }

    public static synchronized void setPreciseFreeSpace(int v){
        freeSpace = v;
    }

    public MyHeap() {
        this.heap = new HashMap<>();
    }

    @Override
    public String toString() {
        if (heap.size() > 0)
            return heap.toString();
        return "The table is empty";
    }

    @Override
    public void put(T key, Y value) {
        heap.put(key, value);
        setFreeSpace();
    }

    @Override
    public void update(T key, Y value) {
        heap.put(key, value);
    }

    @Override
    public Y lookup(T key) throws ADTException {
        if (heap.get(key) == null) throw new ADTException("Not in heap");
        else return heap.get(key);
    }

    @Override
    public boolean isDefined(T key) {
         return heap.get(key) != null;
    }

    @Override
    public int getSize() {
        return heap.size();
    }

    @Override
    public void deleteKey(T key) {
        heap.remove(key);
    }

    @Override
    public int getFreeSpace() {
        return freeSpace;
    }

    @Override
    public Map<T, Y> getContent() {
        Map<T, Y> tmp = new HashMap<>();
        for (T key : heap.keySet()){
            tmp.put(key, heap.get(key));
        }
        return tmp;
    }

    @Override
    public List<Y> getValues() {
        List<Y> tmp;
        tmp = heap.values().stream().toList();
        return tmp;
    }

    @Override
    public void setContent(Map<T, Y> map) {
        heap.clear();
        int tmp = getFreeSpace();
        for (T key: map.keySet()){
            heap.put(key, map.get(key));
        }
        setPreciseFreeSpace(tmp);
    }

}
