package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.ADT.MyIStack;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.BoolType;
import models.Types.Type;
import models.Values.BoolValue;
import models.Values.Value;

public class WhileStmt implements IStmt{
    Exp exp;

    IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "while(" + exp + "){" + stmt + '}';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value Cond = exp.eval(symTable, heap);
        if (Cond.getType().equals(new BoolType())){
            BoolValue b1 = (BoolValue) Cond;
            if (b1.getValue()){
                stk.push(new WhileStmt(exp, stmt));
                stk.push(stmt);
            }
        }
        else throw new ExpException("conditional expr is not a boolean");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);

        if (typeExp.equals(new BoolType())){
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }else throw new MyException("The condition in the while is not bool type");

    }
}
