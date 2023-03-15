package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIList;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.Type;
import models.Values.Value;

public class PrintStmt implements IStmt{

    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print(" +exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws ADTException, ExpException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTable, heap);
        System.out.println(val);
        MyIList<Value> outList = state.getOut();
        outList.add(val);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typeCheck(typeEnv);

        return typeEnv;
    }
}
