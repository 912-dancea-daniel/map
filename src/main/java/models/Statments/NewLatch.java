package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyILatchTable;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.IntType;
import models.Types.Type;
import models.Values.IntValue;
import models.Values.Value;

import java.io.IOException;

public class NewLatch implements IStmt{

    String var;

    Exp exp;




    public NewLatch(String v, Exp e){
        var = v;
        exp = e;
    }


    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = null;
        try {
            typevar = typeEnv.lookup(var);
            Type typexp = exp.typeCheck(typeEnv);
            if (typevar.equals(new IntType()) && typexp.equals(new IntType()))
                return typeEnv;
            else
                throw new MyException("NewLatch stmt: variable and/or expression are not of type int ");
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "newLatch(" + var + "," + exp.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyILatchTable<Integer, Integer> latchTable = state.getLatchTable();

        if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType())) {
            Value num1 =  exp.eval(symTable,heap);
            if(num1.getType().equals(new IntType()))
            {
                IntValue num11 = (IntValue) num1;
                int space = latchTable.getFreeSpace();
                latchTable.put(space, num11.getVal());
                symTable.put(var, new IntValue(space));
            }
            else throw new MyException("Expression is not type int");

        }
        else throw new MyException("Variable isn't of type string");
        return null;
    }
}
