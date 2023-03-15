package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.RefType;
import models.Types.Type;
import models.Values.RefValue;
import models.Values.Value;

public class rW implements IStmt{
    String var_name;
    Exp exp;

    public rW(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "rW(" +  var_name + ", " + exp + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(var_name)){
            RefValue r1 = (RefValue) symTable.lookup(var_name);
            int address = r1.getAddress();
            if (heap.isDefined(address)){
                Value val = exp.eval(symTable, heap);
                if ((val.getType()).equals(r1.getLocationType())){
                    heap.update(address, val);
                } else throw new ExpException("\"declared type of variable\"+id+\" and type of\n" +
                        "the assigned expression do not match");
            } else throw new ExpException("address associated with var_name not in heap");
        }else throw new ExpException("the used variable was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            Type typeVar = typeEnv.lookup(var_name);
            Type typeExp = exp.typeCheck(typeEnv);

            if (typeVar.equals(new RefType(typeExp)))
                return typeEnv;
            else throw new MyException("NEW stmt: right hand side and left hand side have " +
                    "different types");
        } catch (ADTException e) {
            throw new MyException(e.getMessage());
        }
    }
}
