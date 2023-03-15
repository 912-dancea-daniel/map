package models.Statments;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.Expressions.Exp;
import models.PrgState;
import models.Types.BoolType;
import models.Types.Type;

public class ConditionalAssignment implements IStmt{

    Exp exp1, exp2, exp3;
    String v;

    public ConditionalAssignment(Exp exp1, Exp exp2, Exp exp3, String v) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.v = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ADTException, ExpException {
        IStmt stmt = new IfStmt(exp1, new AssignStmt(v, exp2), new AssignStmt(v, exp3));
        state.getStk().push(stmt);

        return null;
    }

    @Override
    public String toString() {
        return v + "=" + exp1 + "?" + exp2 + ":" + exp3;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        try {
            Type t1, t2, t3, t4;

            t1 = exp1.typeCheck(typeEnv);
            t2 = exp2.typeCheck(typeEnv);
            t3 = exp3.typeCheck(typeEnv);
            t4 = typeEnv.lookup(v);

            if (t2.equals(t3) && t3.equals(t4) && t1.equals(new BoolType()))
                return typeEnv;
            else throw new MyException("not the same");
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }


    }
}
