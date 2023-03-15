package models.Statments;

import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.PrgState;
import models.Types.Type;

public class NopStmt implements IStmt{

    @Override
    public String toString() {
        return "Nop";
    }

    public NopStmt() {
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
