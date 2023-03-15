package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.IntType;
import models.Types.StringType;
import models.Types.Type;
import models.Values.IntValue;
import models.Values.StringValue;
import models.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class readFile implements IStmt{
    Exp exp;

    String var_name;

    public readFile(Exp exp, String var_name) {
        this.exp = exp;
        this.var_name = var_name;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            Value val = symTable.lookup(var_name);
            Type typeId = (symTable.lookup(var_name)).getType();
            if (typeId.equals(new IntType())){
                Value expValue = exp.eval(symTable, heap);
                StringValue myString = (StringValue) expValue;
                if (fileTable.isDefined(myString)){
                    BufferedReader br = fileTable.lookup(myString);
                    try {
                        String read_value = br.readLine();
                        if (Objects.equals(read_value, "")){
                            symTable.put(var_name, new IntValue(0));
                        }else{
                            symTable.put(var_name, new IntValue(Integer.parseInt(read_value)));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else throw new ExpException("No such string found in FileTable");
            } else throw new ExpException("Var is not int");
        }else throw new ExpException("the used variable" + var_name + " was not declared before");


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        try {
            Type typeVar = typeEnv.lookup(var_name);
            if (typeVar.equals(new IntType()))
                if (typeExp.equals(new StringType()))
                    return typeEnv;
                else throw new MyException("The exp is not string");
            else throw new MyException("The var is not int");
        } catch (ADTException e) {
            throw new MyException(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return var_name + "=" + exp.toString() + ".read()";
    }
}
