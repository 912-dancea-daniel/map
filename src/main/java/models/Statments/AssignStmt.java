package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.Type;
import models.Values.Value;


public class AssignStmt implements IStmt{

    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id+"="+ exp.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, ADTException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(id)){
            Value val = exp.eval(symTable, heap);
            Type typeId = (symTable.lookup(id)).getType();
            if ((val.getType()).equals(typeId)){
                symTable.put(id, val);
            } else throw new ExpException("\"declared type of variable\"+id+\" and type of\n" +
                    "the assigned expression do not match");
        }else throw new ExpException("the used variable" +id + " was not declared before");


        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        try {
            Type typeVar = typeEnv.lookup(id);
            Type typeExp = exp.typeCheck(typeEnv);

            if (typeVar.equals(typeExp))
                return typeEnv;
            else throw new MyException("Right and lef hand side have different types");
        } catch (ADTException e) {
            throw new MyException(e.getMessage());
        }
    }
}
