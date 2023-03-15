package models.Statments;

import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.PrgState;
import models.Types.*;
import models.Values.Value;

public class VarDeclStmt implements IStmt {

    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) throw new MyException("Variable with name " + name + " has already been defined.");
        else {
            if (typ.equals(new BoolType())){
                Value val = typ.defaultValue();
                symTable.put(name, val);
            }
            else if (typ.equals(new IntType())){
                Value val = typ.defaultValue();
                symTable.put(name, val);
            }
            else if (typ.equals(new StringType())){
                Value val = typ.defaultValue();
                symTable.put(name, val);
            }
            else if (typ.equals(new RefType())){
                Value val = typ.defaultValue();
                symTable.put(name, val);
            }
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, typ);

        return typeEnv;
    }

    @Override
    public String toString() {
        return  typ + " " + name;
    }
}
