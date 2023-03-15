package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyDictionary;
import models.ADT.MyIDictionary;
import models.ADT.MyStack;
import models.PrgState;
import models.Types.Type;
import models.Values.Value;

import java.util.Enumeration;

public class forkStmt implements IStmt{
    IStmt stmt;

    public forkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyDictionary<String, Value> newSymTable = new MyDictionary<>();

        Enumeration<String> e = state.getSymTable().getKeys();
        while(e.hasMoreElements()) {
            String k = e.nextElement();
            newSymTable.put(k, state.getSymTable().lookup(k));
        }

        MyStack<IStmt> newStack = new MyStack<>();

        return new PrgState(newStack, newSymTable, state.getOut(), stmt ,state.getFileTable(), state.getHeap(), state.getLatchTable());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typeCheck(typeEnv.deepCopy());

        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(){" + stmt + "}";
    }
}
