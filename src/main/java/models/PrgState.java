package models;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import javafx.util.Pair;
import models.ADT.*;
import models.Statments.IStmt;
import models.Values.IntValue;
import models.Values.StringValue;
import models.Values.Value;

import java.io.BufferedReader;
import java.util.ArrayList;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;

    MyIDictionary<StringValue, BufferedReader> fileTable;

    MyIHeap<Integer, Value> heap;

    MyILatchTable<Integer, Integer> latchTable;

    IStmt originalProgram;

    static int uniqueID = 1;

    int id;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> sT, MyIList<Value> o, IStmt original, MyIDictionary<StringValue, BufferedReader> fT, MyIHeap<Integer, Value> h, MyILatchTable<Integer, Integer> lt){
        exeStack = stk;
        symTable = sT;
        out = o;
        originalProgram = original;
        stk.push(originalProgram);
        fileTable = fT;
        heap = h;
        latchTable = lt;
        setId(this);
    }

    public MyIStack<IStmt> getStk(){
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable(){return symTable;}

    public MyIList<Value> getOut(){return out;}

    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable(){return fileTable;}

    public MyILatchTable<Integer, Integer> getLatchTable(){return latchTable;}

    public Boolean isNotCompleted(){return !exeStack.isEmpty();}

    public PrgState oneStep() throws MyException, ExpException, ADTException {
        if (exeStack.isEmpty()) throw new MyException("Prg state stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public static synchronized void setId(PrgState prgState){
        prgState.id = uniqueID;
        uniqueID++;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return  "Prg id: " + id +
                "\nexeStack=" + exeStack.toString() +
                "\nsymTable=" + symTable.toString() +
                "\nheap= " + heap.toString() +
                "\nfileTable=" + fileTable.toString() +
                "\nout=" + out.toString() + "\n\n";

    }

    public int getId() {
        return id;
    }
}
