package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyILatchTable;
import models.ADT.MyIStack;
import models.PrgState;
import models.Types.IntType;
import models.Types.Type;
import models.Values.IntValue;
import models.Values.Value;

import java.io.IOException;

public class countDownStmt implements IStmt{

    String var;

    public countDownStmt(String v){
        var = v;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        try {
            Type typevar = typeEnv.lookup(var);
            if (typevar.equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("Await stmt: variable is not of type int ");
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "countDown(" + var + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyILatchTable<Integer, Integer> latchTable = state.getLatchTable();

        if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType())) {
            IntValue foundIndex = (IntValue)symTable.lookup(var);
            Integer latchValue = latchTable.lookup(foundIndex.getVal());
            if(latchValue == null) throw new MyException("Non existent latch value");
            else if(latchValue > 0) latchTable.put(foundIndex.getVal(), latchTable.lookup(foundIndex.getVal()) - 1);
            state.getOut().add(new IntValue(state.getId()));

        }else throw new MyException("Not in defined or not int");
        return null;
    }
}
