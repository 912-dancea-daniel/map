package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.PrgState;
import models.Types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, ADTException, ExpException;

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
