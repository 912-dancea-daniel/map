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

public class IfStmt implements IStmt {

    Exp exp;
    IStmt thenS, elseS;

    public IfStmt(Exp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    @Override
    public String toString() {
        return "if("+ exp.toString()+") {" +thenS.toString()
                +"}else{"+elseS.toString()+"}";
    }

    @Override
    public PrgState execute(PrgState state) throws ExpException, ADTException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value Cond = exp.eval(symTable, heap);
        if (Cond.getType().equals(new BoolType())) {
            BoolValue b1 = (BoolValue) Cond;
            if (b1.getValue()){
                stk.push(thenS);
            } else stk.push(elseS);
        }else throw new ExpException("conditional expr is not a boolean");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())){
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());

            return typeEnv;
        }
        else throw new MyException("The condition of IF is not a boolean type");
    }
}
