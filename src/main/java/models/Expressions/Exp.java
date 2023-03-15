package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.Type;
import models.Values.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException;

    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;

}
