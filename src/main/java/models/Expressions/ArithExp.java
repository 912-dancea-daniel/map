package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.IntType;
import models.Types.Type;
import models.Values.IntValue;
import models.Values.Value;

public class ArithExp implements Exp{
    Exp e1, e2;
    int op;//1 + , 2 - , 3 * , 4 /

    public ArithExp(int op, Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        if (op == 1) return e1.toString() + " + " + e2.toString();
        if (op == 2) return e1.toString() + " - " + e2.toString();
        if (op == 3) return e1.toString() + " * " + e2.toString();
        return e1.toString() + " / " + e2.toString();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1+n2);
                if (op == 2) return new IntValue(n1-n2);
                if (op == 3) return new IntValue(n1*n2);
                if (op == 4)
                    if (n2==0) throw new ExpException("Division by 0");
                    else return new IntValue(n1/n2);

            } else throw new ExpException("The second operand is not an integer");
        }else throw new ExpException("The first operand is not an integer.");
        return null;

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);

        if (type1.equals(new IntType())){
            if (type2.equals(new IntType()))
                return new IntType();
            else throw new MyException("the second operand is not an integer");
        } else throw new MyException("the first operand is not an integer");

    }
}
