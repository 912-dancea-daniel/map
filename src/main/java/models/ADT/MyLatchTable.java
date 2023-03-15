package models.ADT;

import Exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyLatchTable<T, Y> implements MyILatchTable<T, Y>{

    Map<T,Y> latchTable;

    public MyLatchTable() {
        this.latchTable = new HashMap<>();
    }
    static int freeSpace = 1;


    public static synchronized void setFreeSpace(){
        freeSpace++;
    }

    static Lock lock = new ReentrantLock();


    @Override
    public void update(T key, Y value) {
        latchTable.put(key, value);
    }


    @Override
    public Y lookup(T key) throws ADTException {
        lock.lock();

        if (latchTable.get(key) == null) {lock.unlock(); throw new ADTException("Not in semaphore table");}
        else {lock.unlock(); return latchTable.get(key);}
    }

    @Override
    public boolean isDefined(T key) {
        return latchTable.get(key) != null;
    }

    @Override
    public Map<T, Y> getContent() {
        Map<T, Y> tmp = new HashMap<>();
        for (T key : latchTable.keySet()){
            tmp.put(key, latchTable.get(key));
        }
        return tmp;
    }

    @Override
    public int getFreeSpace() {
        return freeSpace;
    }


    @Override
    public void put(T key, Y value) {
        lock.lock();
        latchTable.put(key, value);
        lock.unlock();
        setFreeSpace();
    }





}
