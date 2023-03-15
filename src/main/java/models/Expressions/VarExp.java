package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.Type;
import models.Values.Value;

public class VarExp implements Exp{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException {
        return tbl.lookup(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            return typeEnv.lookup(id);
        }
        catch (ADTException e) {
            System.out.println(e);
        }
        return null;
    }
}
