package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.StringType;
import models.Types.Type;
import models.Values.StringValue;
import models.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt{
    Exp exp;

    public closeRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value ex = exp.eval(symTable, heap);

        if (ex.getType().equals(new StringType())){
            StringValue stringValue = (StringValue) ex;
            if (fileTable.isDefined(stringValue)){
                BufferedReader br = fileTable.lookup(stringValue);
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileTable.deleteKey(stringValue);
            } else throw new ExpException("It's already defined");
        }else throw new ExpException("The expression is not a string type");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new StringType()))
            return typeEnv;
        else throw new MyException("The var is not string");
    }

    @Override
    public String toString() {
        return exp.toString() + ".close()";
    }
}
