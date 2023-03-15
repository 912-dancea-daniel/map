package models.Expressions;

import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.Type;
import models.Values.Value;

public class ValueExp implements Exp{

    Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException {
        return e;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return  e.toString();
    }
}
