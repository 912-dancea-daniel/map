package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.BoolType;
import models.Types.Type;
import models.Values.BoolValue;
import models.Values.Value;

public class LogicExp implements Exp{

    Exp e1,e2;
    int opt;//1 and, 2 or

    @Override
    public String toString() {
        return "LogicExp{" +
                "e1=" + e1 +
                ", e2=" + e2 +
                ", opt=" + opt +
                '}';
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException {
        Value v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new BoolType())){
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new BoolType())) {

                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1,n2;
                n1 = b1.getValue();
                n2 = b2.getValue();
                if (opt == 1){
                    if (n1 && n2) return new BoolValue(true);
                    else return new BoolValue(false);
                }
                if (opt == 2){
                    if (n1 || n2) return new BoolValue(true);
                    else return new BoolValue(false);
                }
            }else throw new ExpException("The second operand is not boolean");

        }else throw new ExpException("The first operand is not boolean");

        return null;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {

        Type t1, t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);

        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType()))
                return new BoolType();
            else throw new MyException("the second operand is not boolean");
        }
        else throw new MyException("the first operand is not boolean");

    }
}
