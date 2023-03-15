package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.BoolType;
import models.Types.IntType;
import models.Types.Type;
import models.Values.BoolValue;
import models.Values.IntValue;
import models.Values.Value;

import java.util.Objects;

public class RelationalExp implements Exp {

    Exp e1,e2;

    String opt;

    public RelationalExp(Exp e1, Exp e2, String opt) {
        this.e1 = e1;
        this.e2 = e2;
        this.opt = opt;
    }

    @Override
    public String toString() {
        return e1 + " " + opt + " " + e2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (Objects.equals(opt, "<")) return new BoolValue(n1 < n2);
                if (Objects.equals(opt, "<=")) return new BoolValue(n1 <= n2);
                if (Objects.equals(opt, ">")) return new BoolValue(n1 > n2);
                if (Objects.equals(opt, ">=")) return new BoolValue(n1 >= n2);
                if (Objects.equals(opt, "==")) return new BoolValue(n1 == n2);
                if (Objects.equals(opt, "!=")) return new BoolValue(n1 != n2);
            } else throw new ExpException("The second operand is not an integer");
        }else throw new ExpException("The first operand is not an integer.");
        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1, t2;

        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);

        if (t1.equals(new IntType()))
            if (t2.equals(new IntType()))
                return new BoolType();
            else throw new MyException("the second operand is not integer");
        else throw new MyException("the first operand is not integer");
    }
}
