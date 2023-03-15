package models.Expressions;

import Exceptions.ADTException;
import Exceptions.ExpException;
import Exceptions.MyException;
import models.ADT.MyIDictionary;
import models.ADT.MyIHeap;
import models.Types.RefType;
import models.Types.Type;
import models.Values.RefValue;
import models.Values.Value;

public class rH implements Exp{
    Exp e1;

    public rH(Exp e1) {
        this.e1 = e1;
    }

    @Override
    public String toString() {
        return "rh(" +  e1.toString() + ")";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer,Value> hp) throws ExpException, ADTException {
        Value v1;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new RefType())){
            RefValue r1 = (RefValue) v1;
            int address = r1.getAddress();
            if (hp.isDefined(address)){
                return hp.lookup(address);
            }else throw new ExpException("The address not found in the heap.");
        }else throw new ExpException("The first operand is not an ref type.");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = e1.typeCheck(typeEnv);

        if (type.equals(new RefType()))
            return ((RefType) type).getInner();
        else throw new MyException("the rH argument is not a Ref type");

    }
}
