package models.Statments;

import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIStack;
import models.PrgState;
import models.Types.Type;

public class CompStmt implements IStmt{

    IStmt first;
    IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    public String toString(){
        return first.toString() + "; " + snd.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStk();
        stack.push(snd);
        stack.push(first);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typeCheck(first.typeCheck(typeEnv));
    }
}
